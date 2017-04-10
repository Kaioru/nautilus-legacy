package co.kaioru.nautilus.login.handler;

import co.kaioru.nautilus.core.packet.IPacketReader;
import co.kaioru.nautilus.login.LoginServerApplication;
import co.kaioru.nautilus.login.packet.LoginStructures;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.packet.IServerPacketHandler;

import java.rmi.RemoteException;

public class WorldInfoRequestHandler implements IServerPacketHandler {

	@Override
	public void handle(RemoteUser user, IPacketReader reader) {
		LoginServerApplication.getInstance().getClusters().forEach(c -> {
			try {
				user.sendPacket(LoginStructures.getWorldInfoResult(c));
			} catch (RemoteException e) {
				e.printStackTrace(); // TODO: log
			}
		});
		user.sendPacket(LoginStructures.getWorldInfoResultEnd());
	}

}
