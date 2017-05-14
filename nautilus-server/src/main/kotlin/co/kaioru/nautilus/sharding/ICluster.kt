package co.kaioru.nautilus.sharding

import java.rmi.RemoteException

interface ICluster : IStub {
	val shards: MutableSet<IShard<*>>
		@Throws(RemoteException::class)
		get
}
