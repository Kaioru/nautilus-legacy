package co.kaioru.nautilus.core.util.converter.field;

import co.kaioru.nautilus.core.field.template.FieldTemplate;
import co.kaioru.nautilus.core.util.converter.NXImporter;
import co.kaioru.nautilus.data.IDataNode;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class DataToFieldImporter extends NXImporter<Map<Integer, FieldTemplate>> {

	@Override
	public Map<Integer, FieldTemplate> read(IDataNode dataNode) throws IOException, ClassNotFoundException {
		return dataNode.getChild("Map").getChildren()
			.stream()
			.filter(c -> c.getName().startsWith("Map"))
			.flatMap(c -> c.getChildren().stream())
			.map(c -> {
				int id = Integer.parseInt(c.getName().substring(0, 9));
				IDataNode info = c.getChild("info");
				IDataNode foothold = c.getChild("foothold");
				IDataNode portal = c.getChild("portal");

				return new FieldTemplate(id, info, foothold, portal);
			})
			.collect(Collectors.toMap(FieldTemplate::getTemplateID, t -> t));
	}

}
