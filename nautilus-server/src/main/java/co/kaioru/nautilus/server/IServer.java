package co.kaioru.nautilus.server;

import co.kaioru.nautilus.server.config.ServerConfig;
import co.kaioru.nautilus.server.game.IChannelServer;
import co.kaioru.nautilus.server.game.IWorldCluster;
import co.kaioru.nautilus.server.migration.IServerMigration;

import java.rmi.RemoteException;

public interface IServer<C extends ICluster, CO extends ServerConfig> extends IShard<C, CO> {

	void registerMigration(IWorldCluster worldCluster, IChannelServer channelServer, int characterId) throws RemoteException;

	void deregisterMigration(int characterId) throws RemoteException;

	IServerMigration getServerMigration(int characterId) throws RemoteException;

}
