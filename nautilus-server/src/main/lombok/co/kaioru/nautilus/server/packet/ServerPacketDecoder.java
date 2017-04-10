package co.kaioru.nautilus.server.packet;

import co.kaioru.nautilus.core.packet.PacketDecoder;
import co.kaioru.nautilus.crypto.maple.MapleCrypto;
import co.kaioru.nautilus.crypto.maple.ShandaCrypto;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

public class ServerPacketDecoder extends PacketDecoder {

	private final ShandaCrypto shandaCrypto;

	public ServerPacketDecoder(ShandaCrypto shandaCrypto) {
		this.shandaCrypto = shandaCrypto;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		MapleCrypto mapleCrypto = ctx.channel().attr(RemoteUser.RECV_CRYPTO_KEY).get();
		super.decode(ctx, in, out, shandaCrypto, mapleCrypto);
	}

}
