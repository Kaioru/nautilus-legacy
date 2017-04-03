package co.kaioru.nautilus.channel.packet;

import co.kaioru.nautilus.server.packet.IPacket;
import co.kaioru.nautilus.server.packet.PacketBuilder;

public class GameStructures {

	public IPacket getCharacterInformation() {
		return PacketBuilder.create()
			.build();
	}

}
