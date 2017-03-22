package co.kaioru.nautilus.core.util;

public interface IValue<T> {

	T getValue();

	default void setValue(T value) {
	}

}
