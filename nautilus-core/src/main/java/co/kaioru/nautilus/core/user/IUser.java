package co.kaioru.nautilus.core.user;

import co.kaioru.nautilus.orm.Account;
import co.kaioru.nautilus.orm.Character;

public interface IUser {

	Account getAccount();

	Character getCharacter();

}
