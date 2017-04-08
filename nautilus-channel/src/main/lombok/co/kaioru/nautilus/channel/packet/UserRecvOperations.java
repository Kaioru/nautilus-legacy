package co.kaioru.nautilus.channel.packet;

import co.kaioru.nautilus.core.util.IValue;

public enum UserRecvOperations implements IValue<Integer> {

	;

	private final int value;

	UserRecvOperations(int value) {
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return value;
	}

}
