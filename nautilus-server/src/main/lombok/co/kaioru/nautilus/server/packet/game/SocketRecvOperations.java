package co.kaioru.nautilus.server.packet.game;

import co.kaioru.nautilus.core.util.IValue;

public enum SocketRecvOperations implements IValue<Integer> {

	MIGRATE_IN(0x14),
	ALIVE_ACK(0x18),
	EXCEPTION_LOG(0x19);

	private final int value;

	SocketRecvOperations(int value) {
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return value;
	}

}
