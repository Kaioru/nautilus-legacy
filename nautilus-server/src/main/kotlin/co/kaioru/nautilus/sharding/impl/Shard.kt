package co.kaioru.nautilus.sharding.impl

import co.kaioru.nautilus.sharding.ICluster
import co.kaioru.nautilus.sharding.IShard
import io.netty.util.internal.ConcurrentSet
import java.rmi.RemoteException
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

open class Shard<C : ICluster> : IShard<C> {
	override val clusters: MutableSet<C> = ConcurrentSet()
	override val executor: ScheduledExecutorService = Executors.newScheduledThreadPool(8)

	override fun run() {
		executor.scheduleAtFixedRate({
			clusters
				.removeIf {
					try {
						it.ping()
						false
					} catch (e: RemoteException) {
						true
					}
				}
		}, 0, 3, TimeUnit.SECONDS)
	}
}
