package co.kaioru.nautilus.login.handler;

import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.packet.IServerPacketHandler;
import co.kaioru.nautilus.core.packet.IPacketReader;

public class LogoutWorldHandler implements IServerPacketHandler {

	@Override
	public void handle(RemoteUser user, IPacketReader reader) {
		user.setWorldCluster(null);
		user.setChannelServer(null);
	}

}
