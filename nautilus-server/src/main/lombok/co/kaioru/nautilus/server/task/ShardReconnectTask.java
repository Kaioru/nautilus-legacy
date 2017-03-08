package co.kaioru.nautilus.server.task;

import co.kaioru.nautilus.server.ICluster;
import co.kaioru.nautilus.server.Shard;
import co.kaioru.nautilus.server.config.RemoteConfig;
import lombok.extern.slf4j.Slf4j;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Set;

@Slf4j
public class ShardReconnectTask implements Runnable {

	private final Shard shard;
	private final Remote stub;
	private final Set<RemoteConfig> clusters;

	public ShardReconnectTask(Shard shard, Remote stub, Set<RemoteConfig> clusters) {
		this.shard = shard;
		this.stub = stub;
		this.clusters = clusters;
	}

	@Override
	public void run() {
		clusters
			.forEach(c -> {
				try {
					Registry registry = LocateRegistry.getRegistry(c.getHost(), Registry.REGISTRY_PORT);
					ICluster cluster = (ICluster) registry.lookup(c.getName());

					if (!shard.getClusters().contains(cluster)) {
						registry.rebind(shard.getConfig().getName(), stub);
						shard.registerCluster(cluster);
					}
				} catch (RemoteException e) {
				} catch (NotBoundException e) {
				}
			});
	}

}
