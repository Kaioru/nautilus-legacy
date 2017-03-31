package co.kaioru.nautilus.server.game.user;

import co.kaioru.nautilus.orm.account.Account;
import co.kaioru.nautilus.orm.account.AccountState;
import co.kaioru.nautilus.orm.account.Character;
import co.kaioru.nautilus.server.IServer;
import co.kaioru.nautilus.server.config.ServerConfig;
import co.kaioru.nautilus.server.migration.ServerMigration;
import co.kaioru.nautilus.server.packet.game.SocketStructures;
import io.netty.channel.Channel;

import javax.persistence.EntityManager;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

public class RemoteUserFactory implements IRemoteUserFactory {

	private final EntityManager entityManager;

	public RemoteUserFactory(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public RemoteUser create(Channel channel) {
		return new RemoteUser(channel) {

			@Override
			public void migrate(IServer<?, ? extends ServerConfig> server) throws RemoteException, UnknownHostException {
				InetAddress serverAddress = InetAddress.getByName(server.getConfig().getHost());
				short serverPort = server.getConfig().getPort();
				Account account = getAccount();
				Character character = getCharacter();

				account.setState(AccountState.MIGRATING);
				entityManager.getTransaction().begin();
				entityManager.merge(account);
				entityManager.getTransaction().commit();

				server.registerServerMigration(new ServerMigration(account.getId(), character.getId()));
				sendPacket(SocketStructures.getMigrateCommand(serverAddress, serverPort));
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
			}

		};
	}

}
