package co.kaioru.nautilus.server.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteOrder;
import java.nio.charset.Charset;

public class PacketReader implements IPacketReader {

	private final ByteBuf buffer;

	public PacketReader(byte[] payload) {
		this.buffer = Unpooled.wrappedBuffer(payload).order(ByteOrder.LITTLE_ENDIAN);
	}

	public PacketReader(IPacket packet) {
		this(packet.getPayload());
	}

	@Override
	public byte readByte() {
		return buffer.readByte();
	}

	@Override
	public short readShort() {
		return buffer.readShort();
	}

	@Override
	public int readInt() {
		return buffer.readInt();
	}

	@Override
	public long readLong() {
		return buffer.readLong();
	}

	@Override
	public String readString() {
		return buffer.readBytes(readShort()).toString(Charset.defaultCharset());
	}

}
