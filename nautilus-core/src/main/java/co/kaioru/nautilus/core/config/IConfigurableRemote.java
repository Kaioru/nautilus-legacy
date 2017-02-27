package co.kaioru.nautilus.core.config;

import java.io.Serializable;
import java.rmi.RemoteException;

public interface IConfigurableRemote<C extends IConfig> extends Serializable {

	C getConfig() throws RemoteException;

}
