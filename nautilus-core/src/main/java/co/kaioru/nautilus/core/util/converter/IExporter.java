package co.kaioru.nautilus.core.util.converter;

import java.io.IOException;

public interface IExporter<T> {

	void write(T object, String path) throws IOException;

}
