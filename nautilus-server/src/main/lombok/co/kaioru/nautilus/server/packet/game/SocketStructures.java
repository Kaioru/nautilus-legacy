package co.kaioru.nautilus.server.packet.game;


import co.kaioru.nautilus.core.packet.IPacket;
import co.kaioru.nautilus.core.packet.PacketBuilder;

import java.net.InetAddress;

public class SocketStructures {

	public static IPacket getAliveReq() {
		return PacketBuilder.create(SocketSendOperations.ALIVE_REQ)
			.build();
	}

	public static IPacket getMigrateCommand(InetAddress address, short port) {
		return PacketBuilder.create(SocketSendOperations.MIGRATE_COMMAND)
			.writeBoolean(true)
			.writeBytes(address.getAddress())
			.writeShort(port)
			.build();
	}

}
