package co.kaioru.nautilus.channel.field;

import co.kaioru.nautilus.core.field.IFieldInstance;
import co.kaioru.nautilus.core.field.IFieldObject;
import co.kaioru.nautilus.core.field.IFieldSplit;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.List;

@Getter
@Setter
public class FieldObject implements IFieldObject {

	private final IFieldInstance fieldInstance;
	private final List<IFieldSplit> fieldSplits;
	private final int objectId;
	private final Point position;

	public FieldObject(IFieldInstance fieldInstance) {
		this.fieldInstance = fieldInstance;
		this.fieldSplits = Lists.newArrayList();
		this.objectId = fieldInstance.getAvailableObjectId();
		this.position = new Point();
	}

}
