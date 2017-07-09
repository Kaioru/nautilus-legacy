package co.kaioru.nautilus.interop

import co.kaioru.nautilus.interop.info.ShardInfo
import java.rmi.RemoteException

interface IShard<out I : ShardInfo, C : ICluster<*>> : INode<I> {
	val clusters: MutableCollection<C>
		@Throws(RemoteException::class) get
}
