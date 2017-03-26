package co.kaioru.nautilus.server.config;

import co.kaioru.nautilus.core.config.IConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RemoteConfig extends IConfig {

	private int id;

	private String host;
	private String name;
	private UUID uuid;

}
