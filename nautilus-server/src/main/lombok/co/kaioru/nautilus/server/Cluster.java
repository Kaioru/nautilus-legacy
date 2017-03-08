package co.kaioru.nautilus.server;

import co.kaioru.nautilus.server.config.ClusterConfig;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;

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
			getShards().add(shard);
			shard.getClusters().add(this);
			log.info("Registered Shard {} to registry", shard.getConfig().getName());
		}
	}

	@Override
	public void deregisterShard(S shard) throws RemoteException {
		getShards().remove(shard);
		shard.getClusters().remove(this);
		log.info("Deregistered Shard {} from registry", shard.getConfig().getName());
	}

	@Override
	public void ping(IDaemon remote) throws RemoteException {
		log.debug("Received ping from Shard {}", remote.getConfig().getName());
	}

	@Override
	public void run() {
		try {
			java.rmi.Remote stub = UnicastRemoteObject.exportObject(this, 0);
			Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);

			registry.bind(getConfig().getName(), stub);
			log.info("Started Cluster {}", getConfig().getName());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		}
	}

}
