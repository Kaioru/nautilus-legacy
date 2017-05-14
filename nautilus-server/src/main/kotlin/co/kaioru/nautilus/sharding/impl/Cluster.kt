package co.kaioru.nautilus.sharding.impl

import co.kaioru.nautilus.sharding.ICluster
import co.kaioru.nautilus.sharding.IShard
import io.netty.util.internal.ConcurrentSet

open class Cluster : ICluster {
	override val shards: MutableSet<IShard<*>> = ConcurrentSet()
}
