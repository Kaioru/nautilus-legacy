package co.kaioru.nautilus.server.packet;

import co.kaioru.nautilus.server.game.user.RemoteUser;

@FunctionalInterface
public interface IPacketHandler {

	default boolean validate(RemoteUser user) {
		return true;
	}

	void handle(RemoteUser user, IPacketReader reader);

}
