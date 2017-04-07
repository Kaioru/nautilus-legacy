package co.kaioru.nautilus.core.util.converter;

import lombok.Cleanup;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SerializeExporter implements IExporter {

	@Override
	public void write(Object object, String path) throws IOException {
		@Cleanup FileOutputStream fileOutputStream = new FileOutputStream(path);
		@Cleanup ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

		objectOutputStream.writeObject(object);
	}

}
