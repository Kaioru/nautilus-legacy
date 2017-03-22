package co.kaioru.nautilus.login.packet;

import co.kaioru.nautilus.core.util.IValue;
import co.kaioru.nautilus.orm.account.Account;
import co.kaioru.nautilus.server.game.IChannelServer;
import co.kaioru.nautilus.server.game.IWorldCluster;
import co.kaioru.nautilus.server.game.config.WorldConfig;
import co.kaioru.nautilus.server.game.packet.Packet;
import co.kaioru.nautilus.server.game.packet.PacketBuilder;

import java.rmi.RemoteException;
import java.util.Collection;

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

	public static Packet getWorldInfoResult(IWorldCluster world) throws RemoteException {
		WorldConfig config = world.getConfig();
		return PacketBuilder.create(LoginSendOperations.WORLD_INFO_RESULT)
			.writeByte(0) // world id
			.writeMapleString(config.getName())
			.writeByte(0) // world flag
			.writeMapleString("") // world message

			.writeShort(0x64)
			.writeShort(0x64)
			.writeByte(0x00)

			.writeByte(0) // Channel stuff

			.writeShort(0) // Dialogues
			.build();
	}

	public static Packet getWorldInfoResultEnd() {
		return PacketBuilder.create(LoginSendOperations.WORLD_INFO_RESULT)
			.writeByte(0xFF)
			.build();
	}

}
