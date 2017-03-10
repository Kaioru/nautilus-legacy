package co.kaioru.nautilus.core.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Model implements IModel {

	private int id;
	private QueryType queryType;

	public Model(QueryType queryType) {
		this.queryType = queryType;
	}

}
