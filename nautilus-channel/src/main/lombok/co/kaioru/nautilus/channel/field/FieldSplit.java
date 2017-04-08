package co.kaioru.nautilus.channel.field;

import co.kaioru.nautilus.core.field.IFieldInstance;
import co.kaioru.nautilus.core.field.IFieldObject;
import co.kaioru.nautilus.core.field.IFieldSplit;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class FieldSplit implements IFieldSplit {

	private final IFieldInstance fieldInstance;
	private final int index, row, col;
	private final Set<IFieldObject> fieldObjects;

	public FieldSplit(IFieldInstance fieldInstance, int index, int row, int col) {
		this.fieldInstance = fieldInstance;
		this.index = index;
		this.row = row;
		this.col = col;
		this.fieldObjects = Sets.newConcurrentHashSet();
	}

	@Override
	public boolean enter(IFieldObject fieldObject) {
		fieldObjects.add(fieldObject);
		if (fieldObject instanceof FieldObject) {
			FieldObject object = (FieldObject) fieldObject;
			object.sendPacketRemote(object.getEnterFieldPacket());
		}
		return true;
	}

	@Override
	public boolean leave(IFieldObject fieldObject) {
		fieldObjects.remove(fieldObject);
		if (fieldObject instanceof FieldObject) {
			FieldObject object = (FieldObject) fieldObject;
			object.sendPacketRemote(object.getLeaveFieldPacket());
		}
		return true;
	}

}
