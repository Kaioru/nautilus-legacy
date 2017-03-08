package co.kaioru.nautilus.server.game.packet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Packet {

	private final byte[] payload;
	private int offset = 0;

	public Packet(byte[] payload) {
		this.payload = payload;
	}

	public int read() {
		try {
			return 0xFF & payload[offset++];
		} catch (Exception e) {
			return -1;
		}
	}

	public short readShort() {
		return (short) (read() + (read() << 8));
	}

}
