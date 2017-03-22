package co.kaioru.nautilus.server;

import co.kaioru.nautilus.core.util.IValue;
import co.kaioru.nautilus.crypto.ICrypto;
import co.kaioru.nautilus.crypto.maple.MapleCrypto;
import co.kaioru.nautilus.crypto.maple.ShandaCrypto;
import co.kaioru.nautilus.server.config.ServerConfig;
import co.kaioru.nautilus.server.game.packet.*;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import com.google.common.collect.Maps;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManagerFactory;
import java.security.GeneralSecurityException;
import java.util.Map;

@Getter
@Setter
@Slf4j
public abstract class Server<C extends ICluster, CO extends ServerConfig> extends Shard<C, CO> implements IServer<C, CO> {

	private Channel channel;
	private EventLoopGroup bossGroup, workerGroup;

	private Map<Integer, IPacketHandler> handlers;

	public Server(CO config, EntityManagerFactory entityManagerFactory) {
		super(config, entityManagerFactory);
		this.handlers = Maps.newConcurrentMap();
	}

	@Override
	public void run() {
		super.run();

		try {
			short majorVersion = getConfig().getMapleMajorVersion();
			short minorVersion = getConfig().getMapleMinorVersion();
			ICrypto crypto = new ShandaCrypto();

			this.bossGroup = new NioEventLoopGroup(2, getExecutor());
			this.workerGroup = new NioEventLoopGroup(4, getExecutor());

			this.channel = new ServerBootstrap()
				.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel socketChannel) throws Exception {
						socketChannel.pipeline().addLast(
							new PacketDecoder(crypto),
							new ChannelInboundHandlerAdapter() {

								@Override
								public void channelActive(ChannelHandlerContext ctx) throws GeneralSecurityException {
									byte[] riv = {70, 114, (byte) (Math.random() * 255), 82};
									byte[] siv = {82, 48, (byte) (Math.random() * 255), 115};
									byte[] key = {
										0x13, 0x00, 0x00, 0x00,
										0x08, 0x00, 0x00, 0x00,
										0x06, 0x00, 0x00, 0x00,
										(byte) 0xB4, 0x00, 0x00, 0x00,
										0x1B, 0x00, 0x00, 0x00,
										0x0F, 0x00, 0x00, 0x00,
										0x33, 0x00, 0x00, 0x00,
										0x52, 0x00, 0x00, 0x00};
									Channel channel = ctx.channel();
									RemoteUser remoteUser = new RemoteUser(channel);

									PacketBuilder.create(0x0E)
										.writeShort(majorVersion)
										.writeMapleString(String.valueOf(minorVersion))
										.writeBytes(riv)
										.writeBytes(siv)
										.writeByte((byte) 8)
										.buildAndFlush(channel);

									channel.attr(RemoteUser.USER_KEY).set(remoteUser);
									channel.attr(RemoteUser.RECV_CRYPTO_KEY).set(new MapleCrypto(majorVersion, key, riv));
									channel.attr(RemoteUser.SEND_CRYPTO_KEY).set(new MapleCrypto(majorVersion, key, siv));
								}

								@Override
								public void channelRead(ChannelHandlerContext ctx, Object msg) {
									PacketReader reader = new PacketReader((Packet) msg);
									RemoteUser remoteUser = ctx.channel().attr(RemoteUser.USER_KEY).get();

									int operation = reader.readShort();
									IPacketHandler handler = handlers.get(operation);

									if (handler != null) {
										if (handler.validate(remoteUser))
											handler.handle(remoteUser, reader);
										log.debug("Handled operation code {} with {}",
											operation,
											handler.getClass().getSimpleName());
									} else {
										log.warn("No packet handlers found for operation code {}", operation);
									}
								}

							},
							new PacketEncoder(crypto)
						);
					}

				})
				.childOption(ChannelOption.TCP_NODELAY, true)
				.childOption(ChannelOption.SO_KEEPALIVE, true)
				.bind(getConfig().getHost(), getConfig().getPort())
				.channel();

			log.info("{} started on {}:{}", getConfig().getName(), getConfig().getHost(), getConfig().getPort());
			channel.closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	public void registerPacketHandler(IValue<Integer> operation, IPacketHandler handler) {
		handlers.put(operation.getValue(), handler);
	}

	public void deregisterPacketHandler(IPacketHandler handler) {
		handlers.remove(handler);
	}

}
