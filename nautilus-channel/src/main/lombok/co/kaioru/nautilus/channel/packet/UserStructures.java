package co.kaioru.nautilus.channel.packet;

import co.kaioru.nautilus.channel.util.TimeUtil;
import co.kaioru.nautilus.orm.account.Character;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.packet.IPacket;
import co.kaioru.nautilus.server.packet.PacketBuilder;

import java.rmi.RemoteException;
import java.time.Instant;

public class UserStructures {

	public static IPacket getCharacterInformation(RemoteUser user) throws RemoteException {
		Character character = user.getCharacter();

		return PacketBuilder.create(UserSendOperations.SET_FIELD)
			.writeInt(user.getChannelServer().getConfig().getId())
			.writeBool(true)
			.writeBool(true)

			.writeShort(0)

			.writeInt(0)
			.writeInt(0)
			.writeInt(0)

			.writeLong(-1)
			.writeInt(user.getFieldObject().getObjectId())
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
			.writeInt(character.getFieldId())
			.writeByte(character.getSpawnPoint())
			.writeInt(0)
			.writeByte(100) // Friend List capacity

			.writeInt(0)
			.writeByte(24)
			.writeByte(24)
			.writeByte(24)
			.writeByte(24)
			.writeByte(24)
			.writeByte(0x00)
			.writeByte(0x00)
			.writeByte(0x00)
			.writeByte(0x00)
			.writeByte(0x00)
			.writeByte(0x00)
			.writeByte(0x00)

			.writeShort(0)

			.writeShort(0)
			.writeShort(0)

			.writeShort(0)
			.writeShort(0)
			.writeBool(false)
			.writeByte(0x00)
			.writeShort(0)

			.writeInt(0)
			.writeInt(0)
			.writeInt(0)
			.writeInt(0)
			.writeInt(0)
			.writeInt(0)
			.writeInt(0)
			.writeInt(0)
			.writeInt(0)
			.writeInt(0)

			.writeShort(0)
			.writeShort(0)

			.writeLong(TimeUtil.getTimestamp(Instant.now().toEpochMilli()))

			.build();
	}

	public static IPacket getUserChat(int fieldObjectId, String message, boolean gm, boolean show) {
		return PacketBuilder.create(UserSendOperations.USER_CHAT)
			.writeInt(fieldObjectId)
			.writeBool(gm)
			.writeString(message)
			.writeBool(show)
			.build();
	}

}
