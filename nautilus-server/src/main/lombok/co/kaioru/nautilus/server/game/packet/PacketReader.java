package co.kaioru.nautilus.server.game.packet;

import lombok.Getter;
import lombok.Setter;

import java.nio.charset.Charset;

@Getter
@Setter
public class PacketReader {

	private static Charset CHARSET = Charset.forName("US-ASCII");

	private final byte[] payload;
	private int offset = 0;

	public PacketReader(final Packet packet) {
		this.payload = packet.getPayload();
	}

	public int read() {
		try {
			return 0xFF & payload[offset++];
		} catch (Exception e) {
			return -1;
		}
	}

	public boolean readBool() {
		return read() > 0;
	}

	public byte readByte() {
		return (byte) read();
	}

	public short readShort() {
		return (short) (read() + (read() << 8));
	}

	public int readInt() {
		return read() + (read() << 8) + (read() << 16)
			+ (read() << 24);
	}

	public long readLong() {
		return read() + (read() << 8) + (read() << 16)
			+ (read() << 24) + (read() << 32)
			+ (read() << 40) + (read() << 48)
			+ (read() << 56);
	}

	public String readString(int len) {
		byte[] sd = new byte[len];
		for (int i = 0; i < len; i++) {
			sd[i] = readByte();
		}
		return new String(sd, CHARSET);
	}

	public String readMapleString() {
		return readString(readShort());
	}

	public String readNullTerminatedString() {
		int c = 0;
		while (read() != 0) {
			c++;
		}
		offset -= (c + 1);
		return readString(c);
	}

	public PacketReader skip(int num) {
		offset += num;
		return this;
	}

	public int available() {
		return payload.length - offset;
	}

}
