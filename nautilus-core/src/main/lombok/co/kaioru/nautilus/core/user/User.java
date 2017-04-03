package co.kaioru.nautilus.core.user;

import co.kaioru.nautilus.core.field.IField;
import co.kaioru.nautilus.orm.account.Account;
import co.kaioru.nautilus.orm.account.Character;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class User implements IUser {

	private Account account;
	private Character character;
	private IField field;

}
