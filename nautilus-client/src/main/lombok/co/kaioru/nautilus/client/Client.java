package co.kaioru.nautilus.client;

import co.kaioru.nautilus.client.packet.ClientPacketDecoder;
import co.kaioru.nautilus.client.packet.ClientPacketEncoder;
import co.kaioru.nautilus.client.packet.IClientPacketHandler;
import co.kaioru.nautilus.core.packet.IPacketReader;
import co.kaioru.nautilus.core.user.User;
import co.kaioru.nautilus.crypto.maple.MapleCrypto;
import co.kaioru.nautilus.crypto.maple.ShandaCrypto;
import com.google.common.collect.Maps;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.util.Map;

@Slf4j
public class Client extends User implements Runnable {

	private final String host;
	private final short port;
	private final Map<Integer, IClientPacketHandler> handlers;

	private Channel channel;
	private ShandaCrypto shandaCrypto;
	private MapleCrypto sendCrypto, recvCrypto;

	public Client(String host, short port) {
		this.host = host;
		this.port = port;
		this.handlers = Maps.newConcurrentMap();
	}

	@Override
	public void run() {
		EventLoopGroup group = new NioEventLoopGroup();
		Client client = this;

		try {
			byte[] key = {
				0x13, 0x00, 0x00, 0x00,
				0x08, 0x00, 0x00, 0x00,
				0x06, 0x00, 0x00, 0x00,
				(byte) 0xB4, 0x00, 0x00, 0x00,
				0x1B, 0x00, 0x00, 0x00,
				0x0F, 0x00, 0x00, 0x00,
				0x33, 0x00, 0x00, 0x00,
				0x52, 0x00, 0x00, 0x00};
			Cipher cipher = Cipher.getInstance("AES", new BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"));

			this.channel = new Bootstrap()
				.group(group)
				.channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel socketChannel) throws Exception {
						socketChannel.pipeline().addLast(
							new ClientPacketDecoder(shandaCrypto, sendCrypto),
							new ChannelInboundHandlerAdapter() {


								@Override
								public void channelRead(ChannelHandlerContext ctx, Object msg) throws GeneralSecurityException {
									IPacketReader reader = (IPacketReader) msg;

									if (shandaCrypto == null || sendCrypto == null || recvCrypto == null) {
										reader.readShort();

										short majorVersion = reader.readShort();
										reader.readString();
										byte[] siv = reader.readBytes(4);
										byte[] riv = reader.readBytes(4);

										shandaCrypto = new ShandaCrypto();
										sendCrypto = new MapleCrypto(cipher, majorVersion, siv);
										recvCrypto = new MapleCrypto(cipher, majorVersion, riv);
										log.debug("Received heartbeat from {}", channel.remoteAddress());
									} else {
										int operation = reader.readShort();
										IClientPacketHandler handler = handlers.get(operation);

										// TODO: duplicate code
										if (handler != null) {
											if (handler.validate(client))
												handler.handle(client, reader);
											log.debug("Handled operation code {} with {}",
												Integer.toHexString(operation),
												handler.getClass().getSimpleName());
										} else {
											log.warn("No packet handlers found for operation code {}", Integer.toHexString(operation));
										}
									}
								}

								@Override
								public void exceptionCaught(ChannelHandlerContext ctx, Throwable throwable) {
									//
								}

							},
							new ClientPacketEncoder(shandaCrypto, sendCrypto)
						);
					}

				})
				.option(ChannelOption.TCP_NODELAY, true)
				.option(ChannelOption.SO_KEEPALIVE, true)
				.connect(host, 8484)
				.channel();
		} catch (Exception e) {
			group.shutdownGracefully();
		}
	}

}
