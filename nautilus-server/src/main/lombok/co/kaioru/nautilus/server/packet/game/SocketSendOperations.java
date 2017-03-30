package co.kaioru.nautilus.server.packet.game;

import co.kaioru.nautilus.core.util.IValue;

public enum SocketSendOperations implements IValue<Integer> {

	MIGRATE_COMMAND(0x10),
	ALIVE_REQ(0x11);

	private final int value;

	SocketSendOperations(int value) {
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return value;
	}

}
