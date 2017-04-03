package co.kaioru.nautilus.channel.field.object;

import co.kaioru.nautilus.channel.field.FieldObject;
import co.kaioru.nautilus.core.field.IFieldInstance;
import co.kaioru.nautilus.core.user.User;
import co.kaioru.nautilus.orm.account.Character;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterFieldObject extends FieldObject {

	private final User user;
	private final Character character;

	public CharacterFieldObject(IFieldInstance fieldInstance, User user) {
		super(fieldInstance);
		this.user = user;
		this.character = user.getCharacter();
	}

}
