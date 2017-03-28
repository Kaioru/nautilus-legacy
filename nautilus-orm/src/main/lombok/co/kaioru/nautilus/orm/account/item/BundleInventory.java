package co.kaioru.nautilus.orm.account.item;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bundle_inventories")
public class BundleInventory extends Inventory<BundleItem> {


}
