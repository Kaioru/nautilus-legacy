package co.kaioru.nautilus.login.packet;

import co.kaioru.nautilus.core.packet.IPacket;
import co.kaioru.nautilus.core.packet.IPacketWriter;
import co.kaioru.nautilus.core.packet.PacketBuilder;
import co.kaioru.nautilus.core.util.IValue;
import co.kaioru.nautilus.orm.account.Account;
import co.kaioru.nautilus.orm.account.Character;
import co.kaioru.nautilus.server.game.IChannelServer;
import co.kaioru.nautilus.server.game.IWorldCluster;
import co.kaioru.nautilus.server.game.config.WorldConfig;

import java.net.InetAddress;
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

	public static IPacket getGuestLoginResult(IValue<Byte> result) {
		return PacketBuilder.create(LoginSendOperations.GUEST_LOGIN_RESULT)
			.writeByte(result.getValue())
			.writeByte(0)
			.build();
	}

	public static IPacket getCheckPasswordSuccess(Account account) {
		return PacketBuilder.create(LoginSendOperations.CHECK_PASSWORD_RESULT)
			.writeByte(0)
			.writeByte(0)
			.writeInt(0)

			.writeInt(account.getId())
			.writeByte(0) // Gender
			.writeBoolean(false) // GM?
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

			.write(builder -> {
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

	// TODO
	public static void appendCharacterEntry(IPacketWriter writer, Character character, boolean rank) {
		appendCharacterStats(writer, character);
		appendCharacterLooks(writer, character);

		writer.writeBoolean(rank);
		if (rank) {
			writer
				.writeInt(0)
				.writeInt(0)
				.writeInt(0)
				.writeInt(0);
		}
	}

	public static void appendCharacterStats(IPacketWriter writer, Character character) {
		writer
			.writeInt(character.getId())
			.writeBytes(character.getName().substring(0, Math.min(13, character.getName().length())).getBytes()) // This is a lil' different..
			.writeBytes(0x00, 13 - character.getName().length())
			.writeByte(character.getGender())
			.writeByte(character.getSkin())
			.writeInt(character.getFace())
			.writeInt(character.getHair())

			.writeLong(0).writeLong(0).writeLong(0) // Pets

			.writeByte(1)
			.writeShort(0)
			.writeShort(4)
			.writeShort(4)
			.writeShort(4)
			.writeShort(4)
			.writeShort(15)
			.writeShort(15)
			.writeShort(15)
			.writeShort(15)
			.writeShort(0)
			.writeShort(0)
			.writeInt(0)
			.writeShort(0)
			.writeInt(0)
			.writeInt(0)
			.writeByte(0)
			.writeInt(0);
	}

	public static void appendCharacterLooks(IPacketWriter writer, Character character) {
		writer
			.writeByte(character.getGender())
			.writeByte(character.getSkin())
			.writeInt(character.getFace())
			.writeBoolean(false)
			.writeInt(character.getHair())

			.writeByte(0xFF)
			.writeByte(0xFF)
			.writeInt(0)

			.writeInt(0).writeInt(0).writeInt(0);
	}

	public static IPacket getViewAllCharResultStart(int count) {
		return PacketBuilder.create(LoginSendOperations.VIEW_ALL_CHAR_RESULT)
			.writeBoolean(true)
			.writeInt(count)
			.writeInt(count + (3 - count % 3))
			.build();
	}

	public static IPacket getViewAllCharResult(int world, List<Character> characters) {
		return PacketBuilder.create(LoginSendOperations.VIEW_ALL_CHAR_RESULT)
			.writeBoolean(false)
			.writeByte(world)
			.writeByte(characters.size())
			.write(builder -> characters
				.forEach(character -> appendCharacterEntry(builder, character, true)))
			.build();
	}

	public static IPacket getSelectWorldSuccess(List<Character> characters) {
		return PacketBuilder.create(LoginSendOperations.SELECT_WORLD_RESULT)
			.writeBoolean(false)
			.writeByte(characters.size())
			.write(builder -> characters
				.forEach(character -> appendCharacterEntry(builder, character, true)))
			.writeInt(3)
			.build();
	}

	public static IPacket getCheckDuplicatedIdResult(String name, IValue<Byte> result) {
		return PacketBuilder.create(LoginSendOperations.CHECK_DUPLICATED_ID_RESULT)
			.writeString(name)
			.writeByte(result.getValue())
			.build();
	}

	public static IPacket getCreateNewCharacterFailed() {
		return PacketBuilder.create(LoginSendOperations.CREATE_NEW_CHARACTER_RESULT)
			.writeBoolean(true)
			.build();
	}

	public static IPacket getCreateNewCharacterSuccess(Character character) {
		return PacketBuilder.create(LoginSendOperations.CREATE_NEW_CHARACTER_RESULT)
			.writeBoolean(false)
			.write(builder -> appendCharacterEntry(builder, character, false))
			.build();
	}

	public static IPacket getDeleteCharacterResult(int characterId, IValue<Byte> result) {
		return PacketBuilder.create(LoginSendOperations.DELETE_CHARACTER_RESULT)
			.writeInt(characterId)
			.writeByte(result.getValue())
			.build();
	}

	public static IPacket getSelectCharacterSuccess(InetAddress address, short port, int characterId) {
		return PacketBuilder.create(LoginSendOperations.SELECT_CHARACTER_RESULT)
			.writeShort(0)
			.writeBytes(address.getAddress())
			.writeShort(port)
			.writeInt(characterId)
			.writeBytes(0x00, 5)
			.build();
	}

}
