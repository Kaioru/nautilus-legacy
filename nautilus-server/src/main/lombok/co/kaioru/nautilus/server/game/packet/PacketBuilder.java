package co.kaioru.nautilus.server.game.packet;

import co.kaioru.nautilus.core.util.IValue;
import io.netty.channel.Channel;

import java.awt.*;
import java.nio.charset.Charset;

public class PacketBuilder {

	private static final Charset ASCII = Charset.forName("US-ASCII");

	private int offset;
	private byte[] data;

	private PacketBuilder() {
		offset = 0;
		data = new byte[32];
	}

	public static PacketBuilder create() {
		return new PacketBuilder();
	}

	public static PacketBuilder create(int operation) {
		return create().writeShort(operation);
	}

	public static PacketBuilder create(IValue<Integer> operation) {
		return create().writeShort(operation.getValue());
	}

	private void expand(int size) {
		byte[] nd = new byte[size];
		System.arraycopy(data, 0, nd, 0, offset);
		data = nd;
	}

	private void trim() {
		expand(offset);
	}

	protected PacketBuilder write(long b) {
		if (offset + 1 >= data.length) {
			expand(data.length * 2);
		}
		data[offset++] = (byte) b;
		return this;
	}

	protected PacketBuilder write(byte... b) {
		for (int i = 0; i < b.length; i++) {
			write(b[i]);
		}
		return this;
	}

	public PacketBuilder writeByte(byte b) {
		return write(b);
	}

	public PacketBuilder writeBytes(byte[] b) {
		return write(b);
	}

	public PacketBuilder writeShort(int s) {
		return write(s & 0xFF).write(s >>> 8);
	}

	public PacketBuilder writeInt(int i) {
		return write(i & 0xFF).write(i >>> 8).write(i >>> 16).
			write(i >>> 24);
	}

	public PacketBuilder writeFloat(float f) {
		return writeInt(Float.floatToIntBits(f));
	}

	public PacketBuilder writeLong(long l) {
		return write(l & 0xFF).write(l >>> 8).write(l >>> 16).
			write(l >>> 24).write(l >>> 32).write(l >>> 40).
			write(l >>> 48).write(l >>> 56);
	}

	public PacketBuilder writeDouble(double d) {
		return writeLong(Double.doubleToLongBits(d));
	}

	public PacketBuilder writeString(String s) {
		return write(s.getBytes(ASCII));
	}

	public PacketBuilder writeMapleString(String s) {
		return writeShort(s.length()).writeString(s);
	}

	public PacketBuilder writeNullTerminatedString(String s) {
		return writeString(s).write(0);
	}

	public PacketBuilder writeBool(boolean b) {
		return write(b ? 1 : 0);
	}

	public PacketBuilder fill(int val, int num) {
		for (int i = 0; i < num; i++) {
			write(val);
		}
		return this;
	}

	public PacketBuilder writePos(Point p) {
		return writeShort(p.x).writeShort(p.y);
	}

	public int getOffset() {
		return offset;
	}

	public byte[] getData() {
		return data;
	}

	public void close() {
		offset = -1;
		data = null;
	}

	public Packet build() {
		if (data != null) {
			if (data.length > offset) {
				trim();
			}
			return new Packet(data);
		}
		return null;
	}

	public void buildAndFlush(Channel channel) {
		channel.writeAndFlush(build());
	}

}
