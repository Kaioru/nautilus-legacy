package co.kaioru.nautilus.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

public interface ICluster<S extends IShard> extends Remote {

	Collection<S> getShards() throws RemoteException;

	void registerShard(S shard) throws RemoteException;

	void deregisterShard(S shard) throws RemoteException;

}
