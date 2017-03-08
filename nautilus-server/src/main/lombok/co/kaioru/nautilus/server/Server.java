package co.kaioru.nautilus.server;

import co.kaioru.nautilus.crypto.ICrypto;
import co.kaioru.nautilus.crypto.maple.MapleCrypto;
import co.kaioru.nautilus.crypto.maple.ShandaCrypto;
import co.kaioru.nautilus.server.config.ServerConfig;
import co.kaioru.nautilus.server.game.client.Client;
import co.kaioru.nautilus.server.game.packet.Packet;
import co.kaioru.nautilus.server.game.packet.PacketBuilder;
import co.kaioru.nautilus.server.game.packet.PacketDecoder;
import co.kaioru.nautilus.server.game.packet.PacketEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public abstract class Server<C extends ICluster, CO extends ServerConfig> extends Shard<C, CO> implements IServer<C, CO> {

	private Channel channel;
	private EventLoopGroup bossGroup, workerGroup;

	public Server(CO config) {
		super(config);
	}

	@Override
	public void run() {
		this.bossGroup = new NioEventLoopGroup();
		this.workerGroup = new NioEventLoopGroup();

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
									Client client = new Client(channel, siv, riv);

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
									Packet packet = (Packet) msg;
									Channel channel = ctx.channel();
									short header = packet.readShort();

									log.info("Packet with operation code: {} received", header);
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
			super.run();
			channel.closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

}
