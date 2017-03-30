package co.kaioru.nautilus.orm.auth;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class BasicAuthenticator implements IAuthenticator {

	private final EntityManager entityManager;

	public BasicAuthenticator(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public int authenticate(String username, String password) {
		int identityId = 0;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Identity> identityCriteriaQuery = builder.createQuery(Identity.class);
			Root<Identity> identityRoot = identityCriteriaQuery.from(Identity.class);

			identityCriteriaQuery.select(identityRoot);
			identityCriteriaQuery.where(builder.equal(identityRoot.get(Identity_.username), username));

			Identity identity = entityManager.createQuery(identityCriteriaQuery).getSingleResult();

			if (identity.getPassword().equals(password))
				identityId = identity.getId();
		} finally {
			return identityId;
		}
	}

}
