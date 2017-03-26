package co.kaioru.nautilus.server.game;

import co.kaioru.nautilus.server.Cluster;
import co.kaioru.nautilus.server.IShard;
import co.kaioru.nautilus.server.game.config.WorldConfig;
import lombok.Getter;
import lombok.Setter;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Getter
@Setter
public class WorldCluster extends Cluster<IShard, WorldConfig> implements IWorldCluster {

	private Supplier<List<ILoginServer>> loginServerSupplier =
		() -> getShards()
			.stream()
			.filter(s -> s instanceof ILoginServer)
			.map(s -> (ILoginServer) s)
			.collect(Collectors.toList());
	private Supplier<List<IChannelServer>> channelServerSupplier =
		() -> getShards()
			.stream()
			.filter(s -> s instanceof IChannelServer)
			.map(s -> (IChannelServer) s)
			.sorted(Comparator.comparingInt(s -> {
				try {
					return s.getConfig().getChannel();
				} catch (RemoteException e) {
					return 0;
				}
			}))
			.collect(Collectors.toList());

	public WorldCluster(WorldConfig config) {
		super(config);
	}

}
