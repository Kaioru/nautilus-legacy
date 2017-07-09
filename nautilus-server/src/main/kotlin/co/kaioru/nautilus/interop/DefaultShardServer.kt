package co.kaioru.nautilus.interop

import co.kaioru.nautilus.interop.info.ShardInfo
import co.kaioru.nautilus.server.Server
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

open class DefaultShardServer<out I : ShardInfo, C : ICluster<*>>(
	override val clusters: MutableCollection<C>,
	override val info: I,
	override val executor: ScheduledExecutorService = Executors.newScheduledThreadPool(8),
	open val server: Server
) : DefaultShard<I, C>(
	clusters = clusters,
	info = info,
	executor = executor
) {
	override fun run() {
		super.run()
		server.run()
		logger.info { "Started Server ${info.name} on ${info.host}:${info.port}" }
	}
}
