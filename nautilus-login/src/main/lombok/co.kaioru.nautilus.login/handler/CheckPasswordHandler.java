package co.kaioru.nautilus.login.handler;

import co.kaioru.nautilus.core.util.IValue;
import co.kaioru.nautilus.login.packet.LoginStructures;
import co.kaioru.nautilus.orm.account.Account;
import co.kaioru.nautilus.orm.auth.IAuthenticator;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import co.kaioru.nautilus.server.packet.IPacketHandler;
import co.kaioru.nautilus.server.packet.IPacketReader;

import java.util.Optional;

public class CheckPasswordHandler implements IPacketHandler {

	private final IAuthenticator authenticator;

	public CheckPasswordHandler(IAuthenticator authenticator) {
		this.authenticator = authenticator;
	}

	@Override
	public void handle(RemoteUser user, IPacketReader reader) {
		String username = reader.readString();
		String password = reader.readString();

		Optional<Account> accountOptional = authenticator.authenticate(username, password);

		if (accountOptional.isPresent()) {
			Account account = accountOptional.get();

			user.setAccount(account);
			user.sendPacket(LoginStructures.getCheckPasswordSuccess(account));
			return;
		}

		user.sendPacket(LoginStructures.getCheckPasswordResult(CheckPasswordResult.INVALID_PASSWORD));
	}

	public enum CheckPasswordResult implements IValue<Byte> {

		ENABLE_BUTTON((byte) 1),
		BLOCKED_ACCOUNT((byte) 3),
		INVALID_PASSWORD((byte) 4),
		INVALID_USERNAME((byte) 5),
		SYSTEM_ERROR((byte) 6),
		ALREADY_LOGGED_IN((byte) 7),
		CONNECTION_ERROR((byte) 8),
		SYSTEM_ERROR_2((byte) 9),
		TOO_MANY_REQUESTS((byte) 10);

		private final byte value;

		CheckPasswordResult(byte value) {
			this.value = value;
		}

		@Override
		public Byte getValue() {
			return value;
		}

	}

}
