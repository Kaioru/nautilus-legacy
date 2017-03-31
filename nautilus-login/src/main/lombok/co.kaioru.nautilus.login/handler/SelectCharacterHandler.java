package co.kaioru.nautilus.login.handler;

import co.kaioru.nautilus.orm.account.Character;
import co.kaioru.nautilus.server.game.IChannelServer;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.packet.IPacketHandler;
import co.kaioru.nautilus.server.packet.IPacketReader;

import java.util.NoSuchElementException;

public class SelectCharacterHandler implements IPacketHandler {

	@Override
	public void handle(RemoteUser user, IPacketReader reader) {
		int characterId = reader.readInt();

		try {
			Character character = user.getAccount().getCharacters()
				.stream()
				.filter(c -> c.getId() == characterId)
				.findFirst()
				.orElseThrow(NoSuchElementException::new);
			IChannelServer server = user.getChannelServer();

			user.setCharacter(character);
			user.migrate(server);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
