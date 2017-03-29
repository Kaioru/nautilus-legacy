package co.kaioru.nautilus.orm.auth;

import co.kaioru.nautilus.orm.account.Account;
import co.kaioru.nautilus.orm.account.Account_;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class BasicAuthenticator implements IAuthenticator {

	private final EntityManager entityManager;

	public BasicAuthenticator(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Optional<Account> authenticate(String username, String password) {
		Account account = null;

		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Identity> identityCriteriaQuery = builder.createQuery(Identity.class);
			Root<Identity> identityRoot = identityCriteriaQuery.from(Identity.class);

			identityCriteriaQuery.select(identityRoot);
			identityCriteriaQuery.where(builder.equal(identityRoot.get(Identity_.username), username));

			Identity identity = entityManager.createQuery(identityCriteriaQuery).getSingleResult();

			if (identity.getPassword().equals(password)) {
				try {
					CriteriaQuery<Account> accountCriteriaQuery = builder.createQuery(Account.class);
					Root<Account> accountRoot = accountCriteriaQuery.from(Account.class);

					accountCriteriaQuery.select(accountRoot);
					accountCriteriaQuery.where(builder.equal(accountRoot.get(Account_.identity), identity.getId()));

					account = entityManager.createQuery(accountCriteriaQuery).getSingleResult();
				} catch (NoResultException e) {
					account = new Account();
					account.setIdentity(identity.getId());

					entityManager.getTransaction().begin();
					account = entityManager.merge(account);
					entityManager.getTransaction().commit();
				}
			}
		} finally {
			return Optional.ofNullable(account);
		}
	}

}
