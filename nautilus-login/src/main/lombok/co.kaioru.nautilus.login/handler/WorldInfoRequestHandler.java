package co.kaioru.nautilus.login.handler;

import co.kaioru.nautilus.login.LoginServerImpl;
import co.kaioru.nautilus.login.packet.LoginStructures;
import co.kaioru.nautilus.server.game.packet.IPacketHandler;
import co.kaioru.nautilus.server.game.packet.PacketReader;
import co.kaioru.nautilus.server.game.user.RemoteUser;

import java.rmi.RemoteException;

public class WorldInfoRequestHandler implements IPacketHandler {

	@Override
	public void handle(RemoteUser remoteUser, PacketReader reader) {
		LoginServerImpl.getInstance().getClusters().forEach(c -> {
			try {
				remoteUser.sendPacket(LoginStructures.getWorldInfoResult(c));
			} catch (RemoteException e) {
				e.printStackTrace(); // TODO: log
			}
		});
		remoteUser.sendPacket(LoginStructures.getWorldInfoResultEnd());
	}

}
