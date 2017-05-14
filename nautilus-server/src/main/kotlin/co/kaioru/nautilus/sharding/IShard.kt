package co.kaioru.nautilus.sharding

import java.rmi.RemoteException

interface IShard<C : ICluster> : IStub {
	val clusters: MutableSet<C>
		@Throws(RemoteException::class)
		get
}
