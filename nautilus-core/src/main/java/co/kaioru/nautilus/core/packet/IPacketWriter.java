package co.kaioru.nautilus.core.packet;

public interface IPacketWriter extends IPacket {

	IPacketWriter writeByte(int val);

	IPacketWriter writeBytes(byte[] val);

	IPacketWriter writeBytes(int val, int i);

	IPacketWriter writeBoolean(boolean val);

	IPacketWriter writeShort(int val);

	IPacketWriter writeInt(int val);

	IPacketWriter writeLong(long val);

	IPacketWriter writeString(String val);

}
