package co.kaioru.nautilus.server.game.user;

import co.kaioru.nautilus.core.user.User;
import co.kaioru.nautilus.crypto.maple.MapleCrypto;
import co.kaioru.nautilus.server.IServer;
import co.kaioru.nautilus.server.config.ServerConfig;
import co.kaioru.nautilus.server.game.IChannelServer;
import co.kaioru.nautilus.server.game.IWorldCluster;
import co.kaioru.nautilus.server.packet.IPacket;
import co.kaioru.nautilus.server.packet.IPacketWriter;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.Closeable;
import java.util.concurrent.locks.ReentrantLock;

@Getter
@Setter
public abstract class RemoteUser extends User {

	public static final AttributeKey<RemoteUser> USER_KEY = AttributeKey.valueOf("USER");
	public static final AttributeKey<MapleCrypto> RECV_CRYPTO_KEY = AttributeKey.valueOf("RECV");
	public static final AttributeKey<MapleCrypto> SEND_CRYPTO_KEY = AttributeKey.valueOf("SEND");

	@Getter(AccessLevel.PRIVATE)
	private final Channel channel;

	private IWorldCluster worldCluster;
	private IChannelServer channelServer;

	public RemoteUser(Channel channel) {
		this.channel = channel;
	}

	public abstract void migrate(IServer<?, ? extends ServerConfig> server) throws Exception;

	public abstract void close();

	public void sendPacket(IPacket packet) {
		getChannel().writeAndFlush(packet);
	}

	public void sendPacket(IPacketWriter writer) {
		writer.buildAndFlush(getChannel());
	}

}
