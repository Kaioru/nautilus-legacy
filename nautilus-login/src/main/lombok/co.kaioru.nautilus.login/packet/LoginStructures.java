package co.kaioru.nautilus.login.packet;

import co.kaioru.nautilus.core.util.IValue;
import co.kaioru.nautilus.orm.account.Account;
import co.kaioru.nautilus.server.game.packet.Packet;
import co.kaioru.nautilus.server.game.packet.PacketBuilder;

public class LoginStructures {

	public static Packet getCheckPasswordResult(IValue<Byte> result) {
		return PacketBuilder.create(LoginSendOperations.CHECK_PASSWORD_RESULT)
			.writeByte(result.getValue())
			.writeByte(0)
			.writeInt(0)
			.build();
	}

	public static Packet getCheckPasswordSuccess(Account account) {
		return PacketBuilder.create(LoginSendOperations.CHECK_PASSWORD_RESULT)
			.writeByte(0)
			.writeByte(0)
			.writeInt(0)

			.writeInt(account.getId())
			.writeByte(0) // Gender
			.writeBool(false) // GM?
			.writeByte(0) // GM?
			.writeMapleString(String.valueOf(account.getIdentity())) // TODO: Account name
			.writeByte(0)
			.writeByte(0)
			.writeLong(0)
			.writeLong(0)
			.writeInt(0)
			.writeShort(0)
			.build();
	}

}
