package co.kaioru.nautilus.server.packet;

import io.netty.channel.Channel;

public interface IPacketWriter {

	IPacketWriter writeByte(int data);

	IPacketWriter writeBytes(byte[] data);

	IPacketWriter writeBool(boolean data);

	IPacketWriter writeShort(int data);

	IPacketWriter writeInt(int data);

	IPacketWriter writeLong(long data);

	IPacketWriter writeString(String string);

	default void buildAndFlush(Channel channel) {
		channel.writeAndFlush(build());
	}

	IPacket build();

}
