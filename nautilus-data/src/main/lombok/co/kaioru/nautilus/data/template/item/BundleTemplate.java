package co.kaioru.nautilus.data.template.item;

import co.kaioru.nautilus.data.IDataNode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BundleTemplate extends ItemTemplate {

	private final short slotMax;

	public BundleTemplate(int templateID, IDataNode info) {
		super(templateID, info);
		this.slotMax = info.getShort("slotMax", (short) 100);
	}

}
