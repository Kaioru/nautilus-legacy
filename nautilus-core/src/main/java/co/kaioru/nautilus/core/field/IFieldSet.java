package co.kaioru.nautilus.core.field;

import java.util.List;
import java.util.Map;

public interface IFieldSet {

	List<IFieldInstance> getFieldInstances();

	default IFieldInstance getFieldInstance(int index) {
		return getFieldInstances().get(index);
	}

	Map<String, String> getFieldVariables();

	default void setFieldVariable(String key, String value) {
		getFieldVariables().put(key, value);
	}

	default String getFieldVariable(String key) {
		return getFieldVariables().getOrDefault(key, "");
	}

}
