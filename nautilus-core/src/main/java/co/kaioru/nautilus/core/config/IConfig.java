package co.kaioru.nautilus.core.config;

import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public abstract class IConfig implements Serializable {

	public String toJson() {
		return new GsonBuilder()
			.serializeNulls()
			.setPrettyPrinting()
			.create()
			.toJson(this);
	}

}
