package co.kaioru.nautilus.client.packet;

import co.kaioru.nautilus.core.packet.IPacketWriter;
import co.kaioru.nautilus.core.packet.PacketEncoder;
import co.kaioru.nautilus.crypto.maple.MapleCrypto;
import co.kaioru.nautilus.crypto.maple.ShandaCrypto;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class ClientPacketEncoder extends PacketEncoder {

	private final ShandaCrypto shandaCrypto;
	private final MapleCrypto mapleCrypto;

	public ClientPacketEncoder(ShandaCrypto shandaCrypto, MapleCrypto mapleCrypto) {
		this.shandaCrypto = shandaCrypto;
		this.mapleCrypto = mapleCrypto;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, IPacketWriter in, ByteBuf out) throws Exception {
		super.encode(ctx, in, out, shandaCrypto, mapleCrypto);
	}

}
