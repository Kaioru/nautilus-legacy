package co.kaioru.nautilus.server.packet.handler;

import co.kaioru.nautilus.core.packet.IPacketReader;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.packet.IServerPacketHandler;

import java.time.Instant;

public class AliveAckHandler implements IServerPacketHandler {

	@Override
	public void handle(RemoteUser user, IPacketReader reader) {
		user.setLastAliveAck(Instant.now());
	}

}
