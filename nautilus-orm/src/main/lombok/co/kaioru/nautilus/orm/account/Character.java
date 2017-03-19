package co.kaioru.nautilus.orm.account;

import co.kaioru.nautilus.orm.Model;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "characters")
public class Character extends Model {

	@Setter(AccessLevel.NONE)
	@ManyToOne
	private Account account;

}
