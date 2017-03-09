package co.kaioru.nautilus.world;

import co.kaioru.nautilus.server.game.WorldCluster;
import co.kaioru.nautilus.server.game.config.WorldConfig;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

@Slf4j
public class WorldClusterImpl extends WorldCluster {

	@Getter
	@Setter
	private static WorldClusterImpl instance;

	public WorldClusterImpl(WorldConfig config) {
		super(config);
	}

	public static void main(String[] args) {
		String configPath = "config.json";

		try {
			BufferedReader br = new BufferedReader(new FileReader(configPath));
			WorldConfig config = new Gson().fromJson(br, WorldConfig.class);
			WorldClusterImpl cluster = new WorldClusterImpl(config);

			setInstance(cluster);
			cluster.run();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.error("Failed to find {}..\r\n{}", configPath, new WorldConfig().toJson());
		}
	}

}
