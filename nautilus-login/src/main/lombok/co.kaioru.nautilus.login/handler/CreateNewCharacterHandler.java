package co.kaioru.nautilus.login.handler;

import co.kaioru.nautilus.login.packet.LoginStructures;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.packet.IPacketHandler;
import co.kaioru.nautilus.server.packet.IPacketReader;

import javax.persistence.EntityManager;

public class CreateNewCharacterHandler implements IPacketHandler {

	private final EntityManager entityManager;

	public CreateNewCharacterHandler(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void handle(RemoteUser user, IPacketReader reader) {
		user.sendPacket(LoginStructures.getCreateNewCharacterFailed());
	}

}
