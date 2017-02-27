package co.kaioru.nautilus.server.game;

import co.kaioru.nautilus.server.ICluster;
import co.kaioru.nautilus.server.IShard;
import co.kaioru.nautilus.server.game.config.WorldConfig;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.function.Supplier;

public interface IWorldCluster extends ICluster<IShard, WorldConfig> {

	Supplier<Collection<ILoginServer>> getLoginServerSupplier() throws RemoteException;

}
