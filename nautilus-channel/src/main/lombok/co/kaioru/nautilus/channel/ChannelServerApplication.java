package co.kaioru.nautilus.channel;

import co.kaioru.nautilus.channel.field.FieldManager;
import co.kaioru.nautilus.channel.handler.MigrateInHandler;
import co.kaioru.nautilus.channel.handler.user.UserChatHandler;
import co.kaioru.nautilus.channel.handler.user.UserMoveHandler;
import co.kaioru.nautilus.server.game.ChannelServer;
import co.kaioru.nautilus.server.game.config.ChannelConfig;
import co.kaioru.nautilus.server.game.config.LoginConfig;
import co.kaioru.nautilus.server.game.user.RemoteUserFactory;
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

import static co.kaioru.nautilus.channel.packet.UserRecvOperations.USER_CHAT;
import static co.kaioru.nautilus.channel.packet.UserRecvOperations.USER_MOVE;
import static co.kaioru.nautilus.server.packet.game.SocketRecvOperations.MIGRATE_IN;

@Slf4j
public class ChannelServerApplication {

	@Getter
	@Setter
	private static ChannelServer instance;

	public static void main(String[] args) throws Exception {
		String configPath = "config.json";
		String databasePath = "database.properties";

		try {
			Properties properties = new Properties();

			properties.load(new BufferedReader(new FileReader(databasePath)));

			EntityManagerFactory entityManagerFactory = Persistence
				.createEntityManagerFactory("co.kaioru.nautilus.orm.jpa", properties);
			BufferedReader br = new BufferedReader(new FileReader(configPath));
			EntityManager entityManager = entityManagerFactory.createEntityManager();
			ChannelConfig config = new Gson().fromJson(br, ChannelConfig.class);

			FieldManager fieldManager = FieldManager.load("data/fields.bin");

			ChannelServer server = new ChannelServer(
				config,
				new RemoteUserFactory(entityManager),
				entityManagerFactory,
				fieldManager);

			server.registerPacketHandler(MIGRATE_IN, new MigrateInHandler());
			server.registerPacketHandler(USER_MOVE, new UserMoveHandler());
			server.registerPacketHandler(USER_CHAT, new UserChatHandler());

			setInstance(server);
			server.run();
		} catch (IOException e) {
			e.printStackTrace();
			log.error("Failed to find {}..\r\n{}", configPath, new LoginConfig().toJson());
		}
	}

}
