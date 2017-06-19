package co.kaioru.nautilus.interop.info

import java.io.Serializable
import java.util.*

open class NodeInfo(
	open var uuid: UUID,
	open var host: String,
	open var port: Int
) : Serializable
