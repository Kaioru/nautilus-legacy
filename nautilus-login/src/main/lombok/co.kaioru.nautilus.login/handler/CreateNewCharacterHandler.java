package co.kaioru.nautilus.login.handler;

import co.kaioru.nautilus.login.packet.LoginStructures;
import co.kaioru.nautilus.orm.account.Account;
import co.kaioru.nautilus.orm.account.Character;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.packet.IServerPacketHandler;
import co.kaioru.nautilus.core.packet.IPacketReader;

import javax.persistence.EntityManager;

public class CreateNewCharacterHandler implements IServerPacketHandler {

	private final EntityManager entityManager;

	public CreateNewCharacterHandler(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void handle(RemoteUser user, IPacketReader reader) {
		try {
			String name = reader.readString();

			int face = reader.readInt();
			int hair = reader.readInt();
			int hairColor = reader.readInt();
			int skin = reader.readInt();

			int coat = reader.readInt();
			int pants = reader.readInt();
			int shoes = reader.readInt();
			int weapon = reader.readInt();
			byte gender = reader.readByte();

			byte str = reader.readByte();
			byte dex = reader.readByte();
			byte _int = reader.readByte();
			byte luk = reader.readByte();
			int total = str + dex + _int + luk;

			if (CheckDuplicatedIDHandler.getPredicate().test(entityManager, name)) {
				Account account = user.getAccount();
				Character character = new Character(account);
				character.setWorld(user.getWorldCluster().getConfig().getId());
				character.setName(name);
				character.setFace(face);
				character.setHair(hair + hairColor);
				character.setSkin((byte) skin);
				character.setGender(gender);

				account.getCharacters().add(character);

				// EquipInventory equipped = character.getEquippedInventory();
				// TODO

				entityManager.getTransaction().begin();
				entityManager.persist(character);
				entityManager.merge(account);
				entityManager.getTransaction().commit();

				// Hacky but w/e it works
				character = account.getCharacters().get(account.getCharacters().size() - 1);

				user.sendPacket(LoginStructures.getCreateNewCharacterSuccess(character));
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		user.sendPacket(LoginStructures.getCreateNewCharacterFailed());
	}

}
