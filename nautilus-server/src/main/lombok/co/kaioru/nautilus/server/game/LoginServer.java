package co.kaioru.nautilus.server.game;

import co.kaioru.nautilus.server.Server;
import co.kaioru.nautilus.server.game.config.LoginConfig;

import javax.persistence.EntityManagerFactory;

public class LoginServer extends Server<IWorldCluster, LoginConfig> implements ILoginServer {

	public LoginServer(LoginConfig config, EntityManagerFactory entityManagerFactory) {
		super(config, entityManagerFactory);
	}

}
