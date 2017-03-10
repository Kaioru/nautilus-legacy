package co.kaioru.nautilus.core.user;

import co.kaioru.nautilus.core.model.Account;
import co.kaioru.nautilus.core.model.Character;

public interface IUser {

	Account getAccount();

	Character getCharacter();

}
