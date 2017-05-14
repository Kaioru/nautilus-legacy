package co.kaioru.nautilus.server.impl

import co.kaioru.nautilus.server.IServer
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import javax.inject.Inject

class Server
@Inject constructor(
	override val host: String, // TODO: Configs
	override val port: Int,
	override var childHandler: ChannelInitializer<SocketChannel>
) : IServer {
	override fun run() {
		val bossGroup = NioEventLoopGroup(4)
		val workerGroup = NioEventLoopGroup(4)
		val channel = ServerBootstrap()
			.group(
				bossGroup,
				workerGroup)
			.channel(NioServerSocketChannel::class.java)
			.childHandler(childHandler)
			.childOption(ChannelOption.TCP_NODELAY, true)
			.childOption(ChannelOption.SO_KEEPALIVE, true)
			.bind(host, port)
			.channel()

		channel.closeFuture().addListener {
			bossGroup.shutdownGracefully()
			workerGroup.shutdownGracefully()
		}
	}
}
