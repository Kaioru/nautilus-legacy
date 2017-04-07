package co.kaioru.nautilus.core.util.converter;

import co.kaioru.nautilus.data.IDataNode;
import co.kaioru.nautilus.data.nx.NXDataNode;
import us.aaronweiss.pkgnx.LazyNXFile;

import java.io.IOException;

public abstract class NXImporter<T> implements IImporter<T> {

	@Override
	public T read(String path) throws IOException, ClassNotFoundException {
		return read(new NXDataNode(new LazyNXFile(path)));
	}

	public abstract T read(IDataNode dataNode) throws IOException, ClassNotFoundException;

}
