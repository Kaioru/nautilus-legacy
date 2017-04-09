package co.kaioru.nautilus.channel.packet;

import co.kaioru.nautilus.core.util.IValue;

public enum UserRecvOperations implements IValue<Integer> {

	USER_MOVE(0x26);

	private final int value;

	UserRecvOperations(int value) {
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return value;
	}

}
