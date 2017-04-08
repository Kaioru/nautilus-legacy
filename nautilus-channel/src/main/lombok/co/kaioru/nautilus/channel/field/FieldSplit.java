package co.kaioru.nautilus.channel.field;

import co.kaioru.nautilus.core.field.IFieldInstance;
import co.kaioru.nautilus.core.field.IFieldObject;
import co.kaioru.nautilus.core.field.IFieldSplit;
import co.kaioru.nautilus.core.field.template.FieldTemplate;
import co.kaioru.nautilus.core.user.User;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FieldSplit implements IFieldSplit {

	private final IFieldInstance fieldInstance;
	private final int index, row, col;
	private final List<IFieldObject> fieldObjects;

	public FieldSplit(IFieldInstance fieldInstance, int index, int row, int col) {
		this.fieldInstance = fieldInstance;
		this.index = index;
		this.row = row;
		this.col = col;
		this.fieldObjects = Lists.newArrayList();
	}

	@Override
	public FieldTemplate getTemplate() {
		return fieldInstance.getTemplate();
	}

	@Override
	public boolean enter(User user, int spawnPoint) {
		return false;
	}

	@Override
	public boolean leave(User user, int spawnPoint) {
		return false;
	}

}
