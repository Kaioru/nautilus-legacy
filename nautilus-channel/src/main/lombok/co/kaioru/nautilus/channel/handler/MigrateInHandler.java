package co.kaioru.nautilus.channel.handler;

import co.kaioru.nautilus.channel.ChannelServerApplication;
import co.kaioru.nautilus.channel.packet.UserStructures;
import co.kaioru.nautilus.core.field.IField;
import co.kaioru.nautilus.core.packet.IPacketReader;
import co.kaioru.nautilus.server.game.ChannelServer;
import co.kaioru.nautilus.server.game.manager.IFieldManager;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.migration.IServerMigration;
import co.kaioru.nautilus.server.packet.IServerPacketHandler;

public class MigrateInHandler implements IServerPacketHandler {

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

					IFieldManager fieldManager = channelServer.getFieldManager();
					IField field = fieldManager.getField(user.getCharacter().getFieldId());

					if (field.enter(user, user.getCharacter().getSpawnPoint())) {
						user.sendPacket(UserStructures.getCharacterInformation(user));
						return;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		user.close();
	}

}
