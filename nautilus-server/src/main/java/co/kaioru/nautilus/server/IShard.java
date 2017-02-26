package co.kaioru.nautilus.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

public interface IShard<C extends ICluster> extends Remote {

	Collection<C> getClusters() throws RemoteException;

	void registerCluster(C cluster) throws RemoteException;

	void deregisterCluster(C cluster) throws RemoteException;

}
