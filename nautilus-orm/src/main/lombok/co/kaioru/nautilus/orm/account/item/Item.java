package co.kaioru.nautilus.orm.account.item;

import co.kaioru.nautilus.orm.Model;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class Item extends Model {

	@Column
	private int slot;

	@Column
	private int templateId;

}
