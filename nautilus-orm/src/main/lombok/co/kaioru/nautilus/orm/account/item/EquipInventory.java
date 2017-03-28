package co.kaioru.nautilus.orm.account.item;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "equip_inventories")
public class EquipInventory extends Inventory<EquipItem> {


}
