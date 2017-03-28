package co.kaioru.nautilus.orm.account.item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bundles")
public class BundleItem extends Item {

	@Column
	private int quantity;

}
