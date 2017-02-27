package co.kaioru.nautilus.server;

import co.kaioru.nautilus.server.config.ShardConfig;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;

@Getter
@Setter
@Slf4j
public class Shard<C extends ICluster, CO extends ShardConfig> implements IShard<C, CO>, Runnable {

	private final CO config;
	private final Set<C> clusters;

	public Shard(CO config) {
		this.config = config;
		this.clusters = Sets.newConcurrentHashSet();
	}

	@Override
	public void registerCluster(C cluster) throws RemoteException {
		cluster.registerShard(this);
		log.info("Registered Cluster {} to registry", cluster.getConfig().getName());
	}

	@Override
	public void deregisterCluster(C cluster) throws RemoteException {
		cluster.deregisterShard(this);
		log.info("Registered Cluster {} to registry", cluster.getConfig().getName());
	}

	@Override
	public void ping(IRemote remote) throws RemoteException {
		log.debug("Received ping from Cluster {}", remote.getConfig().getName());
	}

	@Override
	public void run() {
		try {
			Remote stub = UnicastRemoteObject.exportObject(this, 0);

			getConfig().getClusters().forEach(c -> {
				try {
					Registry registry = LocateRegistry.getRegistry(c.getHost(), Registry.REGISTRY_PORT);
					C cluster = (C) registry.lookup(c.getName());

					registry.bind(getConfig().getName(), stub);
					registerCluster(cluster);
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (AlreadyBoundException e) {
					e.printStackTrace();
				} catch (NotBoundException e) {
					e.printStackTrace();
				}
			});
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
