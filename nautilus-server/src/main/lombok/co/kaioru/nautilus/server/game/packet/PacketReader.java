package co.kaioru.nautilus.server.game.packet;

import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

@Getter
@Setter
public class PacketReader {

	private final ByteBuffer buffer;

	public PacketReader(final Packet packet) {
		this.buffer = ByteBuffer.wrap(packet.getPayload()).order(ByteOrder.LITTLE_ENDIAN);
	}

	public boolean readBool() {
		return buffer.get() > 0;
	}

	public byte readByte() {
		return buffer.get();
	}

	public short readShort() {
		return buffer.getShort();
	}

	public int readInt() {
		return buffer.getInt();
	}

	public long readLong() {
		return buffer.getLong();
	}

	public String readString(int length) {
		byte[] c = new byte[length];
		for (int i = 0; i < length; i++)
			c[i] = buffer.get();
		return new String(c, Charset.defaultCharset());
	}

	public String readMapleString() {
		return readString(readShort());
	}

	public String readNullTerminatedString() {
		int c = 0;
		while (readByte() != 0) {
			c++;
		}
		buffer.position(buffer.position() - c + 1);
		return readString(c);
	}

	public PacketReader skip(int num) {
		buffer.position(buffer.position() + num);
		return this;
	}

	public int available() {
		return buffer.array().length - buffer.position();
	}

	public byte[] raw() {
		return buffer.array();
	}

}
