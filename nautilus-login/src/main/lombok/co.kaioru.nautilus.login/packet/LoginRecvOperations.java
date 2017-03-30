package co.kaioru.nautilus.login.packet;

import co.kaioru.nautilus.core.util.IValue;

public enum LoginRecvOperations implements IValue<Integer> {

	CHECK_PASSWORD(0x01),
	GUEST_ID_LOGIN(0x02),
	WORLD_INFO_REQUEST(0x04),
	SELECT_WORLD(0x05),
	CHECK_USER_LIMIT(0x06),
	CHECK_PIN_CODE(0x09),
	UPDATE_PIN_CODE(0x0A),
	WORLD_REQUEST(0x0B),
	LOGOUT_WORLD(0x0C),
	VIEW_ALL_CHAR(0x0D),
	VAC_FLAG_SET(0x0F),
	SELECT_CHARACTER(0x13),
	CHECK_DUPLICATED_ID(0x15),
	CREATE_NEW_CHARACTER(0x16),
	DELETE_CHARACTER(0x17);

	private final int value;

	LoginRecvOperations(int value) {
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return value;
	}

}
