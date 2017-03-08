package co.kaioru.nautilus.server;

import co.kaioru.nautilus.core.config.IConfigurableRemote;
import co.kaioru.nautilus.server.config.ShardConfig;

import java.rmi.RemoteException;
import java.util.Collection;

public interface IShard<C extends ICluster, CO extends ShardConfig> extends IConfigurableRemote<CO>, IDaemon<CO> {

	Collection<? extends C> getClusters() throws RemoteException;

	void registerCluster(C cluster) throws RemoteException;

	void deregisterCluster(C cluster) throws RemoteException;

}
