package co.kaioru.nautilus.client;

import co.kaioru.nautilus.client.config.ClientConfig;
import co.kaioru.nautilus.client.packet.ClientPacketDecoder;
import co.kaioru.nautilus.client.packet.ClientPacketEncoder;
import co.kaioru.nautilus.client.packet.IClientPacketHandler;
import co.kaioru.nautilus.core.packet.IPacketReader;
import co.kaioru.nautilus.core.packet.IReceiver;
import co.kaioru.nautilus.core.user.User;
import co.kaioru.nautilus.crypto.maple.MapleCrypto;
import co.kaioru.nautilus.crypto.maple.ShandaCrypto;
import com.google.common.collect.Maps;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.util.Map;

@Getter
@Slf4j
public class Client<CO extends ClientConfig> extends User implements IReceiver<Client, IClientPacketHandler>, Runnable {

	private final CO config;
	private final Map<Integer, IClientPacketHandler> packetHandlers;

	private Channel channel;
	private ShandaCrypto shandaCrypto;
	private MapleCrypto sendCrypto, recvCrypto;

	public Client(CO config) {
		this.config = config;
		this.packetHandlers = Maps.newConcurrentMap();
	}

	@Override
	public void run() {
		EventLoopGroup group = new NioEventLoopGroup();
		Client client = this;

		try {
			byte[] aesKey = getConfig().getAesKey();
			ByteBuf buffer = Unpooled.buffer(aesKey.length * 4);

			for (byte i : aesKey) {
				buffer.writeByte(i);
				buffer.writeBytes(new byte[3]);
			}

			Cipher cipher = Cipher.getInstance("AES", new BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(buffer.array(), "AES"));

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
									} else handlePacket(client, reader);
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
				.connect(config.getHost(), config.getPort())
				.channel();
		} catch (Exception e) {
			group.shutdownGracefully();
		}
	}

}
