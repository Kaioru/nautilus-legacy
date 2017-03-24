package co.kaioru.nautilus.login.handler;

import co.kaioru.nautilus.login.LoginServerImpl;
import co.kaioru.nautilus.login.packet.LoginStructures;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.packet.IPacketHandler;
import co.kaioru.nautilus.server.packet.IPacketReader;

import java.rmi.RemoteException;

public class WorldInfoRequestHandler implements IPacketHandler {

	@Override
	public void handle(RemoteUser user, IPacketReader reader) {
		LoginServerImpl.getInstance().getClusters().forEach(c -> {
			try {
				user.sendPacket(LoginStructures.getWorldInfoResult(c));
			} catch (RemoteException e) {
				e.printStackTrace(); // TODO: log
			}
		});
		user.sendPacket(LoginStructures.getWorldInfoResultEnd());
	}

}
