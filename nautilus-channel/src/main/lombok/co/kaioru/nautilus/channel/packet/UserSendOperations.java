package co.kaioru.nautilus.channel.packet;

import co.kaioru.nautilus.core.util.IValue;

public enum UserSendOperations implements IValue<Integer> {

	SET_FIELD(0x5C),
	USER_CHAT(0x7A);

	private final int value;

	UserSendOperations(int value) {
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return value;
	}

}
