package co.kaioru.nautilus.channel.handler.user;

import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.packet.IPacketHandler;
import co.kaioru.nautilus.server.packet.IPacketReader;

import java.awt.*;

public class UserMoveHandler implements IPacketHandler {

	@Override
	public void handle(RemoteUser user, IPacketReader reader) {
		reader.readByte();
		reader.readInt();

		byte numCommands = reader.readByte();
		for (byte i = 0; i < numCommands; i++) {
			byte type = reader.readByte();
			if (i == 0) {
				Point position = new Point(reader.readShort(), reader.readShort());
				System.out.println(type + " " + position);
			}
		}
	}

}
