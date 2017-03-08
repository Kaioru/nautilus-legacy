package co.kaioru.nautilus.server;

import co.kaioru.nautilus.core.config.IConfigurableRemote;
import co.kaioru.nautilus.server.config.ClusterConfig;

import java.rmi.RemoteException;
import java.util.Collection;

public interface ICluster<S extends IShard, CO extends ClusterConfig> extends IConfigurableRemote<CO>, IDaemon<CO> {

	Collection<? extends S> getShards() throws RemoteException;

	void registerShard(S shard) throws RemoteException;

	void deregisterShard(S shard) throws RemoteException;

}
