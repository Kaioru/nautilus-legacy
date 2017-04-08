package co.kaioru.nautilus.channel.field;

import co.kaioru.nautilus.channel.field.object.UserFieldObject;
import co.kaioru.nautilus.core.field.IFieldInstance;
import co.kaioru.nautilus.core.field.IFieldObject;
import co.kaioru.nautilus.core.field.IFieldSplit;
import co.kaioru.nautilus.server.packet.IPacket;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Getter
@Setter
public abstract class FieldObject implements IFieldObject {

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

	public abstract IPacket getEnterFieldPacket();

	public abstract IPacket getLeaveFieldPacket();

	public Stream<IFieldObject> getViewableObjects() {
		return fieldSplits.stream()
			.flatMap(s -> s.getFieldObjects().stream())
			.distinct();
	}

	public Optional<IFieldObject> getViewableObject(int objectId) {
		return getViewableObjects()
			.filter(o -> o.getObjectId() == objectId)
			.findFirst();
	}

	public void sendPacketRemote(IPacket packet) {
		getViewableObjects()
			.filter(o -> !o.equals(this))
			.filter(o -> o instanceof UserFieldObject)
			.map(o -> (UserFieldObject) o)
			.forEach(o -> o.sendPacketLocal(packet));
	}

	public void sendPacketCommon(IPacket packet) {
		getViewableObjects()
			.filter(o -> o instanceof UserFieldObject)
			.map(o -> (UserFieldObject) o)
			.forEach(o -> o.sendPacketLocal(packet));
	}

}
