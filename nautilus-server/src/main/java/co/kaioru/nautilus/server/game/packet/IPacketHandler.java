package co.kaioru.nautilus.server.game.packet;

import co.kaioru.nautilus.server.game.user.RemoteUser;

@FunctionalInterface
public interface IPacketHandler {

	default boolean validate(RemoteUser remoteUser) {
		return true;
	}

	void handle(RemoteUser remoteUser, PacketReader reader);

}
