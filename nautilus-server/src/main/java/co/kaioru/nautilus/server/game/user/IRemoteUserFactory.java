package co.kaioru.nautilus.server.game.user;

import io.netty.channel.Channel;

public interface IRemoteUserFactory {

	RemoteUser create(Channel channel);

}
