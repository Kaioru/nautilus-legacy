package co.kaioru.nautilus.client

import io.netty.bootstrap.Bootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import mu.KLogging
import javax.inject.Inject

open class Client
@Inject
@JvmOverloads
constructor(
	val host: String,
	val port: Int,
	val initializer: ChannelInitializer<SocketChannel>,
	val group: NioEventLoopGroup = NioEventLoopGroup()
) : Runnable {
	companion object : KLogging()

	var channel: Channel? = null

	override fun run() {
		channel = Bootstrap()
			.group(group)
			.channel(NioSocketChannel::class.java)
			.handler(initializer)
			.option(ChannelOption.TCP_NODELAY, true)
			.option(ChannelOption.SO_KEEPALIVE, true)
			.bind(host, port)
			.channel()

		logger.info { "Client started connection with $host:$port" }
	}
}
