package co.kaioru.nautilus.core.user;

import co.kaioru.nautilus.core.field.IField;
import co.kaioru.nautilus.orm.account.Account;
import co.kaioru.nautilus.orm.account.Character;

public interface IUser {

	Account getAccount();

	Character getCharacter();

	IField getField();

}
