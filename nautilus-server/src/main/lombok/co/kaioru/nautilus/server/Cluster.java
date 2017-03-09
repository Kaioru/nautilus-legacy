package co.kaioru.nautilus.server;

import co.kaioru.nautilus.server.config.ClusterConfig;
import co.kaioru.nautilus.server.task.CrossServerPingTask;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@Slf4j
public class Cluster<S extends IShard, CO extends ClusterConfig> extends Daemon<CO> implements ICluster<S, CO>, Runnable {

	private final Set<S> shards;

	public Cluster(CO config) {
		super(config);
		this.shards = Sets.newConcurrentHashSet();
	}

	@Override
	public void registerShard(S shard) throws RemoteException {
		if (getConfig().getShards()
			.stream()
			.filter(s -> {
				try {
					return s.getName().equals(shard.getConfig().getName())
						&& s.getUuid().equals(shard.getConfig().getUuid());
				} catch (RemoteException e) {
					e.printStackTrace();
					return false;
				}
			})
			.count() > 0) {
			if (!getShards().contains(shard)) {
				getShards().add(shard);
				log.info("Registered Shard {} to registry", shard.getConfig().getName());
			}
		}
	}

	@Override
	public void deregisterShard(S shard) throws RemoteException {
		getShards().remove(shard);
		log.info("Deregistered Shard {} from registry", shard.getConfig().getName());
	}

	@Override
	public void ping(IDaemon remote) throws RemoteException {
		log.debug("Received ping from Shard {}", remote.getConfig().getName());
	}

	@Override
	public void run() {
		try {
			Remote stub = UnicastRemoteObject.exportObject(this, 0);
			Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);

			registry.bind(getConfig().getName(), stub);
			log.info("Started Cluster {}", getConfig().getName());

			getExecutor().scheduleAtFixedRate(
				new CrossServerPingTask<>(this, this.getShards()),
				5, 3,
				TimeUnit.SECONDS
			);

			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				getExecutor().shutdown();
				getShards().forEach(s -> {
					try {
						this.deregisterShard(s);
					} catch (RemoteException e) {
					}
				});
			}));
		} catch (RemoteException e) {
			log.error("Failed to export the remote stub");
		} catch (AlreadyBoundException e) {
			log.error("Failed to create a registry as the port is already bound");
		}
	}

}
