package co.kaioru.nautilus.server.config;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ShardConfig extends RemoteConfig {

	private Set<RemoteConfig> clusters;

}
