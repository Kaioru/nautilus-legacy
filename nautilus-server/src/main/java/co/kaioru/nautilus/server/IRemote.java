package co.kaioru.nautilus.server;

import co.kaioru.nautilus.server.config.RemoteConfig;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemote<CO extends RemoteConfig> extends Remote {

	CO getConfig() throws RemoteException;

	void ping(IRemote<? extends CO> from) throws RemoteException;

}
