package co.kaioru.nautilus.login.handler;

import co.kaioru.nautilus.core.util.IValue;
import co.kaioru.nautilus.login.packet.LoginStructures;
import co.kaioru.nautilus.orm.account.Account;
import co.kaioru.nautilus.orm.account.AccountState;
import co.kaioru.nautilus.orm.account.Account_;
import co.kaioru.nautilus.orm.auth.IAuthenticator;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.packet.IPacketHandler;
import co.kaioru.nautilus.server.packet.IPacketReader;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class CheckPasswordHandler implements IPacketHandler {

	private final EntityManager entityManager;
	private final IAuthenticator authenticator;

	public CheckPasswordHandler(EntityManager entityManager, IAuthenticator authenticator) {
		this.entityManager = entityManager;
		this.authenticator = authenticator;
	}

	@Override
	public void handle(RemoteUser user, IPacketReader reader) {
		String username = reader.readString();
		String password = reader.readString();

		int identityId = authenticator.authenticate(username, password);

		if (identityId > 0) {
			Account account;

			entityManager.clear();

			try {
				CriteriaBuilder builder = entityManager.getCriteriaBuilder();
				CriteriaQuery<Account> accountCriteriaQuery = builder.createQuery(Account.class);
				Root<Account> accountRoot = accountCriteriaQuery.from(Account.class);

				accountCriteriaQuery.select(accountRoot);
				accountCriteriaQuery.where(builder.equal(accountRoot.get(Account_.identity), identityId));

				account = entityManager.createQuery(accountCriteriaQuery).getSingleResult();
			} catch (NoResultException e) {
				account = new Account();

				account.setIdentity(identityId);
			}

			if (account.getState() == AccountState.MIGRATING) {
				Instant last = Instant.ofEpochMilli(account.getLastMigrationTime().getTime());

				if (ChronoUnit.SECONDS.between(last, Instant.now()) > 30)
					account.setState(AccountState.LOGGED_OUT);
			}

			if (account.getState() == AccountState.LOGGED_OUT) {
				account.setState(AccountState.LOGGED_IN);
				account.setLastMigrationTime(Date.from(Instant.now()));

				entityManager.getTransaction().begin();
				account = entityManager.merge(account);
				entityManager.getTransaction().commit();

				user.setAccount(account);
				user.sendPacket(LoginStructures.getCheckPasswordSuccess(account));
			} else user.sendPacket(LoginStructures.getCheckPasswordResult(CheckPasswordResult.ALREADY_LOGGED_IN));
			return;
		}

		user.sendPacket(LoginStructures.getCheckPasswordResult(CheckPasswordResult.INVALID_PASSWORD));
	}

	public enum CheckPasswordResult implements IValue<Byte> {

		ENABLE_BUTTON((byte) 1),
		BLOCKED_ACCOUNT((byte) 3),
		INVALID_PASSWORD((byte) 4),
		INVALID_USERNAME((byte) 5),
		SYSTEM_ERROR((byte) 6),
		ALREADY_LOGGED_IN((byte) 7),
		CONNECTION_ERROR((byte) 8),
		SYSTEM_ERROR_2((byte) 9),
		TOO_MANY_REQUESTS((byte) 10);

		private final byte value;

		CheckPasswordResult(byte value) {
			this.value = value;
		}

		@Override
		public Byte getValue() {
			return value;
		}

	}

}
