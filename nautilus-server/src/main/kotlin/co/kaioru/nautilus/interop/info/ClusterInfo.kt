package co.kaioru.nautilus.interop.info

import java.util.*

open class ClusterInfo(
	override var name: String,
	override var uuid: UUID,
	override var host: String,
	override var port: Int
) : NodeInfo(
	name = name,
	uuid = uuid,
	host = host,
	port = port
)
