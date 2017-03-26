package co.kaioru.nautilus.server.game;

import co.kaioru.nautilus.server.Cluster;
import co.kaioru.nautilus.server.IShard;
import co.kaioru.nautilus.server.game.config.WorldConfig;
import lombok.Getter;
import lombok.Setter;

import java.rmi.RemoteException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class WorldCluster extends Cluster<IShard, WorldConfig> implements IWorldCluster {

	private final Comparator<IShard> sortShardById = (s1, s2) -> {
		try {
			return Integer.compare(s1.getConfig().getId(), s2.getConfig().getId());
		} catch (RemoteException e) {
			return 0;
		}
	};

	public WorldCluster(WorldConfig config) {
		super(config);
	}

	@Override
	public List<ILoginServer> getLoginServers() throws RemoteException {
		return getShards()
			.stream()
			.filter(s -> s instanceof ILoginServer)
			.map(s -> (ILoginServer) s)
			.sorted(sortShardById)
			.collect(Collectors.toList());
	}

	@Override
	public List<IChannelServer> getChannelServers() throws RemoteException {
		return getShards()
			.stream()
			.filter(s -> s instanceof IChannelServer)
			.map(s -> (IChannelServer) s)
			.sorted(sortShardById)
			.collect(Collectors.toList());
	}

}
