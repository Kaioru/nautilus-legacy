package co.kaioru.nautilus.interop

import co.kaioru.nautilus.interop.info.ClusterInfo
import java.rmi.RemoteException

interface ICluster<out I : ClusterInfo> : INode<I> {
	val shards: MutableCollection<IShard<*, *>>
		@Throws(RemoteException::class) get

	@Throws(RemoteException::class)
	fun registerShard(shard: IShard<*, *>)

	@Throws(RemoteException::class)
	fun deregisterShard(shard: IShard<*, *>)
}
