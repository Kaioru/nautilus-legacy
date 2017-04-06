package co.kaioru.nautilus.login.handler;

import co.kaioru.nautilus.login.LoginServerApplication;
import co.kaioru.nautilus.login.packet.LoginStructures;
import co.kaioru.nautilus.orm.account.Character;
import co.kaioru.nautilus.server.IDaemon;
import co.kaioru.nautilus.server.game.IChannelServer;
import co.kaioru.nautilus.server.game.IWorldCluster;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.packet.IPacketHandler;
import co.kaioru.nautilus.server.packet.IPacketReader;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.util.NoSuchElementException;
import java.util.function.BiPredicate;

public class SelectCharacterByVACHandler implements IPacketHandler {

	@Override
	public void handle(RemoteUser user, IPacketReader reader) {
		int characterId = reader.readInt();
		int worldId = reader.readInt();

		try {
			BiPredicate<IDaemon, Integer> filterById = (d, i) -> {
				try {
					return d.getConfig().getId() == i;
				} catch (RemoteException e) {
					return false;
				}
			};
			IWorldCluster cluster = LoginServerApplication.getInstance().getClusters()
				.stream()
				.filter(d -> filterById.test(d, worldId))
				.findFirst()
				.orElseThrow(NoSuchElementException::new);
			IChannelServer channel = cluster.getChannelServers()
				.stream()
				.findFirst()
				.orElseThrow(NoSuchElementException::new);
			Character character = user.getAccount().getCharacters()
				.stream()
				.filter(c -> c.getId() == characterId)
				.findFirst()
				.orElseThrow(NoSuchElementException::new);
			InetAddress address = InetAddress.getByName(channel.getConfig().getHost());
			short port = channel.getConfig().getPort();

			user.setWorldCluster(cluster);
			user.setChannelServer(channel);
			user.setCharacter(character);
			user.migrateOut(channel);
			user.sendPacket(LoginStructures.getSelectCharacterSuccess(address, port, characterId));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
