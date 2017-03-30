package co.kaioru.nautilus.server.game;

import co.kaioru.nautilus.server.Server;
import co.kaioru.nautilus.server.game.config.LoginConfig;
import co.kaioru.nautilus.server.game.user.IRemoteUserFactory;

import javax.persistence.EntityManagerFactory;

public class LoginServer extends Server<IWorldCluster, LoginConfig> implements ILoginServer {

	public LoginServer(LoginConfig config, IRemoteUserFactory remoteUserFactory, EntityManagerFactory entityManagerFactory) {
		super(config, remoteUserFactory, entityManagerFactory);
	}

}
