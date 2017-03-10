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
		MapleCrypto mapleCrypto = chc.channel().attr(RemoteUser.CRYPTO_KEY).get();
		RemoteUser remoteUser = chc.channel().attr(RemoteUser.USER_KEY).get();

		if (remoteUser != null) {
			byte[] iv = remoteUser.getRiv();
			if (remoteUser.getStoredLength() == -1) {
				if (in.readableBytes() >= 4) {
					int h = in.readInt();
					if (!mapleCrypto.check(h, iv)) {
						remoteUser.getChannel().close();
						return;
					}
					remoteUser.setStoredLength(mapleCrypto.getLength(h));
				} else {
					return;
				}
			}
			if (in.readableBytes() >= remoteUser.getStoredLength()) {
				byte[] dec = new byte[remoteUser.getStoredLength()];
				in.readBytes(dec);
				remoteUser.setStoredLength(-1);

				dec = mapleCrypto.encrypt(dec, iv);
				remoteUser.setRiv(mapleCrypto.generateSeed(iv));

				dec = crypto.decrypt(dec);
				out.add(new Packet(dec));
			}
		}
	}

}
