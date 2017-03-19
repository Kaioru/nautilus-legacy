package co.kaioru.nautilus.orm.auth;

import co.kaioru.nautilus.orm.account.Account;
import co.kaioru.nautilus.orm.account.Account_;
import lombok.Cleanup;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class BasicAuthenticator implements IAuthenticator {

	private final EntityManagerFactory entityManagerFactory;

	public BasicAuthenticator(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	@Override
	public Optional<Account> authenticate(String username, String password) {
		@Cleanup EntityManager entityManager = entityManagerFactory.createEntityManager();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Identity> identityCriteriaQuery = builder.createQuery(Identity.class);
		Root<Identity> identityRoot = identityCriteriaQuery.from(Identity.class);

		identityCriteriaQuery.select(identityRoot);
		identityCriteriaQuery.where(builder.equal(identityRoot.get(Identity_.username), username));

		Identity identity = entityManager.createQuery(identityCriteriaQuery).getSingleResult();

		if (identity != null) {
			if (identity.getPassword().equals(password)) {
				CriteriaQuery<Account> accountCriteriaQuery = builder.createQuery(Account.class);
				Root<Account> accountRoot = accountCriteriaQuery.from(Account.class);

				accountCriteriaQuery.select(accountRoot);
				accountCriteriaQuery.where(builder.equal(accountRoot.get(Account_.identity), identity.getId()));

				Account account = entityManager.createQuery(accountCriteriaQuery).getSingleResult();

				if (account == null) {
					account = new Account();
					account.setIdentity(identity.getId());

					entityManager.getTransaction().begin();
					entityManager.persist(account);
					entityManager.getTransaction().commit();
				}

				return Optional.of(account);
			}
		}

		return Optional.empty();
	}

}
