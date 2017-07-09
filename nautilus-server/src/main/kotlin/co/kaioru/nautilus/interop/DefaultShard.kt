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
	override val clusters: MutableCollection<C> = HashSet(),
	override val info: I,
	open val executor: ScheduledExecutorService = Executors.newScheduledThreadPool(8)
) : Runnable, IShard<I, C> {
	companion object : KLogging()

	override fun registerCluster(cluster: C) {
		clusters.add(cluster)
		cluster.registerShard(this)
		logger.info { "Registered Cluster ${cluster.info.name} to registry" }
	}

	override fun deregisterCluster(cluster: C) {
		clusters.remove(cluster)
		cluster.deregisterShard(this)
		logger.info { "Deregistered Cluster ${cluster.info.name} to registry" }
	}

	override fun run() {
		val stub = UnicastRemoteObject.exportObject(this, 0)

		logger.info { "Started Shard ${info.name}" }

		executor.scheduleAtFixedRate({
			info.clusters.forEach {
				try {
					val registry = LocateRegistry.getRegistry(it.host, it.port)
					@Suppress("UNCHECKED_CAST")
					val remote = registry.lookup(it.name) as C

					if (!clusters.contains(remote))
						registerCluster(remote)
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
