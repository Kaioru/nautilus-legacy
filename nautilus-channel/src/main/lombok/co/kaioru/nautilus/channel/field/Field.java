package co.kaioru.nautilus.channel.field;

import co.kaioru.nautilus.core.field.IField;
import co.kaioru.nautilus.core.field.IFieldInstance;
import co.kaioru.nautilus.core.field.template.FieldTemplate;
import co.kaioru.nautilus.core.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Field implements IField {

	private final FieldTemplate template;
	private final IFieldInstance fieldInstance;

	public Field(FieldTemplate template, IFieldInstance fieldInstance) {
		this.template = template;
		this.fieldInstance = fieldInstance;
	}

	@Override
	public boolean enter(User user) {
		return fieldInstance.enter(user);
	}

	@Override
	public boolean leave(User user) {
		return fieldInstance.leave(user);
	}

}
