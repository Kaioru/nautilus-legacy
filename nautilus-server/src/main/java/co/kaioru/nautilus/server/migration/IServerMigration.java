package co.kaioru.nautilus.server.migration;

import java.io.Serializable;

public interface IServerMigration extends Serializable {

	int getCharacterId();

	boolean isExpired();

}
