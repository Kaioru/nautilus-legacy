package co.kaioru.nautilus.server;

import co.kaioru.nautilus.crypto.ICrypto;
import co.kaioru.nautilus.crypto.maple.MapleCrypto;
import co.kaioru.nautilus.crypto.maple.ShandaCrypto;
import co.kaioru.nautilus.server.config.ServerConfig;
import co.kaioru.nautilus.server.game.client.Client;
import co.kaioru.nautilus.server.game.packet.*;
import com.google.common.collect.Maps;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Getter
@Setter
@Slf4j
public abstract class Server<C extends ICluster, CO extends ServerConfig> extends Shard<C, CO> implements IServer<C, CO> {

	private Channel channel;
	private EventLoopGroup bossGroup, workerGroup;

	private Map<Integer, IPacketHandler> handlers;

	public Server(CO config) {
		super(config);

		this.handlers = Maps.newConcurrentMap();
	}

	@Override
	public void run() {
		super.run();

		this.bossGroup = new NioEventLoopGroup(2, getExecutor());
		this.workerGroup = new NioEventLoopGroup(4, getExecutor());

		try {
			this.channel = new ServerBootstrap()
				.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel socketChannel) throws Exception {
						ICrypto crypto = new ShandaCrypto();
						socketChannel.pipeline().addLast(
							new PacketDecoder(crypto),
							new ChannelInboundHandlerAdapter() {

								@Override
								public void channelActive(ChannelHandlerContext ctx) {
									byte[] siv = {82, 48, (byte) (Math.random() * 255), 115};
									byte[] riv = {70, 114, (byte) (Math.random() * 255), 82};
									short majorVersion = 62, minorVersion = 1;
									Channel channel = ctx.channel();
									Client client = new Client(channel, riv, siv);

									new PacketBuilder()
										.writeShort(0x0E)
										.writeShort(majorVersion)
										.writeMapleString(String.valueOf(minorVersion))
										.writeBytes(riv)
										.writeBytes(siv)
										.writeByte((byte) 8)
										.buildAndFlush(channel);

									channel.attr(Client.CLIENT_KEY).set(client);
									channel.attr(Client.CRYPTO_KEY).set(new MapleCrypto(majorVersion));
								}

								@Override
								public void channelRead(ChannelHandlerContext ctx, Object msg) {
									PacketReader reader = new PacketReader((Packet) msg);
									Client client = ctx.channel().attr(Client.CLIENT_KEY).get();

									short operation = reader.readShort();
									IPacketHandler handler = handlers.get(operation);

									if (handler != null) {
										handler.handle(client, reader);
										log.debug("Handled operation code {} with {}",
											operation,
											handler.getClass().getSimpleName());
									} else log.warn("No packet handlers found for operation code {}", operation);
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

}
