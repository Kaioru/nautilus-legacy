package co.kaioru.nautilus.server.game.packet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Packet {

	private final byte[] payload;

	public Packet(byte[] payload) {
		this.payload = payload;
	}

}
