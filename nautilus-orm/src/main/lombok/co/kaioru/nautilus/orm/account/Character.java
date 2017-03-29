package co.kaioru.nautilus.orm.account;

import co.kaioru.nautilus.orm.Model;
import co.kaioru.nautilus.orm.account.item.BundleInventory;
import co.kaioru.nautilus.orm.account.item.EquipInventory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "characters")
public class Character extends Model {

	@Setter(AccessLevel.PRIVATE)
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
/*
	@Fetch(FetchMode.SELECT)
	@OneToOne(mappedBy = "character", fetch = FetchType.EAGER, orphanRemoval = true)
	private EquipInventory
		equippedInventory = new EquipInventory(this),
		equipInventory = new EquipInventory(this);

	@Fetch(FetchMode.SELECT)
	@OneToOne(mappedBy = "character", fetch = FetchType.EAGER, orphanRemoval = true)
	private BundleInventory
		useInventory = new BundleInventory(this),
		setupInventory = new BundleInventory(this),
		etcInventory = new BundleInventory(this),
		cashInventory = new BundleInventory(this);
*/
	public Character(Account account) {
		this.account = account;
	}

}
