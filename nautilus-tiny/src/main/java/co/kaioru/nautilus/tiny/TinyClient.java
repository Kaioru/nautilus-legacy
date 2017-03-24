package co.kaioru.nautilus.tiny;

import com.google.common.io.ByteStreams;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

public class TinyClient {

	public static void main(String[] args) throws IOException {
		OptionParser parser = new OptionParser();
		parser.accepts("host").withRequiredArg().ofType(String.class).defaultsTo("localhost");
		parser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(8484);

		OptionSet optionSet = parser.parse(args);

		String host = (String) optionSet.valueOf("host");
		int port = (int) optionSet.valueOf("port");

		Socket clientSocket = new Socket(host, port);
		DataInputStream inbound = new DataInputStream(clientSocket.getInputStream());

		int length = inbound.read();
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < length; i ++)
			builder.append(inbound.read()).append(" ");

		System.out.println("Received handshake: " + builder.toString());
	}

}
