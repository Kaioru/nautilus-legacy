package co.kaioru.nautilus.orm.account.item;

import co.kaioru.nautilus.orm.Model;
import co.kaioru.nautilus.orm.account.Character;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Delegate;

import javax.persistence.*;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class Inventory<T extends Item> extends Model {

	@OneToOne(cascade = CascadeType.ALL)
	private Character character;

	@Delegate
	@MapKey(name = "slot")
	@OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
	private Map<Integer, T> items;

	@Column
	private int slotLimit = 24;

	public Inventory(Character character) {
		this.character = character;
	}

}
