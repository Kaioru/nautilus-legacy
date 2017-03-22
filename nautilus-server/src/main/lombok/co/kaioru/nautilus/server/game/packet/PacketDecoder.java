package co.kaioru.nautilus.server.game.packet;

import co.kaioru.nautilus.crypto.ICrypto;
import co.kaioru.nautilus.crypto.maple.MapleCrypto;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

	private final ICrypto crypto;

	public PacketDecoder(ICrypto crypto) {
		this.crypto = crypto;
	}

	@Override
	protected void decode(ChannelHandlerContext chc, ByteBuf in, List<Object> out) throws Exception {
		MapleCrypto mapleCrypto = chc.channel().attr(RemoteUser.RECV_CRYPTO_KEY).get();

		if (mapleCrypto != null) {
			if (in.readableBytes() >= 4) {
				int header = in.readInt();

				if (!mapleCrypto.checkData(header)) {
					chc.close();
					return;
				}

				int length = mapleCrypto.getLength(header);
				byte[] payload = new byte[length];

				if (in.readableBytes() >= length) {
					in.readBytes(payload);
					payload = mapleCrypto.encrypt(payload);
					payload = crypto.decrypt(payload);
					out.add(new Packet(payload));
				}
			}
		}
	}

}
