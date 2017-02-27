package co.kaioru.nautilus.server;

import co.kaioru.nautilus.server.config.ServerConfig;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Server<C extends ICluster, CO extends ServerConfig> extends Shard<C, CO> implements IServer<C, CO> {

	public Server(CO config) {
		super(config);
	}

	@Override
	public void run() {
		super.run();
	}

}
