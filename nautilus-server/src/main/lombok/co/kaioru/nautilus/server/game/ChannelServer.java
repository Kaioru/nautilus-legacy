package co.kaioru.nautilus.server.game;

import co.kaioru.nautilus.server.Server;
import co.kaioru.nautilus.server.game.config.ChannelConfig;

import javax.persistence.EntityManagerFactory;

public class ChannelServer extends Server<IWorldCluster, ChannelConfig> implements IChannelServer {

	public ChannelServer(ChannelConfig config, EntityManagerFactory entityManagerFactory) {
		super(config, entityManagerFactory);
	}

}
