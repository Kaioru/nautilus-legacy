package co.kaioru.nautilus.server

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import mu.KLogging
import javax.inject.Inject

open class Server
@Inject
@JvmOverloads
constructor(
	val host: String,
	val port: Int,
	val initializer: ChannelInitializer<SocketChannel>,
	val parentGroup: NioEventLoopGroup = NioEventLoopGroup(4),
	val childGroup: NioEventLoopGroup = NioEventLoopGroup(2)
) : Runnable {
	companion object : KLogging()

	var channel: Channel? = null

	override fun run() {
		try {
			channel = ServerBootstrap()
				.group(parentGroup, childGroup)
				.channel(NioServerSocketChannel::class.java)
				.childHandler(initializer)
				.childOption(ChannelOption.TCP_NODELAY, true)
				.childOption(ChannelOption.SO_KEEPALIVE, true)
				.bind(host, port)
				.channel()

			logger.info { "Server started on $host:$port" }
		} catch (e: Exception) {
			logger.error { "Server failed to start on $host:$port" }
		}
	}
}
