package co.kaioru.nautilus.login.handler;

import co.kaioru.nautilus.login.packet.LoginStructures;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.packet.IPacketHandler;
import co.kaioru.nautilus.server.packet.IPacketReader;

public class CheckDuplicatedIDHandler implements IPacketHandler {

	@Override
	public void handle(RemoteUser user, IPacketReader reader) {
		// TODO: Proper checks
		String name = reader.readString();
		user.sendPacket(LoginStructures.getCheckDuplicatedIdResult(name, () -> (byte) 0));
	}

}
