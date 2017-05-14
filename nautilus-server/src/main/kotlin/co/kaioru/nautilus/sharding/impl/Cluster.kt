package co.kaioru.nautilus.sharding.impl

import co.kaioru.nautilus.sharding.ICluster
import co.kaioru.nautilus.sharding.IShard
import io.netty.util.internal.ConcurrentSet
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

open class Cluster : ICluster {
	override val shards: MutableSet<IShard<*>> = ConcurrentSet()
	override val executor: ScheduledExecutorService = Executors.newScheduledThreadPool(8)

	override fun run() {
		executor.scheduleAtFixedRate({
			shards.forEach {

			}
		}, 5, 3, TimeUnit.SECONDS)
	}
}
