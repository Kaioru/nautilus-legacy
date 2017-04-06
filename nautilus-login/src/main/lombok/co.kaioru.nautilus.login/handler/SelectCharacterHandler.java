package co.kaioru.nautilus.login.handler;

import co.kaioru.nautilus.login.packet.LoginStructures;
import co.kaioru.nautilus.orm.account.Character;
import co.kaioru.nautilus.server.game.IChannelServer;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.packet.IPacketHandler;
import co.kaioru.nautilus.server.packet.IPacketReader;

import java.net.InetAddress;
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

			InetAddress address = InetAddress.getByName(server.getConfig().getHost());
			short port = server.getConfig().getPort();

			user.migrateOut(server);
			user.sendPacket(LoginStructures.getSelectCharacterSuccess(address, port, characterId));
		} catch (Exception e) {
			e.printStackTrace();
			user.close();
		}
	}

}
