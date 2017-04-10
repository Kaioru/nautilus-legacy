package co.kaioru.nautilus.core.packet;

import co.kaioru.nautilus.core.util.IValue;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteOrder;
import java.util.function.Consumer;

public class PacketBuilder extends PacketWriter implements IPacketWriter {

	private PacketBuilder(ByteBuf buffer) {
		super(buffer);
	}

	public static PacketBuilder create() {
		return new PacketBuilder(Unpooled.buffer(16));
	}

	public static PacketBuilder create(int operation) {
		return create().writeShort(operation);
	}

	public static PacketBuilder create(IValue<Integer> operation) {
		return create().writeShort(operation.getValue());
	}

	@Override
	public PacketBuilder writeByte(int val) {
		super.writeByte(val);
		return this;
	}

	@Override
	public PacketBuilder writeBytes(byte[] val) {
		super.writeBytes(val);
		return this;
	}

	@Override
	public PacketBuilder writeBytes(int val, int i) {
		super.writeBytes(val, i);
		return this;
	}

	@Override
	public PacketBuilder writeBoolean(boolean val) {
		super.writeBoolean(val);
		return this;
	}

	@Override
	public PacketBuilder writeShort(int val) {
		super.writeShort(val);
		return this;
	}

	@Override
	public PacketBuilder writeInt(int val) {
		super.writeInt(val);
		return this;
	}

	@Override
	public PacketBuilder writeLong(long val) {
		super.writeLong(val);
		return this;
	}

	@Override
	public PacketBuilder writeString(String val) {
		super.writeString(val);
		return this;
	}

	public PacketBuilder write(Consumer<IPacketWriter> consumer) {
		consumer.accept(this);
		return this;
	}

	public IPacketWriter build() {
		return this;
	}

}
