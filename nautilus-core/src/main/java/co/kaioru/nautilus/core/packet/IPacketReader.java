package co.kaioru.nautilus.core.packet;

public interface IPacketReader extends IPacket {

	byte readByte();

	byte[] readBytes(int val);

	short readShort();

	int readInt();

	long readLong();

	String readString();

}
