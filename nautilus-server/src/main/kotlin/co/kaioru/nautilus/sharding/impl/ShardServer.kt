package co.kaioru.nautilus.sharding.impl

import co.kaioru.nautilus.server.IServer
import co.kaioru.nautilus.sharding.ICluster

open class ShardServer<C : ICluster>(
	val server: IServer
) : Shard<C>()
