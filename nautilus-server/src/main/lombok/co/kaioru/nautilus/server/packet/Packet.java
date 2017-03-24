package co.kaioru.nautilus.server.packet;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Packet implements IPacket {

	private final byte[] payload;

	public Packet(byte[] payload) {
		this.payload = payload;
	}

}
