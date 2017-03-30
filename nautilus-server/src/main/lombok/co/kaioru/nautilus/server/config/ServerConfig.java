package co.kaioru.nautilus.server.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerConfig extends ShardConfig {

	private short mapleMajorVersion;
	private short mapleMinorVersion;
	private short port;

}
