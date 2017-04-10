package co.kaioru.nautilus.core.packet;

import co.kaioru.nautilus.core.user.User;

public interface IPacketHandler<U extends User> {

	default boolean validate(U user) {
		return true;
	}

	void handle(U user, IPacketReader reader);

}
