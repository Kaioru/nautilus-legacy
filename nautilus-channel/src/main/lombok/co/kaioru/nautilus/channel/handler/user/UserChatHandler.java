package co.kaioru.nautilus.channel.handler.user;

import co.kaioru.nautilus.channel.ChannelServerApplication;
import co.kaioru.nautilus.channel.field.object.UserFieldObject;
import co.kaioru.nautilus.core.command.CommandRegistry;
import co.kaioru.nautilus.core.packet.IPacketReader;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.packet.IServerPacketHandler;
import co.kaioru.retort.util.CommandUtil;

public class UserChatHandler implements IServerPacketHandler {

	@Override
	public void handle(RemoteUser user, IPacketReader reader) {
		String message = reader.readString();
		boolean show = reader.readByte() > 0;

		if (user.getFieldObject() instanceof UserFieldObject) {
			UserFieldObject fieldObject = ((UserFieldObject) user.getFieldObject());

			if (message.startsWith("/") || message.startsWith("!") || message.startsWith("@")) {
				CommandRegistry registry = ChannelServerApplication.getInstance().getCommandRegistry();
				String commandString = message.substring(1);

				try {
					registry.execute(CommandUtil.getArgsFromText(commandString), user);
				} catch (Exception e) {
					e.printStackTrace(); // TODO: handle
				}
				return;
			}

			fieldObject.chat(message, false, show);
		}
	}

}
