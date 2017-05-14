package co.kaioru.nautilus.sharding.impl

import co.kaioru.nautilus.sharding.ICluster
import co.kaioru.nautilus.sharding.IShard
import io.netty.util.internal.ConcurrentSet

open class Shard<C : ICluster> : IShard<C> {
	override val clusters: MutableSet<C> = ConcurrentSet()
}
