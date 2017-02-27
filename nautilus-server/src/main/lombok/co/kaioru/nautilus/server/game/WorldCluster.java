package co.kaioru.nautilus.server.game;

import co.kaioru.nautilus.server.Cluster;
import co.kaioru.nautilus.server.IShard;
import co.kaioru.nautilus.server.game.config.WorldConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Getter
@Setter
public class WorldCluster extends Cluster<IShard, WorldConfig> implements IWorldCluster {

	private Supplier<Collection<ILoginServer>> loginServerSupplier =
		() -> getShards()
			.stream()
			.filter(s -> s instanceof ILoginServer)
			.map(s -> (ILoginServer) s)
			.collect(Collectors.toSet());

	public WorldCluster(WorldConfig config) {
		super(config);
	}

}
