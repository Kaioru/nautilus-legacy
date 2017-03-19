package co.kaioru.nautilus.orm.account;

import co.kaioru.nautilus.orm.Model;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account extends Model {

	@Column(unique = true, nullable = false)
	private int identity;

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Character> characters = Lists.newArrayList();

}
