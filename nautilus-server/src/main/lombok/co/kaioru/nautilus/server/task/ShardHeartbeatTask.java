package co.kaioru.nautilus.server.task;

import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.packet.game.SocketStructures;
import com.google.common.collect.Lists;
import io.netty.channel.group.ChannelGroup;

import java.time.temporal.ChronoUnit;
import java.util.List;

public class ShardHeartbeatTask implements Runnable {

	private final ChannelGroup channelGroup;

	public ShardHeartbeatTask(ChannelGroup channelGroup) {
		this.channelGroup = channelGroup;
	}

	@Override
	public void run() {
		List<RemoteUser> users = Lists.newArrayList();
		channelGroup.stream()
			.map(c -> c.attr(RemoteUser.USER_KEY).get())
			.forEach(u -> {
				long between = ChronoUnit.SECONDS.between(
					u.getLastAliveReq(),
					u.getLastAliveAck());
				if (between > 60)
					users.add(u);
				else if (between > 15)
					u.sendPacket(SocketStructures.getAliveReq());
			});
		users.forEach(RemoteUser::close);
	}

}
