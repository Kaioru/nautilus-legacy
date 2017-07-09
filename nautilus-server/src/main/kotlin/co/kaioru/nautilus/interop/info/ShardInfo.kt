package co.kaioru.nautilus.interop.info

import java.util.*

open class ShardInfo(
	override var name: String,
	override var uuid: UUID,
	override var host: String,
	override var port: Int,
	open val clusters: MutableCollection<NodeInfo>
) : NodeInfo(
	name = name,
	uuid = uuid,
	host = host,
	port = port
)
