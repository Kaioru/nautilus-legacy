package co.kaioru.nautilus.core.user;

import co.kaioru.nautilus.orm.Account;
import co.kaioru.nautilus.orm.Character;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class User implements IUser {

	private Account account;
	private Character character;

}
