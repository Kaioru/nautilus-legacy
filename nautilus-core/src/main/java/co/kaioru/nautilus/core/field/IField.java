package co.kaioru.nautilus.core.field;

import co.kaioru.nautilus.core.field.template.FieldTemplate;
import co.kaioru.nautilus.core.user.User;

public interface IField {

	FieldTemplate getTemplate();

	IFieldInstance getFieldInstance();

	boolean enter(User user, int spawnPoint);

	boolean leave(User user);

}
