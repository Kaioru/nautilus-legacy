package co.kaioru.nautilus.interop

import co.kaioru.nautilus.interop.info.ShardInfo
import mu.KLogging
import java.rmi.RemoteException
import java.rmi.registry.LocateRegistry
import java.rmi.server.UnicastRemoteObject
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

open class DefaultShard<out I : ShardInfo, C : ICluster<*>>(
	override val clusters: MutableCollection<C>,
	override val info: I,
	open val executor: ScheduledExecutorService = Executors.newScheduledThreadPool(8)
) : IShard<I, C> {
	companion object : KLogging()

	override fun run() {
		val stub = UnicastRemoteObject.exportObject(this, 0)

		logger.info { "Started Shard ${info.name}" }

		executor.scheduleAtFixedRate({
			info.clusters.forEach {
				try {
					val registry = LocateRegistry.getRegistry(it.host, it.port)
					@Suppress("UNCHECKED_CAST")
					val remote = registry.lookup(it.name) as C

					if (!clusters.contains(remote)) {
						registry.rebind(info.name, stub)
						remote.shards.add(this)
						clusters.add(remote)
					}
				} catch (e: Exception) {
					logger.warn { "Failed to register Cluster ${it.name}" }
				}
			}
		}, 1, 3, TimeUnit.SECONDS)

		executor.scheduleAtFixedRate({
			clusters.removeIf {
				try {
					it.ping()
					false
				} catch (e: RemoteException) {
					true
				}
			}
		}, 5, 3, TimeUnit.SECONDS)
	}
}
