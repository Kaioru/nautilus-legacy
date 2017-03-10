package co.kaioru.nautilus.core.model;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class Account extends Model {

	private Set<Character> characters;

	public Account(QueryType queryType) {
		super(queryType);

		this.characters = Sets.newHashSet();
	}

}
