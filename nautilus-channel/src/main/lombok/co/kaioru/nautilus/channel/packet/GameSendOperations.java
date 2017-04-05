package co.kaioru.nautilus.channel.packet;

import co.kaioru.nautilus.core.util.IValue;

public enum GameSendOperations implements IValue<Integer> {

	SET_FIELD(0x5C);

	private final int value;

	GameSendOperations(int value) {
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return value;
	}

}
