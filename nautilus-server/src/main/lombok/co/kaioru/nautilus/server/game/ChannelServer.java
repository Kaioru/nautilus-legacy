package co.kaioru.nautilus.server.game;

import co.kaioru.nautilus.server.Server;
import co.kaioru.nautilus.server.game.config.ChannelConfig;
import co.kaioru.nautilus.server.game.user.IRemoteUserFactory;

import javax.persistence.EntityManagerFactory;

public class ChannelServer extends Server<IWorldCluster, ChannelConfig> implements IChannelServer {

	public ChannelServer(ChannelConfig config, IRemoteUserFactory remoteUserFactory, EntityManagerFactory entityManagerFactory) {
		super(config, remoteUserFactory, entityManagerFactory);
	}

}
