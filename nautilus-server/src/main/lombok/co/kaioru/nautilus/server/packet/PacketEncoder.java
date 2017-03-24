package co.kaioru.nautilus.server.packet;

import co.kaioru.nautilus.crypto.ICrypto;
import co.kaioru.nautilus.crypto.maple.MapleCrypto;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

	private final ICrypto crypto;

	public PacketEncoder(ICrypto crypto) {
		this.crypto = crypto;
	}

	@Override
	protected void encode(ChannelHandlerContext chc, Packet p, ByteBuf out) throws Exception {
		MapleCrypto mapleCrypto = chc.channel().attr(RemoteUser.SEND_CRYPTO_KEY).get();
		byte[] payload = p.getPayload();

		if (mapleCrypto != null) {
			byte[] header = mapleCrypto.getHeader(payload.length);

			payload = crypto.encrypt(payload);
			payload = mapleCrypto.encrypt(payload);
			out.writeBytes(header);
			out.writeBytes(payload);
		} else {
			out.writeBytes(payload);
		}
	}

}
