package co.kaioru.nautilus.data.template.item;

import co.kaioru.nautilus.data.IDataNode;
import co.kaioru.nautilus.data.template.ITemplate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ItemTemplate implements ITemplate {

	private final int templateID;
	private final boolean cash;
	private final int price;

	public ItemTemplate(int templateID, IDataNode info) {
		this.templateID = templateID;
		this.cash = info.getBoolean("cash", false);
		this.price = info.getInt("price", 1);
	}

}
