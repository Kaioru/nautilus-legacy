package co.kaioru.nautilus.channel;

import co.kaioru.nautilus.server.game.ChannelServer;
import co.kaioru.nautilus.server.game.config.ChannelConfig;
import co.kaioru.nautilus.server.game.config.LoginConfig;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class ChannelServerImpl extends ChannelServer {

	@Getter
	@Setter
	private static ChannelServerImpl instance;

	public ChannelServerImpl(ChannelConfig config, EntityManagerFactory entityManagerFactory) {
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
			ChannelConfig config = new Gson().fromJson(br, ChannelConfig.class);
			ChannelServerImpl server = new ChannelServerImpl(config, entityManagerFactory);

			setInstance(server);
			server.run();
		} catch (IOException e) {
			e.printStackTrace();
			log.error("Failed to find {}..\r\n{}", configPath, new LoginConfig().toJson());
		}
	}

}
