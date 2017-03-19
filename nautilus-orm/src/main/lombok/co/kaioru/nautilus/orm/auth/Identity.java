package co.kaioru.nautilus.orm.auth;

import co.kaioru.nautilus.orm.Model;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "identities")
public class Identity extends Model {

	@Column(unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

}
