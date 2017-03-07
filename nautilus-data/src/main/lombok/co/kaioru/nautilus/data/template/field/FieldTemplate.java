package co.kaioru.nautilus.data.template.field;

import co.kaioru.nautilus.data.IDataNode;
import co.kaioru.nautilus.data.template.ITemplate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldTemplate implements ITemplate {

	private final int templateID;
	private final int returnMap;

	public FieldTemplate(final int templateID, IDataNode info) {
		this.templateID = templateID;
		this.returnMap = info.getInt("returnMap", 999999999);
	}

}
