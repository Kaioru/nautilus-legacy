package co.kaioru.nautilus.server.packet.handler;

import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.packet.IPacketHandler;
import co.kaioru.nautilus.server.packet.IPacketReader;

import java.time.Instant;

public class AliveAckHandler implements IPacketHandler {

	@Override
	public void handle(RemoteUser user, IPacketReader reader) {
		user.setLastAliveAck(Instant.now());
	}

}
