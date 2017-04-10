package co.kaioru.nautilus.login.handler;

import co.kaioru.nautilus.login.LoginServerApplication;
import co.kaioru.nautilus.login.packet.LoginStructures;
import co.kaioru.nautilus.orm.account.Character;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.packet.IServerPacketHandler;
import co.kaioru.nautilus.core.packet.IPacketReader;
import com.google.common.collect.Maps;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ViewAllCharHandler implements IServerPacketHandler {

	@Override
	public void handle(RemoteUser user, IPacketReader reader) {
		Map<Integer, List<Character>> characters = Maps.newHashMap();

		LoginServerApplication.getInstance().getClusters()
			.forEach(w -> {
				try {
					int worldId = w.getConfig().getId();
					characters.put(worldId, user.getAccount().getCharacters()
						.stream()
						.filter(c -> c.getWorld() == worldId)
						.collect(Collectors.toList()));
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			});

		user.sendPacket(LoginStructures.getViewAllCharResultStart(characters.values()
			.stream()
			.mapToInt(List::size)
			.sum()));
		characters.forEach((w, c) -> user.sendPacket(LoginStructures.getViewAllCharResult(w, c)));
	}

}
