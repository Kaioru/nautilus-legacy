package co.kaioru.nautilus.core.packet;

import io.netty.buffer.ByteBuf;
import lombok.Getter;

import java.nio.ByteOrder;

@Getter
public class Packet implements IPacket {

	private final ByteBuf buffer;

	public Packet(ByteBuf buffer) {
		this.buffer = buffer.order(ByteOrder.LITTLE_ENDIAN);
	}

	@Override
	public byte[] getPayload() {
		return buffer.array();
	}

}
