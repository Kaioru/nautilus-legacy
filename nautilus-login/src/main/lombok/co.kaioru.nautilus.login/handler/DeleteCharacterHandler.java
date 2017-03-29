package co.kaioru.nautilus.login.handler;

import co.kaioru.nautilus.login.packet.LoginStructures;
import co.kaioru.nautilus.orm.account.Account;
import co.kaioru.nautilus.orm.account.Character;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.packet.IPacketHandler;
import co.kaioru.nautilus.server.packet.IPacketReader;

import javax.persistence.EntityManager;
import java.util.NoSuchElementException;

public class DeleteCharacterHandler implements IPacketHandler {

	private final EntityManager entityManager;

	public DeleteCharacterHandler(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void handle(RemoteUser user, IPacketReader reader) {
		reader.readInt();
		int characterId = reader.readInt();

		try {
			Account account = user.getAccount();
			Character character = account.getCharacters()
				.stream()
				.filter(c -> c.getId() == characterId)
				.findFirst()
				.get();

			account.getCharacters().remove(character);

			entityManager.getTransaction().begin();
			entityManager.remove(character);
			entityManager.merge(account);
			entityManager.getTransaction().commit();

			user.sendPacket(LoginStructures.getDeleteCharacterResult(characterId, () -> (byte) 0));
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			user.sendPacket(LoginStructures.getDeleteCharacterResult(characterId, () -> (byte) 1));
		}
	}

}
