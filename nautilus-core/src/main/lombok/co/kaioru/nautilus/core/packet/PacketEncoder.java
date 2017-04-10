package co.kaioru.nautilus.core.packet;

import co.kaioru.nautilus.crypto.maple.MapleCrypto;
import co.kaioru.nautilus.crypto.maple.ShandaCrypto;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public abstract class PacketEncoder extends MessageToByteEncoder<IPacketWriter> {

	protected void encode(ChannelHandlerContext ctx,
						  IPacketWriter in,
						  ByteBuf out,
						  ShandaCrypto shandaCrypto,
						  MapleCrypto mapleCrypto) throws Exception {
		byte[] payload = in.getPayload();

		if (shandaCrypto != null && mapleCrypto != null) {
			byte[] header = mapleCrypto.getHeader(payload.length);

			payload = shandaCrypto.encrypt(payload);
			payload = mapleCrypto.encrypt(payload);
			out.writeBytes(header);
		}

		out.writeBytes(payload);
	}

}
