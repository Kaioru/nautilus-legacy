package co.kaioru.nautilus.interop

import co.kaioru.nautilus.interop.info.ClusterInfo
import java.rmi.RemoteException

interface ICluster<out I : ClusterInfo> : INode<I> {
	val shards: MutableCollection<IShard<*, *>>
		@Throws(RemoteException::class) get
}
