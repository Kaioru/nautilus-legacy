package co.kaioru.nautilus.interop

import co.kaioru.nautilus.interop.info.ClusterInfo
import mu.KLogging
import java.rmi.RemoteException
import java.rmi.registry.LocateRegistry
import java.rmi.server.UnicastRemoteObject
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

open class DefaultCluster<out I : ClusterInfo>(
	override val shards: MutableCollection<IShard<*, *>> = HashSet(),
	override val info: I,
	open val executor: ScheduledExecutorService = Executors.newScheduledThreadPool(8)
) : Runnable, ICluster<I> {
	companion object : KLogging()

	override fun registerShard(shard: IShard<*, *>) {
		shards.add(shard)
		logger.info { "Registered Shard ${shard.info.name} to registry" }
	}

	override fun deregisterShard(shard: IShard<*, *>) {
		shards.remove(shard)
		logger.info { "Deregistered Shard ${shard.info.name} to registry" }
	}

	override fun run() {
		val stub = UnicastRemoteObject.exportObject(this, 0)
		val registry = LocateRegistry.createRegistry(info.port)

		registry.bind(info.name, stub)
		logger.info { "Started Cluster ${info.name} on ${info.host}:${info.port}" }

		executor.scheduleAtFixedRate({
			shards.removeIf {
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
