package co.kaioru.nautilus.login.handler;

import co.kaioru.nautilus.server.game.IChannelServer;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.packet.IPacketHandler;
import co.kaioru.nautilus.server.packet.IPacketReader;
import co.kaioru.nautilus.server.packet.game.SocketStructures;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

public class SelectCharacterHandler implements IPacketHandler {

	@Override
	public void handle(RemoteUser user, IPacketReader reader) {
		IChannelServer server = user.getChannelServer();

		try {
			InetAddress address = InetAddress.getByName(server.getConfig().getHost());
			short port = server.getConfig().getPort();
			
			user.sendPacket(SocketStructures.getMigrateCommand(address, port));
		} catch (UnknownHostException | RemoteException e) {
			e.printStackTrace();
		}
	}

}
