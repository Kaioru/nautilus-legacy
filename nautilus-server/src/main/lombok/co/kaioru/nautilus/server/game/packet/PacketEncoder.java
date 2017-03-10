package co.kaioru.nautilus.server.game.packet;

import co.kaioru.nautilus.crypto.ICrypto;
import co.kaioru.nautilus.crypto.maple.MapleCrypto;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

	private final ICrypto crypto;

	int count = 0;

	public PacketEncoder(ICrypto crypto) {
		this.crypto = crypto;
	}

	@Override
	protected void encode(ChannelHandlerContext chc, Packet p, ByteBuf bb) throws Exception {
		MapleCrypto mapleCrypto = chc.channel().attr(RemoteUser.CRYPTO_KEY).get();
		RemoteUser remoteUser = chc.channel().attr(RemoteUser.USER_KEY).get();
		byte[] payload = p.getPayload();

		if (remoteUser != null) {
			byte[] iv = remoteUser.getSiv();
			byte[] head = mapleCrypto.getHeader(payload.length, iv);

			crypto.encrypt(payload);

			remoteUser.getLock().lock();
			try {
				mapleCrypto.encrypt(payload, iv);
				remoteUser.setSiv(mapleCrypto.generateSeed(iv));
			} finally {
				remoteUser.getLock().unlock();
			}

			bb.writeBytes(head);
			bb.writeBytes(payload);
		} else {
			bb.writeBytes(payload);
		}
	}

}
