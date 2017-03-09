package co.kaioru.nautilus.login;

import co.kaioru.nautilus.server.game.LoginServer;
import co.kaioru.nautilus.server.game.config.LoginConfig;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

@Slf4j
public class LoginServerImpl extends LoginServer {

	@Getter
	@Setter
	private static LoginServerImpl instance;

	public LoginServerImpl(LoginConfig config) {
		super(config);
	}

	public static void main(String[] args) {
		String configPath = "config.json";

		try {
			BufferedReader br = new BufferedReader(new FileReader(configPath));
			LoginConfig config = new Gson().fromJson(br, LoginConfig.class);
			LoginServerImpl server = new LoginServerImpl(config);

			setInstance(server);
			server.run();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.error("Failed to find {}..\r\n{}", configPath, new LoginConfig().toJson());
		}
	}

}
