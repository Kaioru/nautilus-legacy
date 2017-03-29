package co.kaioru.nautilus.login.handler;

import co.kaioru.nautilus.login.LoginServerImpl;
import co.kaioru.nautilus.login.packet.LoginStructures;
import co.kaioru.nautilus.server.IDaemon;
import co.kaioru.nautilus.server.game.IChannelServer;
import co.kaioru.nautilus.server.game.IWorldCluster;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.packet.IPacketHandler;
import co.kaioru.nautilus.server.packet.IPacketReader;

import java.rmi.RemoteException;
import java.util.NoSuchElementException;
import java.util.function.BiPredicate;

public class SelectWorldHandler implements IPacketHandler {

	@Override
	public void handle(RemoteUser user, IPacketReader reader) {
		int worldId = reader.readByte();
		int channelId = reader.readByte();

		try {
			BiPredicate<IDaemon, Integer> filterById = (d, i) -> {
				try {
					return d.getConfig().getId() == i;
				} catch (RemoteException e) {
					return false;
				}
			};
			IWorldCluster cluster = LoginServerImpl.getInstance().getClusters()
				.stream()
				.filter((c) -> filterById.test(c, worldId))
				.findFirst()
				.orElseThrow(() -> new NoSuchElementException());
			IChannelServer channel = cluster.getChannelServers()
				.stream()
				.filter((c) -> filterById.test(c, channelId))
				.findFirst()
				.orElseThrow(() -> new NoSuchElementException());

			user.setWorldCluster(cluster);
			user.setChannelServer(channel);
			user.sendPacket(LoginStructures.getSelectWorldSuccess(user.getAccount()));
		} catch (RemoteException | NoSuchElementException e) {
			user.close();
		}
	}

}
