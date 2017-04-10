package co.kaioru.nautilus.server;

import co.kaioru.nautilus.core.packet.IPacketReader;
import co.kaioru.nautilus.core.packet.PacketBuilder;
import co.kaioru.nautilus.crypto.maple.MapleCrypto;
import co.kaioru.nautilus.crypto.maple.ShandaCrypto;
import co.kaioru.nautilus.server.config.ServerConfig;
import co.kaioru.nautilus.server.game.IChannelServer;
import co.kaioru.nautilus.server.game.IWorldCluster;
import co.kaioru.nautilus.server.game.user.IRemoteUserFactory;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.migration.IServerMigration;
import co.kaioru.nautilus.server.migration.ServerMigration;
import co.kaioru.nautilus.server.packet.IServerPacketHandler;
import co.kaioru.nautilus.server.packet.ServerPacketDecoder;
import co.kaioru.nautilus.server.packet.ServerPacketEncoder;
import co.kaioru.nautilus.server.packet.game.SocketRecvOperations;
import co.kaioru.nautilus.server.packet.handler.AliveAckHandler;
import co.kaioru.nautilus.server.task.ShardHeartbeatTask;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.EntityManagerFactory;
import java.rmi.RemoteException;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@Slf4j
public abstract class Server<C extends ICluster, CO extends ServerConfig> extends Shard<C, CO> implements IServer<C, CO> {

	private final IRemoteUserFactory remoteUserFactory;
	private final Map<Integer, IServerPacketHandler> packetHandlers;
	private final Set<IServerMigration> migrations;

	private Channel channel;
	private ChannelGroup channelGroup;
	private EventLoopGroup bossGroup, workerGroup;

	public Server(CO config, IRemoteUserFactory remoteUserFactory, EntityManagerFactory entityManagerFactory) {
		super(config, entityManagerFactory);
		this.remoteUserFactory = remoteUserFactory;
		this.packetHandlers = Maps.newConcurrentMap();
		this.migrations = Sets.newConcurrentHashSet();
	}

	@Override
	public void run() {
		try {
			super.run();

			short majorVersion = getConfig().getMapleMajorVersion();
			short minorVersion = getConfig().getMapleMinorVersion();
			ShandaCrypto shandaCrypto = new ShandaCrypto();

			this.channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
			this.bossGroup = new NioEventLoopGroup(2);
			this.workerGroup = new NioEventLoopGroup(4);

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

			this.channel = new ServerBootstrap()
				.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel socketChannel) throws Exception {
						socketChannel.pipeline().addLast(
							new ServerPacketDecoder(shandaCrypto),
							new ChannelInboundHandlerAdapter() {

								@Override
								public void channelActive(ChannelHandlerContext ctx) throws GeneralSecurityException {
									byte[] riv = {70, 114, (byte) (Math.random() * 255), 82};
									byte[] siv = {82, 48, (byte) (Math.random() * 255), 115};
									Channel channel = ctx.channel();
									RemoteUser user = remoteUserFactory.create(channel);

									channel.writeAndFlush(PacketBuilder.create(0x0E)
										.writeShort(majorVersion)
										.writeString(String.valueOf(minorVersion))
										.writeBytes(riv)
										.writeBytes(siv)
										.writeByte((byte) 8)
										.build());

									channelGroup.add(channel);
									channel.attr(RemoteUser.USER_KEY).set(user);
									channel.attr(RemoteUser.RECV_CRYPTO_KEY).set(new MapleCrypto(cipher, majorVersion, riv));
									channel.attr(RemoteUser.SEND_CRYPTO_KEY).set(new MapleCrypto(cipher, majorVersion, siv));
									log.debug("Opened connection with {} > {}", channel.remoteAddress(), channel.localAddress());
								}

								@Override
								public void channelInactive(ChannelHandlerContext ctx) throws Exception {
									super.channelInactive(ctx);

									Channel channel = ctx.channel();
									RemoteUser user = channel.attr(RemoteUser.USER_KEY).get();

									user.close();
									channelGroup.remove(channel);
									log.debug("Closed connection with {} > {}", channel.remoteAddress(), channel.localAddress());
								}

								@Override
								public void channelRead(ChannelHandlerContext ctx, Object msg) {
									IPacketReader reader = (IPacketReader) msg;
									RemoteUser user = ctx.channel().attr(RemoteUser.USER_KEY).get();

									handlePacket(user, reader);
								}

							},
							new ServerPacketEncoder(shandaCrypto)
						);
					}

				})
				.childOption(ChannelOption.TCP_NODELAY, true)
				.childOption(ChannelOption.SO_KEEPALIVE, true)
				.bind(getConfig().getHost(), getConfig().getPort())
				.channel();

			registerPacketHandler(SocketRecvOperations.ALIVE_ACK, new AliveAckHandler());
			getExecutor().scheduleAtFixedRate(
				new ShardHeartbeatTask(channelGroup),
				10, 10,
				TimeUnit.SECONDS
			);

			log.info("{} started on {}:{}", getConfig().getName(), getConfig().getHost(), getConfig().getPort());
			channel.closeFuture()
				.addListener((ChannelFutureListener) channelFuture -> {
					workerGroup.shutdownGracefully();
					bossGroup.shutdownGracefully();
					channelGroup.disconnect();
				});
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void registerMigration(IWorldCluster worldCluster, IChannelServer channelServer, int characterId) throws RemoteException {
		deregisterMigration(characterId);
		migrations.add(new ServerMigration(worldCluster, channelServer, characterId));
	}

	@Override
	public void deregisterMigration(int characterId) throws RemoteException {
		migrations.removeIf(s -> s.getCharacterId() == characterId);
	}

	@Override
	public IServerMigration getServerMigration(int characterId) {
		return migrations.stream()
			.filter(m -> m.getCharacterId() == characterId)
			.findFirst()
			.orElse(null);
	}

}
