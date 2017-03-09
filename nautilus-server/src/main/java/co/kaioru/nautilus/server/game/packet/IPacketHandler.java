package co.kaioru.nautilus.server.game.packet;

import co.kaioru.nautilus.server.game.client.Client;

@FunctionalInterface
public interface IPacketHandler {

	void handle(Client client, PacketReader reader);

}
