package co.kaioru.nautilus.server.config;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ClusterConfig extends RemoteConfig {

	private Set<RemoteConfig> shards;

}
