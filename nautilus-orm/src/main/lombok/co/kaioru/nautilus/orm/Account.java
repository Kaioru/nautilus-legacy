package co.kaioru.nautilus.orm;

import com.google.common.collect.Lists;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(name = "accounts")
public class Account {

	@Column(unique = true, nullable = false)
	private Long identity;

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Character> characters = Lists.newArrayList();

}
