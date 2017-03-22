package co.kaioru.nautilus.login.packet;

import co.kaioru.nautilus.server.game.packet.Packet;
import co.kaioru.nautilus.server.game.packet.PacketBuilder;

public class LoginStructures {

	public static Packet getCheckPasswordResult(byte reason) {
		return PacketBuilder.create(LoginSendOperations.CHECK_PASSWORD_RESULT)
			.writeByte(reason)
			.writeByte(0)
			.writeInt(0)
			.build();
	}

}
