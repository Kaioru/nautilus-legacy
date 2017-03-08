package co.kaioru.nautilus.server;

import co.kaioru.nautilus.server.config.RemoteConfig;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.Executors;

@Getter
@Setter
public abstract class Daemon<CO extends RemoteConfig> implements IDaemon<CO> {

	private final ListeningScheduledExecutorService executor =
		MoreExecutors.listeningDecorator(Executors.newScheduledThreadPool(32));
	private final CO config;

	protected Daemon(CO config) {
		this.config = config;
	}

}
