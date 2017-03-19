import co.kaioru.nautilus.orm.account.Account;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.assertEquals;

public class ORMTest {

	@Test
	public void test() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("co.kaioru.nautilus.orm.jpa-test");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		Account account = new Account();

		account.setIdentity(2);
		entityManager.getTransaction().begin();
		entityManager.persist(account);
		entityManager.getTransaction().commit();

		assertEquals(entityManager.find(Account.class, 1).getIdentity(), 2);
	}

}
