package co.kaioru.nautilus.server.game;

import co.kaioru.nautilus.server.Server;
import co.kaioru.nautilus.server.game.config.ChannelConfig;
import co.kaioru.nautilus.server.game.manager.IFieldManager;
import co.kaioru.nautilus.server.game.user.IRemoteUserFactory;
import lombok.Getter;

import javax.persistence.EntityManagerFactory;

@Getter
public class ChannelServer extends Server<IWorldCluster, ChannelConfig> implements IChannelServer {

	private final IFieldManager fieldManager;

	public ChannelServer(ChannelConfig config,
						 IRemoteUserFactory remoteUserFactory,
						 EntityManagerFactory entityManagerFactory,
						 IFieldManager fieldManager) {
		super(config, remoteUserFactory, entityManagerFactory);
		this.fieldManager = fieldManager;
	}

}
