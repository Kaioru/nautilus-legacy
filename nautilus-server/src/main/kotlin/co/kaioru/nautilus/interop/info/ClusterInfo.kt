package co.kaioru.nautilus.interop.info

import java.util.*

open class ClusterInfo(
	override var uuid: UUID,
	override var host: String,
	override var port: Int,
	open val shards: MutableCollection<NodeInfo>
) : NodeInfo(
	uuid = uuid,
	host = host,
	port = port
)
