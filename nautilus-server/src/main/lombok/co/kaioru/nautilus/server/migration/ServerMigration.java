package co.kaioru.nautilus.server.migration;

import lombok.Getter;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Getter
public class ServerMigration implements IServerMigration {

	public static int SECONDS_TO_EXPIRE = 30;

	private final int characterId;
	private final Instant instant;

	public ServerMigration(int characterId) {
		this.characterId = characterId;
		this.instant = Instant.now();
	}

	public boolean isExpired() {
		return ChronoUnit.SECONDS.between(instant, Instant.now()) > SECONDS_TO_EXPIRE;
	}

}
