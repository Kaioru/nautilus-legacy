package co.kaioru.nautilus.server;

import co.kaioru.nautilus.server.config.ShardConfig;
import co.kaioru.nautilus.server.task.CrossServerPingTask;
import co.kaioru.nautilus.server.task.ShardReconnectTask;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@Slf4j
public class Shard<C extends ICluster, CO extends ShardConfig> extends Daemon<CO> implements IShard<C, CO>, Runnable {

	private final Set<C> clusters;

	public Shard(CO config) {
		super(config);
		this.clusters = Sets.newConcurrentHashSet();
	}

	@Override
	public void registerCluster(C cluster) throws RemoteException {
		getClusters().add(cluster);
		cluster.registerShard(this);
		log.info("Registered Cluster {} to registry", cluster.getConfig().getName());
	}

	@Override
	public void deregisterCluster(C cluster) throws RemoteException {
		getClusters().remove(cluster);
		cluster.deregisterShard(this);
		log.debug("Deregistered Cluster {} from registry", cluster.getConfig().getName());
	}

	@Override
	public void ping(IDaemon remote) throws RemoteException {
		log.debug("Received ping from Cluster {}", remote.getConfig().getName());
	}

	@Override
	public void run() {
		try {
			Remote stub = UnicastRemoteObject.exportObject(this, 0);

			getExecutor().scheduleAtFixedRate(
				new CrossServerPingTask<>(this, this.getClusters()),
				5, 3,
				TimeUnit.SECONDS
			);
			getExecutor().scheduleAtFixedRate(
				new ShardReconnectTask(this, stub, this.getConfig().getClusters()),
				1, 3,
				TimeUnit.SECONDS
			);

			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				getExecutor().shutdown();
				getClusters().forEach(s -> {
					try {
						s.deregisterShard(this);
					} catch (RemoteException e) {
					}
				});
			}));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
