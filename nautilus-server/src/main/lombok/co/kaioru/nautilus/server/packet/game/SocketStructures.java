package co.kaioru.nautilus.server.packet.game;

import co.kaioru.nautilus.server.packet.IPacket;
import co.kaioru.nautilus.server.packet.PacketBuilder;

import java.net.InetAddress;

public class SocketStructures {

	public static IPacket getAliveReq() {
		return PacketBuilder.create(SocketSendOperations.ALIVE_REQ)
			.build();
	}

	public static IPacket getMigrateCommand(InetAddress address, short port) {
		return PacketBuilder.create(SocketSendOperations.MIGRATE_COMMAND)
			.writeBool(true)
			.writeBytes(address.getAddress())
			.writeShort(port)
			.build();
	}

}
