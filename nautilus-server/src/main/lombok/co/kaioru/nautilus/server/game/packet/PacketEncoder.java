package co.kaioru.nautilus.server.game.packet;

import co.kaioru.nautilus.crypto.ICrypto;
import co.kaioru.nautilus.crypto.maple.MapleCrypto;
import co.kaioru.nautilus.server.game.client.Client;
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
		MapleCrypto mapleCrypto = chc.channel().attr(Client.CRYPTO_KEY).get();
		Client client = chc.channel().attr(Client.CLIENT_KEY).get();
		byte[] payload = p.getPayload();

		if (client != null) {
			byte[] iv = client.getSiv();
			byte[] head = mapleCrypto.getHeader(payload.length, iv);

			crypto.encrypt(payload);

			client.getLock().lock();
			try {
				mapleCrypto.encrypt(payload, iv);
				client.setSiv(mapleCrypto.generateSeed(iv));
			} finally {
				client.getLock().unlock();
			}

			bb.writeBytes(head);
			bb.writeBytes(payload);
		} else {
			bb.writeBytes(payload);
		}
	}

}
