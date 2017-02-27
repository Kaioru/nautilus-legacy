package co.kaioru.nautilus.server;

import co.kaioru.nautilus.server.config.ServerConfig;

public interface IServer<C extends ICluster, CO extends ServerConfig> extends IShard<C, CO> {
}
