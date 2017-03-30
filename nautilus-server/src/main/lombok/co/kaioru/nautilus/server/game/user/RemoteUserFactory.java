package co.kaioru.nautilus.server.game.user;

import co.kaioru.nautilus.orm.account.Account;
import co.kaioru.nautilus.orm.account.AccountState;
import io.netty.channel.Channel;

import javax.persistence.EntityManager;

public class RemoteUserFactory implements IRemoteUserFactory {

	private final EntityManager entityManager;

	public RemoteUserFactory(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public RemoteUser create(Channel channel) {
		return new RemoteUser(channel) {

			@Override
			public void close() {
				Account account = getAccount();

				if (account != null) {
					account.setState(AccountState.LOGGED_OFF);
					entityManager.getTransaction().begin();
					entityManager.merge(account);
					entityManager.getTransaction().commit();
				}
			}

		};
	}

}
