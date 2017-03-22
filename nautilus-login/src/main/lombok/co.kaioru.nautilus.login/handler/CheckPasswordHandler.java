package co.kaioru.nautilus.login.handler;

import co.kaioru.nautilus.login.packet.LoginStructures;
import co.kaioru.nautilus.orm.account.Account;
import co.kaioru.nautilus.orm.auth.IAuthenticator;
import co.kaioru.nautilus.server.game.packet.IPacketHandler;
import co.kaioru.nautilus.server.game.packet.PacketReader;
import co.kaioru.nautilus.server.game.user.RemoteUser;
import com.google.common.base.Joiner;

import java.util.Arrays;
import java.util.Optional;

public class CheckPasswordHandler implements IPacketHandler {

	private final IAuthenticator authenticator;

	public CheckPasswordHandler(IAuthenticator authenticator) {
		this.authenticator = authenticator;
	}

	@Override
	public void handle(RemoteUser remoteUser, PacketReader reader) {
		String username = reader.readMapleString();
		String password = reader.readMapleString();

		Optional<Account> accountOptional = authenticator.authenticate(username, password);

		if (accountOptional.isPresent()) {
			Account account = accountOptional.get();

			remoteUser.setAccount(account);
		} else {

		}

		remoteUser.getChannel().writeAndFlush(LoginStructures.getCheckPasswordResult((byte) 13));
	}

}
