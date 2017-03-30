package co.kaioru.nautilus.orm.account;

import co.kaioru.nautilus.orm.Model;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Cacheable
@Table(name = "accounts")
public class Account extends Model {

	@Column(unique = true, nullable = false)
	private int identity;

	@Enumerated(EnumType.STRING)
	@Column
	private AccountState state = AccountState.LOGGED_OFF;

	@Fetch(FetchMode.SELECT)
	@OneToMany(mappedBy = "account", fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Character> characters = Lists.newArrayList();

}
