package co.kaioru.nautilus.server.migration;

import co.kaioru.nautilus.server.game.IChannelServer;
import co.kaioru.nautilus.server.game.IWorldCluster;
import lombok.Getter;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Getter
public class ServerMigration implements IServerMigration {

	public static int SECONDS_TO_EXPIRE = 30;

	private final IWorldCluster worldCluster;
	private final IChannelServer channelServer;
	private final int characterId;
	private final Instant instant;

	public ServerMigration(IWorldCluster worldCluster, IChannelServer channelServer, int characterId) {
		this.worldCluster = worldCluster;
		this.channelServer = channelServer;
		this.characterId = characterId;
		this.instant = Instant.now();
	}

	public boolean isExpired() {
		return ChronoUnit.SECONDS.between(instant, Instant.now()) > SECONDS_TO_EXPIRE;
	}

}
