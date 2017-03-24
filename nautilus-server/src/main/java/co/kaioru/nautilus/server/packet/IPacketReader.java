package co.kaioru.nautilus.server.packet;

public interface IPacketReader {

	byte readByte();

	short readShort();

	int readInt();

	long readLong();

	String readString();

}
