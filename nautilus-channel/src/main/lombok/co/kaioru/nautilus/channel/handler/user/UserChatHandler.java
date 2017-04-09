package co.kaioru.nautilus.channel.handler.user;

import co.kaioru.nautilus.channel.field.object.UserFieldObject;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.packet.IPacketHandler;
import co.kaioru.nautilus.server.packet.IPacketReader;

public class UserChatHandler implements IPacketHandler {

	@Override
	public void handle(RemoteUser user, IPacketReader reader) {
		String message = reader.readString();
		boolean show = reader.readByte() > 0;

		if (user.getFieldObject() instanceof UserFieldObject) {
			UserFieldObject fieldObject = ((UserFieldObject) user.getFieldObject());

			fieldObject.chat(message, false, show);
		}
	}

}
