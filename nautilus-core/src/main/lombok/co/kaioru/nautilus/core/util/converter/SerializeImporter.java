package co.kaioru.nautilus.core.util.converter;

import lombok.Cleanup;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SerializeImporter<T> implements IImporter<T> {

	@Override
	@SuppressWarnings("unchecked")
	public T read(String path) throws IOException, ClassNotFoundException {
		@Cleanup FileInputStream fileInputStream = new FileInputStream(path);
		@Cleanup ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

		return (T) objectInputStream.readObject();
	}

}
