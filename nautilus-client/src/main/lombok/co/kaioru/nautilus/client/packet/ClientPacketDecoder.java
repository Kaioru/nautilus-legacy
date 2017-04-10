package co.kaioru.nautilus.client.packet;

import co.kaioru.nautilus.core.packet.PacketDecoder;
import co.kaioru.nautilus.crypto.maple.MapleCrypto;
import co.kaioru.nautilus.crypto.maple.ShandaCrypto;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

public class ClientPacketDecoder extends PacketDecoder {

	private final ShandaCrypto shandaCrypto;
	private final MapleCrypto mapleCrypto;

	public ClientPacketDecoder(ShandaCrypto shandaCrypto, MapleCrypto mapleCrypto) {
		this.shandaCrypto = shandaCrypto;
		this.mapleCrypto = mapleCrypto;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		super.decode(ctx, in, out, shandaCrypto, mapleCrypto);
	}

}
