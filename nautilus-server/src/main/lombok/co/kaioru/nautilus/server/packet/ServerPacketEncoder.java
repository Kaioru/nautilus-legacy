package co.kaioru.nautilus.server.packet;

import co.kaioru.nautilus.core.packet.IPacketWriter;
import co.kaioru.nautilus.core.packet.PacketEncoder;
import co.kaioru.nautilus.crypto.maple.MapleCrypto;
import co.kaioru.nautilus.crypto.maple.ShandaCrypto;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class ServerPacketEncoder extends PacketEncoder {

	private final ShandaCrypto shandaCrypto;

	public ServerPacketEncoder(ShandaCrypto shandaCrypto) {
		this.shandaCrypto = shandaCrypto;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, IPacketWriter in, ByteBuf out) throws Exception {
		MapleCrypto mapleCrypto = ctx.channel().attr(RemoteUser.SEND_CRYPTO_KEY).get();
		super.encode(ctx, in, out, shandaCrypto, mapleCrypto);
	}

}
