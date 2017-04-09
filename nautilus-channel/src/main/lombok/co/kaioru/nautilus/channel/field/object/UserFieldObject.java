package co.kaioru.nautilus.channel.field.object;

import co.kaioru.nautilus.channel.field.FieldObject;
import co.kaioru.nautilus.channel.packet.UserStructures;
import co.kaioru.nautilus.core.field.IFieldInstance;
import co.kaioru.nautilus.core.user.User;
import co.kaioru.nautilus.orm.account.Character;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.packet.IPacket;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFieldObject extends FieldObject {

	private final User user;
	private final Character character;

	public UserFieldObject(IFieldInstance fieldInstance, User user) {
		super(fieldInstance);
		this.user = user;
		this.character = user.getCharacter();
	}

	@Override
	public IPacket getEnterFieldPacket() {
		return null;
	}

	@Override
	public IPacket getLeaveFieldPacket() {
		return null;
	}

	public void sendPacketLocal(IPacket packet) {
		if (user instanceof RemoteUser)
			((RemoteUser) user).sendPacket(packet);
	}

	public void chat(String message, boolean gm, boolean show) {
		sendPacketCommon(UserStructures.getUserChat(getObjectId(), message, gm, show));
	}

}
