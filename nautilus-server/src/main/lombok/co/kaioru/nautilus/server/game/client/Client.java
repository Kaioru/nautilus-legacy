package co.kaioru.nautilus.server.game.client;

import co.kaioru.nautilus.crypto.maple.MapleCrypto;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.locks.ReentrantLock;

@Getter
@Setter
public class Client {

	public static final AttributeKey<MapleCrypto> CRYPTO_KEY = AttributeKey.valueOf("A");

	public static final AttributeKey<Client> CLIENT_KEY = AttributeKey.valueOf("C");

	private final Channel channel;
	private final ReentrantLock lock;
	private byte[] riv;
	private byte[] siv;
	private int storedLength = -1;

	public Client(Channel channel, byte[] riv, byte[] siv) {
		this.channel = channel;
		this.siv = siv;
		this.riv = riv;

		this.lock = new ReentrantLock(true);
	}

}
