package co.kaioru.nautilus.login.handler;

import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.packet.IPacketHandler;
import co.kaioru.nautilus.server.packet.IPacketReader;

public class LogoutWorldHandler implements IPacketHandler {

	@Override
	public void handle(RemoteUser user, IPacketReader reader) {
		user.setWorldCluster(null);
		user.setChannelServer(null);
	}

}
