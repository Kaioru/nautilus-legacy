package co.kaioru.nautilus.core.packet;

import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;

public class PacketWriter extends Packet implements IPacketWriter {

	public PacketWriter(ByteBuf buffer) {
		super(buffer);
	}

	@Override
	public IPacketWriter writeByte(int val) {
		getBuffer().writeByte(val);
		return this;
	}

	@Override
	public IPacketWriter writeBytes(byte[] val) {
		getBuffer().writeBytes(val);
		return this;
	}

	@Override
	public IPacketWriter writeBytes(int val, int i) {
		for (int ii = 0; ii < i; ii++)
			getBuffer().writeByte(val);
		return this;
	}

	@Override
	public IPacketWriter writeBoolean(boolean val) {
		return writeByte(val ? 1 : 0);
	}

	@Override
	public IPacketWriter writeShort(int val) {
		getBuffer().writeShort(val);
		return this;
	}

	@Override
	public IPacketWriter writeInt(int val) {
		getBuffer().writeInt(val);
		return this;
	}

	@Override
	public IPacketWriter writeLong(long val) {
		getBuffer().writeLong(val);
		return this;
	}

	@Override
	public IPacketWriter writeString(String val) {
		getBuffer().writeShort(val.length());
		getBuffer().writeBytes(val.getBytes(Charset.defaultCharset()));
		return this;
	}

}
