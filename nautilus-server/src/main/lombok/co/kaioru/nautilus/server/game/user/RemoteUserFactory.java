package co.kaioru.nautilus.server.game.user;

import co.kaioru.nautilus.orm.account.Account;
import co.kaioru.nautilus.orm.account.AccountState;
import co.kaioru.nautilus.orm.account.Character;
import co.kaioru.nautilus.server.IServer;
import co.kaioru.nautilus.server.config.ServerConfig;
import co.kaioru.nautilus.server.migration.IServerMigration;
import co.kaioru.nautilus.server.packet.game.SocketStructures;
import io.netty.channel.Channel;

import javax.persistence.EntityManager;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.time.Instant;

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
				Account account = getAccount();

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
