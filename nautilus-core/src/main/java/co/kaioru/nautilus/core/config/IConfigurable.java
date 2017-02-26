package co.kaioru.nautilus.core.config;

import java.io.Serializable;

public interface IConfigurable<C extends IConfig> extends Serializable {

	C getConfig();

}
