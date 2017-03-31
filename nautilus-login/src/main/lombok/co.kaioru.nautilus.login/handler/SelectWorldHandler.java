package co.kaioru.nautilus.login.handler;

import co.kaioru.nautilus.login.LoginServerImpl;
import co.kaioru.nautilus.login.packet.LoginStructures;
import co.kaioru.nautilus.orm.account.Character;
import co.kaioru.nautilus.server.IDaemon;
import co.kaioru.nautilus.server.game.IChannelServer;
import co.kaioru.nautilus.server.game.IWorldCluster;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.packet.IPacketHandler;
import co.kaioru.nautilus.server.packet.IPacketReader;

import java.rmi.RemoteException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

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
				.filter(d -> filterById.test(d, worldId))
				.findFirst()
				.orElseThrow(NoSuchElementException::new);
			IChannelServer channel = cluster.getChannelServers()
				.stream()
				.filter(d -> filterById.test(d, channelId))
				.findFirst()
				.orElseThrow(NoSuchElementException::new);
			List<Character> characters = user.getAccount().getCharacters()
				.stream()
				.filter(c -> c.getWorld() == worldId)
				.collect(Collectors.toList());

			user.setWorldCluster(cluster);
			user.setChannelServer(channel);
			user.sendPacket(LoginStructures.getSelectWorldSuccess(characters));
		} catch (RemoteException | NoSuchElementException e) {
			user.close();
		}
	}

}
