package co.kaioru.nautilus.login.packet;

import co.kaioru.nautilus.core.util.IValue;
import co.kaioru.nautilus.orm.account.Account;
import co.kaioru.nautilus.server.game.IChannelServer;
import co.kaioru.nautilus.server.game.IWorldCluster;
import co.kaioru.nautilus.server.game.config.WorldConfig;
import co.kaioru.nautilus.server.packet.IPacket;
import co.kaioru.nautilus.server.packet.PacketBuilder;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Objects;

public class LoginStructures {

	public static IPacket getCheckPasswordResult(IValue<Byte> result) {
		return PacketBuilder.create(LoginSendOperations.CHECK_PASSWORD_RESULT)
			.writeByte(result.getValue())
			.writeByte(0)
			.writeInt(0)
			.build();
	}

	public static IPacket getCheckPasswordSuccess(Account account) {
		return PacketBuilder.create(LoginSendOperations.CHECK_PASSWORD_RESULT)
			.writeByte(0)
			.writeByte(0)
			.writeInt(0)

			.writeInt(account.getId())
			.writeByte(0) // Gender
			.writeBool(false) // GM?
			.writeByte(0) // GM?
			.writeString(String.valueOf(account.getIdentity())) // TODO: Account name
			.writeByte(0)
			.writeByte(0)
			.writeLong(0)
			.writeLong(0)
			.writeInt(0)
			.build();
	}

	public static IPacket getCheckPINCodeResult(IValue<Byte> result) {
		return PacketBuilder.create(LoginSendOperations.CHECK_PIN_CODE_RESULT)
			.writeByte(result.getValue())
			.build();
	}

	public static IPacket getCheckUserLimitResult(IValue<Byte> result) {
		return PacketBuilder.create(LoginSendOperations.CHECK_USER_LIMIT_RESULT)
			.writeByte(result.getValue())
			.build();
	}

	public static IPacket getWorldInfoResult(IWorldCluster world) throws RemoteException {
		WorldConfig config = world.getConfig();
		List<IChannelServer> channels = world.getChannelServers();

		return PacketBuilder.create(LoginSendOperations.WORLD_INFO_RESULT)
			.writeByte(0) // world id
			.writeString(config.getName())
			.writeByte(0) // world flag
			.writeString("") // world message

			.writeShort(0x64)
			.writeShort(0x64)
			.writeByte(0x00)

			.write((builder) -> {
				builder.writeByte(channels.size());
				channels.stream()
					.map(channel -> {
						try {
							return channel.getConfig();
						} catch (RemoteException e) {
							return null;
						}
					})
					.filter(Objects::nonNull)
					.forEach(channel -> builder
						.writeString(channel.getName())
						.writeInt(0)
						.writeByte(1)
						.writeShort(channel.getId()));
			})

			.writeShort(0) // Dialogues
			.build();
	}

	public static IPacket getWorldInfoResultEnd() {
		return PacketBuilder.create(LoginSendOperations.WORLD_INFO_RESULT)
			.writeByte(0xFF)
			.build();
	}

}
