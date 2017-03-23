package co.kaioru.nautilus.tiny;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class TinyServer {

	public static void main(String[] args) throws IOException {
		OptionParser parser = new OptionParser();
		parser.accepts("majorversion").withRequiredArg().ofType(Integer.class);
		parser.accepts("minorversion").withRequiredArg().ofType(Integer.class);
		parser.accepts("locale").withRequiredArg().ofType(Integer.class).defaultsTo(8);
		parser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(8484);

		OptionSet optionSet = parser.parse(args);

		int majorVersion = (int) optionSet.valueOf("majorversion");
		String minorVersion = String.valueOf((int) optionSet.valueOf("minorversion"));
		int locale = (int) optionSet.valueOf("locale");
		int port = (int) optionSet.valueOf("port");

		byte[] riv = {70, 114, (byte) (Math.random() * 255), 82};
		byte[] siv = {82, 48, (byte) (Math.random() * 255), 115};
		byte[] handshake = ByteBuffer.allocate(16)
			.order(ByteOrder.LITTLE_ENDIAN)
			.putShort((short) 0x0E)
			.putShort((short) majorVersion)
			.putShort((short) minorVersion.length())
			.put(minorVersion.getBytes())
			.put(riv)
			.put(siv)
			.put((byte) locale)
			.array();

		ServerSocket serverSocket = new ServerSocket(port);

		System.out.println("Started Tiny Server (v" + majorVersion + "." + minorVersion + "/" + locale + ") on port " + port);
		while (!Thread.interrupted()) {
			Socket socket = serverSocket.accept();
			DataOutputStream outbound = new DataOutputStream(socket.getOutputStream());

			System.out.println("Sending handshake packet to " + socket.getInetAddress().getHostAddress());
			outbound.write(handshake);
		}
	}

}
