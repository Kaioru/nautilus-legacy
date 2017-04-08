package co.kaioru.nautilus.core.field.template;

import co.kaioru.nautilus.data.IDataNode;
import co.kaioru.nautilus.data.template.ITemplate;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class FieldPortalTemplate implements ITemplate {

	private final int templateID;

	private final String pn;
	private final int pt;
	private final int tm;
	private final String tn;
	private final Point position;

	public FieldPortalTemplate(int templateID,
							   IDataNode portal) {
		this.templateID = templateID;

		this.pn = portal.getString("pn");
		this.pt = portal.getInt("pt");
		this.tm = portal.getInt("tm");
		this.tn = portal.getString("tn");
		this.position = new Point(portal.getInt("x"), portal.getInt("y"));
	}

}
