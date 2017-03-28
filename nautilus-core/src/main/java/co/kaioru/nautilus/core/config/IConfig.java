package co.kaioru.nautilus.core.config;

import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public interface IConfig extends Serializable {

	default String toJson() {
		return new GsonBuilder()
			.serializeNulls()
			.setPrettyPrinting()
			.create()
			.toJson(this);
	}

}
