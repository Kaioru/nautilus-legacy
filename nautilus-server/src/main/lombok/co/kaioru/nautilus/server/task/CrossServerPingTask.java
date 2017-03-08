package co.kaioru.nautilus.server.task;

import co.kaioru.nautilus.server.Daemon;
import co.kaioru.nautilus.server.IDaemon;
import lombok.extern.slf4j.Slf4j;

import java.rmi.RemoteException;
import java.util.Set;

@Slf4j
public class CrossServerPingTask<T extends IDaemon> implements Runnable {

	private final Daemon daemon;
	private final Set<T> daemons;

	public CrossServerPingTask(Daemon daemon, Set<T> daemons) {
		this.daemon = daemon;
		this.daemons = daemons;
	}

	@Override
	public void run() {
		daemons.removeIf(d -> {
			try {
				d.ping(daemon);
				return false;
			} catch (RemoteException e) {
				return true;
			}
		});
	}

}
