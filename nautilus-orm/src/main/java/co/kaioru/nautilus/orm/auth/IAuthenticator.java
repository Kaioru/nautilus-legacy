package co.kaioru.nautilus.orm.auth;

import co.kaioru.nautilus.orm.account.Account;

import java.util.Optional;

@FunctionalInterface
public interface IAuthenticator {

	int authenticate(String username, String password);

}
