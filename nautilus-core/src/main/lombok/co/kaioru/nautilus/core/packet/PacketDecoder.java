package co.kaioru.nautilus.core.packet;

import co.kaioru.nautilus.crypto.maple.MapleCrypto;
import co.kaioru.nautilus.crypto.maple.ShandaCrypto;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public abstract class PacketDecoder extends ByteToMessageDecoder {

	protected void decode(ChannelHandlerContext ctx,
						  ByteBuf in,
						  List<Object> out,
						  ShandaCrypto shandaCrypto,
						  MapleCrypto mapleCrypto) throws Exception {
		if (shandaCrypto != null && mapleCrypto != null) {
			if (in.readableBytes() >= 4) {
				int header = in.readInt();

				if (!mapleCrypto.checkData(header)) {
					ctx.close();
					return;
				}

				int length = mapleCrypto.getLength(header);
				byte[] payload = new byte[length];

				if (in.readableBytes() >= length) {
					in.readBytes(payload);
					payload = mapleCrypto.encrypt(payload);
					payload = shandaCrypto.decrypt(payload);
					out.add(new PacketReader(Unpooled.copiedBuffer(payload)));
				}
			}
		} else {
			out.add(new PacketReader(Unpooled.copiedBuffer(in)));
		}
	}

}
