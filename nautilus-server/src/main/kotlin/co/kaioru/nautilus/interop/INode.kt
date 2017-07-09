package co.kaioru.nautilus.interop

import co.kaioru.nautilus.interop.info.NodeInfo
import java.rmi.Remote
import java.rmi.RemoteException

interface INode<out I : NodeInfo> : Remote {
	val info: I
		@Throws(RemoteException::class) get

	@Throws(RemoteException::class)
	fun ping() {}
}
