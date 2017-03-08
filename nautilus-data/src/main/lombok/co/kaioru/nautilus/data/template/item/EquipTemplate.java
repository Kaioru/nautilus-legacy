package co.kaioru.nautilus.data.template.item;

import co.kaioru.nautilus.data.IDataNode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EquipTemplate extends ItemTemplate {

	public EquipTemplate(int templateID, IDataNode info) {
		super(templateID, info);
	}

}
