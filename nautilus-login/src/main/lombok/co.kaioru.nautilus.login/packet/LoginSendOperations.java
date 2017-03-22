package co.kaioru.nautilus.login.packet;

import co.kaioru.nautilus.core.util.IValue;

public enum LoginSendOperations implements IValue<Integer> {

	CHECK_PASSWORD_RESULT(0x00),
	WORLD_INFO_RESULT(0x0A);

	private final int value;

	LoginSendOperations(int value) {
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return value;
	}

}
