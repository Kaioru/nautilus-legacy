package co.kaioru.nautilus.login;

import co.kaioru.nautilus.login.handler.*;
import co.kaioru.nautilus.orm.auth.BasicAuthenticator;
import co.kaioru.nautilus.server.game.LoginServer;
import co.kaioru.nautilus.server.game.config.LoginConfig;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static co.kaioru.nautilus.login.packet.LoginRecvOperations.*;

@Slf4j
public class LoginServerImpl extends LoginServer {

	@Getter
	@Setter
	private static LoginServerImpl instance;

	public LoginServerImpl(LoginConfig config, EntityManagerFactory entityManagerFactory) {
		super(config, entityManagerFactory);
	}

	public static void main(String[] args) {
		String configPath = "config.json";
		String databasePath = "database.properties";

		try {
			Properties properties = new Properties();

			properties.load(new BufferedReader(new FileReader(databasePath)));

			EntityManagerFactory entityManagerFactory = Persistence
				.createEntityManagerFactory("co.kaioru.nautilus.orm.jpa", properties);
			BufferedReader br = new BufferedReader(new FileReader(configPath));
			LoginConfig config = new Gson().fromJson(br, LoginConfig.class);
			LoginServerImpl server = new LoginServerImpl(config, entityManagerFactory);

			EntityManager entityManager = entityManagerFactory.createEntityManager();

			server.registerPacketHandler(CHECK_PASSWORD, new CheckPasswordHandler(new BasicAuthenticator(entityManager)));
			server.registerPacketHandler(GUEST_ID_LOGIN, new GuestIDLoginHandler());
			server.registerPacketHandler(WORLD_INFO_REQUEST, new WorldInfoRequestHandler());
			server.registerPacketHandler(CHECK_USER_LIMIT, new CheckUserLimitHandler());
			server.registerPacketHandler(CHECK_PIN_CODE, new CheckPinCodeHandler());
			server.registerPacketHandler(UPDATE_PIN_CODE, new UpdatePinCodeHandler());
			server.registerPacketHandler(SELECT_WORLD, new SelectWorldHandler());
			server.registerPacketHandler(WORLD_REQUEST, new WorldInfoRequestHandler());
			server.registerPacketHandler(LOGOUT_WORLD, new LogoutWorldHandler());
			server.registerPacketHandler(CHECK_DUPLICATED_ID, new CheckDuplicatedIDHandler());
			server.registerPacketHandler(CREATE_NEW_CHARACTER, new CreateNewCharacterHandler(entityManager));
			server.registerPacketHandler(DELETE_CHARACTER, new DeleteCharacterHandler(entityManager));

			setInstance(server);
			server.run();
		} catch (IOException e) {
			e.printStackTrace();
			log.error("Failed to find {}..\r\n{}", configPath, new LoginConfig().toJson());
		}
	}

}
