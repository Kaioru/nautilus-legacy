package co.kaioru.nautilus.server;

import co.kaioru.nautilus.server.config.RemoteConfig;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.Executor;

public interface IDaemon<CO extends RemoteConfig> extends Remote {

	CO getConfig() throws RemoteException;

	ListeningScheduledExecutorService getExecutor() throws RemoteException;

	void ping(IDaemon<? extends CO> from) throws RemoteException;

}
