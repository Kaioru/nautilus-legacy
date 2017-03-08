package co.kaioru.nautilus.server.game.packet;

import co.kaioru.nautilus.crypto.ICrypto;
import co.kaioru.nautilus.crypto.maple.MapleCrypto;
import co.kaioru.nautilus.crypto.maple.ShandaCrypto;
import co.kaioru.nautilus.server.game.client.Client;
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
		MapleCrypto mapleCrypto = chc.channel().attr(Client.CRYPTO_KEY).get();
		Client client = chc.channel().attr(Client.CLIENT_KEY).get();

		if (client != null) {
			byte[] iv = client.getRiv();
			if (client.getStoredLength() == -1) {
				if (in.readableBytes() >= 4) {
					int h = in.readInt();
					if (!mapleCrypto.check(h, iv)) {
						client.getChannel().close();
						return;
					}
					client.setStoredLength(mapleCrypto.getLength(h));
				} else {
					return;
				}
			}
			if (in.readableBytes() >= client.getStoredLength()) {
				byte[] dec = new byte[client.getStoredLength()];
				in.readBytes(dec);
				client.setStoredLength(-1);

				dec = mapleCrypto.encrypt(dec, iv);
				client.setRiv(mapleCrypto.generateSeed(iv));

				dec = crypto.decrypt(dec);
				out.add(new Packet(dec));
			}
		}
	}

}
