package co.kaioru.nautilus.server.packet;

import co.kaioru.nautilus.core.util.IValue;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteOrder;
import java.nio.charset.Charset;

public class PacketBuilder implements IPacketWriter {

	private final ByteBuf buffer;

	private PacketBuilder() {
		this.buffer = Unpooled.buffer(16).order(ByteOrder.LITTLE_ENDIAN);
	}

	public static IPacketWriter create(IValue<Integer> operation) {
		return create(operation.getValue());
	}

	public static IPacketWriter create(int operation) {
		return create().writeShort(operation);
	}

	public static IPacketWriter create() {
		return new PacketBuilder();
	}

	@Override
	public IPacketWriter writeByte(int data) {
		buffer.writeByte(data);
		return this;
	}

	@Override
	public IPacketWriter writeBytes(byte[] data) {
		buffer.writeBytes(data);
		return this;
	}

	@Override
	public IPacketWriter writeBool(boolean data) {
		return writeByte(data ? 1 : 0);
	}

	@Override
	public IPacketWriter writeShort(int data) {
		buffer.writeShort(data);
		return this;
	}

	@Override
	public IPacketWriter writeInt(int data) {
		buffer.writeInt(data);
		return this;
	}

	@Override
	public IPacketWriter writeLong(long data) {
		buffer.writeLong(data);
		return this;
	}

	@Override
	public IPacketWriter writeString(String string) {
		buffer.writeShort(string.length());
		buffer.writeBytes(string.getBytes(Charset.defaultCharset()));
		return this;
	}

	@Override
	public Packet build() {
		return new Packet(buffer.array());
	}

}
