package co.kaioru.nautilus.login.packet;

import co.kaioru.nautilus.core.util.IValue;

public enum LoginSendOperations implements IValue<Integer> {

	CHECK_PASSWORD_RESULT(0x00),
	GUEST_LOGIN_RESULT(0x01),
	CHECK_USER_LIMIT_RESULT(0x03),
	CHECK_PIN_CODE_RESULT(0x06),
	UPDATE_PIN_CODE_RESULT(0x07),
	SELECT_WORLD_RESULT(0x0B),
	WORLD_INFO_RESULT(0x0A),
	CHECK_DUPLICATED_ID_RESULT(0x0D),
	CREATE_NEW_CHARACTER_RESULT(0x0E),
	DELETE_CHARACTER_RESULT(0x0F);

	private final int value;

	LoginSendOperations(int value) {
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return value;
	}

}
