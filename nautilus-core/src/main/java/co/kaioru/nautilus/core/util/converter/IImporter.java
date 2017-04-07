package co.kaioru.nautilus.core.util.converter;

import java.io.IOException;

public interface IImporter<T> {

	T read(String path) throws IOException, ClassNotFoundException;

}
