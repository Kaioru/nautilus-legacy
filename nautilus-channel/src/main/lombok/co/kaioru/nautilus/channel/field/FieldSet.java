package co.kaioru.nautilus.channel.field;

import co.kaioru.nautilus.core.field.IFieldInstance;
import co.kaioru.nautilus.core.field.IFieldSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class FieldSet implements IFieldSet {

	private final List<IFieldInstance> fieldInstances;
	private final Map<String, String> fieldVariables;

	public FieldSet() {
		this.fieldInstances = Lists.newArrayList();
		this.fieldVariables = Maps.newConcurrentMap();
	}
}
