package co.kaioru.nautilus.interop

import co.kaioru.nautilus.interop.info.NodeInfo
import java.rmi.Remote
import java.rmi.RemoteException

interface INode<out I : NodeInfo> : Runnable, Remote {
	val info: I
		@Throws(RemoteException::class) get

	fun ping() {}
}
