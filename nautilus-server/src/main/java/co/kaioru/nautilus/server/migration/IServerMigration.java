package co.kaioru.nautilus.server.migration;

import co.kaioru.nautilus.server.game.IChannelServer;
import co.kaioru.nautilus.server.game.IWorldCluster;

import java.io.Serializable;

public interface IServerMigration extends Serializable {

	int getCharacterId();

	IWorldCluster getWorldCluster();

	IChannelServer getChannelServer();

	boolean isExpired();

}
