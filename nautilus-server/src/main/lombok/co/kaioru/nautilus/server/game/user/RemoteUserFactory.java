package co.kaioru.nautilus.server.game.user;

import co.kaioru.nautilus.core.field.IFieldObject;
import co.kaioru.nautilus.core.field.template.FieldPortalTemplate;
import co.kaioru.nautilus.core.field.template.FieldTemplate;
import co.kaioru.nautilus.orm.account.Account;
import co.kaioru.nautilus.orm.account.AccountState;
import co.kaioru.nautilus.orm.account.Character;
import co.kaioru.nautilus.server.IServer;
import co.kaioru.nautilus.server.migration.IServerMigration;
import io.netty.channel.Channel;

import javax.persistence.EntityManager;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.time.Instant;
import java.util.Comparator;

public class RemoteUserFactory implements IRemoteUserFactory {

	private final EntityManager entityManager;

	public RemoteUserFactory(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public RemoteUser create(Channel channel) {
		return new RemoteUser(channel) {

			@Override
			public void migrateOut(IServer server) throws RemoteException, UnknownHostException {
				Account account = getAccount();
				Character character = getCharacter();

				account.setState(AccountState.MIGRATING);
				account.setLastMigrationTime(Date.from(Instant.now()));
				entityManager.getTransaction().begin();
				entityManager.merge(account);
				entityManager.getTransaction().commit();

				server.registerMigration(getWorldCluster(), getChannelServer(), character.getId());
			}

			@Override
			public void migrateIn(IServer server, int characterId) throws Exception {
				IServerMigration migration = server.getServerMigration(characterId);

				if (migration != null) {
					if (!migration.isExpired()) {
						entityManager.clear();

						Character character = entityManager.find(Character.class, characterId);
						Account account = character.getAccount();

						setAccount(account);
						setCharacter(character);
						account.setState(AccountState.LOGGED_IN);

						entityManager.getTransaction().begin();
						entityManager.merge(account);
						entityManager.getTransaction().commit();

						server.deregisterMigration(characterId);
						return;
					}
				}

				close();
			}

			@Override
			public void close() {
				Character character = getCharacter();
				IFieldObject fieldObject = getFieldObject();
				Account account = getAccount();

				if (character != null) {
					if (fieldObject != null) {
						// TODO: Spawn point
						FieldTemplate fieldTemplate = fieldObject.getFieldInstance().getTemplate();
						int fieldId = fieldTemplate.getReturnMap();
						int spawnPoint = 0;

						if (fieldId == 999999999)
							fieldId = fieldTemplate.getTemplateID();

						character.setFieldId(fieldId);
						character.setSpawnPoint(spawnPoint);
					}
				}

				if (account != null) {
					if (account.getState() != AccountState.MIGRATING)
						account.setState(AccountState.LOGGED_OUT);
					entityManager.getTransaction().begin();
					entityManager.merge(account);
					entityManager.getTransaction().commit();
				}

				channel.close();
			}

		};
	}

}
