package co.kaioru.nautilus.client.config;

import co.kaioru.nautilus.core.config.IConfig;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientConfig implements IConfig {

	private String host;
	private short port;
	private byte[] aesKey;

}
