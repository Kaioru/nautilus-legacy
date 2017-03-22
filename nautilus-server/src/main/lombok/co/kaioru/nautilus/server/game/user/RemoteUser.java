package co.kaioru.nautilus.server.game.user;

import co.kaioru.nautilus.core.user.User;
import co.kaioru.nautilus.crypto.maple.MapleCrypto;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.locks.ReentrantLock;

@Getter
@Setter
public class RemoteUser extends User {

	public static final AttributeKey<RemoteUser> USER_KEY = AttributeKey.valueOf("USER");
	public static final AttributeKey<MapleCrypto> RECV_CRYPTO_KEY = AttributeKey.valueOf("RECV");
	public static final AttributeKey<MapleCrypto> SEND_CRYPTO_KEY = AttributeKey.valueOf("SEND");

	private final Channel channel;
	private final ReentrantLock lock;

	public RemoteUser(Channel channel) {
		this.channel = channel;
		this.lock = new ReentrantLock(true);
	}

}
