package co.kaioru.nautilus.orm.account.item;

import co.kaioru.nautilus.orm.Model;
import co.kaioru.nautilus.orm.account.Character;

import javax.persistence.*;
import java.util.Map;

@MappedSuperclass
public class Inventory<T extends Item> extends Model {

	@OneToOne
	private Character character;

	@MapKey(name = "slot")
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Map<Integer, T> items;

	@Column
	private int slotLimit = 24;

}
