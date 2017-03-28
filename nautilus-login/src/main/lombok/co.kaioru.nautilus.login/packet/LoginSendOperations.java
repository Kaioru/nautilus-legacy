package co.kaioru.nautilus.login.packet;

import co.kaioru.nautilus.core.util.IValue;

public enum LoginSendOperations implements IValue<Integer> {

	CHECK_PASSWORD_RESULT(0x00),
	CHECK_USER_LIMIT_RESULT(0x03),
	CHECK_PIN_CODE_RESULT(0x06),
	UPDATE_PIN_CODE_RESULT(0x07),
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
