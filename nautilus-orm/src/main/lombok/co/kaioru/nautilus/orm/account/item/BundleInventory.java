package co.kaioru.nautilus.orm.account.item;

import co.kaioru.nautilus.orm.account.Character;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "bundle_inventories")
public class BundleInventory extends Inventory<BundleItem> {

	public BundleInventory(Character character) {
		super(character);
	}

}
