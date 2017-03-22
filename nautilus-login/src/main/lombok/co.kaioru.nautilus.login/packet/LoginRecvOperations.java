package co.kaioru.nautilus.login.packet;

import co.kaioru.nautilus.core.util.IValue;

public enum LoginRecvOperations implements IValue<Integer> {

	CHECK_PASSWORD(0x01);

	private final int value;

	LoginRecvOperations(int value) {
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return value;
	}

}
