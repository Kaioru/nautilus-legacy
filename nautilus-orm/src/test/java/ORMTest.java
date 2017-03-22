import co.kaioru.nautilus.orm.account.Account;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class ORMTest {

	@Test
	public void test() throws IOException {
		Properties properties = new Properties();

		properties.load(getClass().getResourceAsStream("database.test.properties"));

		EntityManagerFactory entityManagerFactory = Persistence
			.createEntityManagerFactory("co.kaioru.nautilus.orm.jpa", properties);
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		Account account = new Account();

		account.setIdentity(2);
		entityManager.getTransaction().begin();
		entityManager.persist(account);
		entityManager.getTransaction().commit();

		assertEquals(entityManager.find(Account.class, 1).getIdentity(), 2);
	}

}
