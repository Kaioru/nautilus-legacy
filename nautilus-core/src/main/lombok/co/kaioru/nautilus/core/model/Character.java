package co.kaioru.nautilus.core.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Character extends Model {

	private Account account;

	public Character(QueryType queryType) {
		super(queryType);
	}

}
