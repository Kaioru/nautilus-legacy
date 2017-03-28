package co.kaioru.nautilus.login.handler;

import co.kaioru.nautilus.login.packet.LoginStructures;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.packet.IPacketHandler;
import co.kaioru.nautilus.server.packet.IPacketReader;

public class CheckUserLimitHandler implements IPacketHandler {

	@Override
	public void handle(RemoteUser user, IPacketReader reader) {
		// TODO: Proper limits
		user.sendPacket(LoginStructures.getCheckUserLimitResult(() -> (byte) 0));
	}

}
