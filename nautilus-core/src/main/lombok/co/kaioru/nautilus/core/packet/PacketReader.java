package co.kaioru.nautilus.core.packet;

import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;

public class PacketReader extends Packet implements IPacketReader {

	public PacketReader(ByteBuf buffer) {
		super(buffer);
	}

	@Override
	public byte readByte() {
		return getBuffer().readByte();
	}

	@Override
	public byte[] readBytes(int val) {
		byte[] data = new byte[val];
		getBuffer().readBytes(data);
		return data;
	}

	@Override
	public short readShort() {
		return getBuffer().readShort();
	}

	@Override
	public int readInt() {
		return getBuffer().readInt();
	}

	@Override
	public long readLong() {
		return getBuffer().readLong();
	}

	@Override
	public String readString() {
		return getBuffer().readBytes(readShort()).toString(Charset.defaultCharset());
	}

}
