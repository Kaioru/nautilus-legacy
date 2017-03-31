package co.kaioru.nautilus.server.migration;

import lombok.Getter;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Getter
public class ServerMigration implements IServerMigration {

	private final int accountId;
	private final int characterId;
	private final Instant instant;

	public ServerMigration(int accountId, int characterId) {
		this.accountId = accountId;
		this.characterId = characterId;
		this.instant = Instant.now();
	}

	public boolean isExpired() {
		return ChronoUnit.SECONDS.between(instant, Instant.now()) > 30;
	}

}
