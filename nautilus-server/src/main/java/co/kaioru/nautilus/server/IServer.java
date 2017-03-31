package co.kaioru.nautilus.server;

import co.kaioru.nautilus.server.config.ServerConfig;
import co.kaioru.nautilus.server.migration.IServerMigration;

import java.rmi.RemoteException;

public interface IServer<C extends ICluster, CO extends ServerConfig> extends IShard<C, CO> {

	void registerServerMigration(IServerMigration serverMigration) throws RemoteException;

	void deregisterServerMigration(IServerMigration serverMigration) throws RemoteException;

	IServerMigration getServerMigration(int characterId) throws RemoteException;

}
