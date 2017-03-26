package co.kaioru.nautilus.server.packet;

import io.netty.channel.Channel;

import java.util.function.Consumer;
import java.util.function.Function;

public interface IPacketWriter {

	IPacketWriter writeByte(int data);

	IPacketWriter writeBytes(byte[] data);

	IPacketWriter writeBool(boolean data);

	IPacketWriter writeShort(int data);

	IPacketWriter writeInt(int data);

	IPacketWriter writeLong(long data);

	IPacketWriter writeString(String string);

	IPacketWriter write(Consumer<IPacketWriter> consumer);

	default void buildAndFlush(Channel channel) {
		channel.writeAndFlush(build());
	}

	IPacket build();

}
