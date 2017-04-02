package co.kaioru.nautilus.channel.packet;

import co.kaioru.nautilus.core.util.IValue;
import co.kaioru.nautilus.orm.account.Account;
import co.kaioru.nautilus.orm.account.Character;
import co.kaioru.nautilus.server.game.IChannelServer;
import co.kaioru.nautilus.server.game.IWorldCluster;
import co.kaioru.nautilus.server.game.config.WorldConfig;
import co.kaioru.nautilus.server.packet.IPacket;
import co.kaioru.nautilus.server.packet.IPacketWriter;
import co.kaioru.nautilus.server.packet.PacketBuilder;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Objects;

public class GameStructures {

	public IPacket getSetField() {
		return PacketBuilder.create()
			.build();
	}

}
