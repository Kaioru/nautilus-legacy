package co.kaioru.nautilus.sharding

import java.rmi.Remote
import java.rmi.RemoteException
import java.util.concurrent.ScheduledExecutorService

interface IStub : Remote, Runnable {
	val executor: ScheduledExecutorService

	@Throws(RemoteException::class)
	fun ping() {
	}
}
