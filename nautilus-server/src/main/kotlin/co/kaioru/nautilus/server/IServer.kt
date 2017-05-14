package co.kaioru.nautilus.server

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import java.io.Serializable

interface IServer : Runnable, Serializable {
	val host: String
	val port: Int
	var childHandler: ChannelInitializer<SocketChannel>
}
