package co.kaioru.nautilus.core.user;

import co.kaioru.nautilus.core.model.Account;
import co.kaioru.nautilus.core.model.Character;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class User implements IUser {

	private Account account;
	private Character character;

}
