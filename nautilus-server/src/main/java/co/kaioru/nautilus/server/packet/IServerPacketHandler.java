package co.kaioru.nautilus.server.packet;

import co.kaioru.nautilus.core.packet.IPacketReader;
import co.kaioru.nautilus.server.game.user.RemoteUser;

public interface IServerPacketHandler {

	default boolean validate(RemoteUser user) {
		return true;
	}

	void handle(RemoteUser user, IPacketReader reader);

}
