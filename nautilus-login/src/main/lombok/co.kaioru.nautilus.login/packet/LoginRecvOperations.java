package co.kaioru.nautilus.login.packet;

import co.kaioru.nautilus.core.util.IValue;

public enum LoginRecvOperations implements IValue<Integer> {

	CHECK_PASSWORD(0x01),
	WORLD_INFO_REQUEST(0x04),
	CHECK_PIN_CODE(0x09),
	UPDATE_PIN_CODE(0x0A),
	WORLD_REQUEST(0x0B);

	private final int value;

	LoginRecvOperations(int value) {
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return value;
	}

}
