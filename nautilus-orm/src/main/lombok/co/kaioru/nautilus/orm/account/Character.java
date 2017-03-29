package co.kaioru.nautilus.orm.account;

import co.kaioru.nautilus.orm.Model;
import co.kaioru.nautilus.orm.account.item.BundleInventory;
import co.kaioru.nautilus.orm.account.item.EquipInventory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "characters")
public class Character extends Model {

	@Setter(AccessLevel.NONE)
	@ManyToOne
	private Account account;

	@Column
	private int world;

	@Column
	private String name;

	@Column
	private byte gender, skin;

	@Column
	private int face, hair;

	@OneToOne(mappedBy = "character", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private EquipInventory
		equippedInventory = new EquipInventory(),
		equipInventory = new EquipInventory();

	@OneToOne(mappedBy = "character", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private BundleInventory
		useInventory = new BundleInventory(),
		setupInventory = new BundleInventory(),
		etcInventory = new BundleInventory(),
		cashInventory = new BundleInventory();

}
