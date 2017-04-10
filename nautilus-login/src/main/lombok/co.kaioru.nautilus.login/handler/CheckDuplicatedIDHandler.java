package co.kaioru.nautilus.login.handler;

import co.kaioru.nautilus.core.packet.IPacketReader;
import co.kaioru.nautilus.login.packet.LoginStructures;
import co.kaioru.nautilus.orm.account.Character;
import co.kaioru.nautilus.orm.account.Character_;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.packet.IServerPacketHandler;
import lombok.Getter;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.function.BiPredicate;

public class CheckDuplicatedIDHandler implements IServerPacketHandler {

	@Getter
	private static final BiPredicate<EntityManager, String> predicate = (em, name) -> {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Character> characterCriteriaQuery = builder.createQuery(Character.class);
		Root<Character> characterRoot = characterCriteriaQuery.from(Character.class);

		characterCriteriaQuery.select(characterRoot);
		characterCriteriaQuery.where(builder.equal(characterRoot.get(Character_.name), name));

		return em.createQuery(characterCriteriaQuery).getResultList().size() == 0;
	};

	private final EntityManager entityManager;

	public CheckDuplicatedIDHandler(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void handle(RemoteUser user, IPacketReader reader) {
		String name = reader.readString();

		int result = getPredicate().test(entityManager, name) ? 0 : 1;
		user.sendPacket(LoginStructures.getCheckDuplicatedIdResult(name, () -> (byte) result));
	}

}
