package co.kaioru.nautilus.login.handler;

import co.kaioru.nautilus.login.packet.LoginStructures;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.packet.IServerPacketHandler;
import co.kaioru.nautilus.core.packet.IPacketReader;

public class CheckUserLimitHandler implements IServerPacketHandler {

	@Override
	public void handle(RemoteUser user, IPacketReader reader) {
		// TODO: Proper limits
		user.sendPacket(LoginStructures.getCheckUserLimitResult(() -> (byte) 0));
	}

}
