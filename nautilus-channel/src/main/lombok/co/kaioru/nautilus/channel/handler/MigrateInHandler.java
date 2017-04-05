package co.kaioru.nautilus.channel.handler;

import co.kaioru.nautilus.channel.ChannelServerApplication;
import co.kaioru.nautilus.channel.packet.GameStructures;
import co.kaioru.nautilus.server.game.ChannelServer;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.migration.IServerMigration;
import co.kaioru.nautilus.server.packet.IPacketHandler;
import co.kaioru.nautilus.server.packet.IPacketReader;

public class MigrateInHandler implements IPacketHandler {

	@Override
	public void handle(RemoteUser user, IPacketReader reader) {
		int characterId = reader.readInt();
		ChannelServer channelServer = ChannelServerApplication.getInstance();
		IServerMigration migration = channelServer.getServerMigration(characterId);

		if (migration != null) {
			try {
				if (channelServer.getClusters().contains(migration.getWorldCluster())) {
					user.setWorldCluster(migration.getWorldCluster());
					user.setChannelServer(channelServer);
					user.migrateIn(channelServer, characterId);

					user.sendPacket(GameStructures.getCharacterInformation(user));
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		user.close();
	}

}
