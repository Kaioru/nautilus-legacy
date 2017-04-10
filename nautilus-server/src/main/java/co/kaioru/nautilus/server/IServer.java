package co.kaioru.nautilus.server;

import co.kaioru.nautilus.core.packet.IReceiver;
import co.kaioru.nautilus.server.config.ServerConfig;
import co.kaioru.nautilus.server.game.IChannelServer;
import co.kaioru.nautilus.server.game.IWorldCluster;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.migration.IServerMigration;
import co.kaioru.nautilus.server.packet.IServerPacketHandler;

import java.rmi.RemoteException;

public interface IServer<C extends ICluster, CO extends ServerConfig> extends IShard<C, CO>, IReceiver<RemoteUser, IServerPacketHandler> {

	void registerMigration(IWorldCluster worldCluster, IChannelServer channelServer, int characterId) throws RemoteException;

	void deregisterMigration(int characterId) throws RemoteException;

	IServerMigration getServerMigration(int characterId) throws RemoteException;

}
