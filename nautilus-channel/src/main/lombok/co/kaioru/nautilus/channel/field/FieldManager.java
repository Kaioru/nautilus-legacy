package co.kaioru.nautilus.channel.field;

import co.kaioru.nautilus.core.field.IField;
import co.kaioru.nautilus.core.field.template.FieldTemplate;
import co.kaioru.nautilus.core.util.converter.SerializeImporter;
import co.kaioru.nautilus.server.game.manager.IFieldManager;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Slf4j
@Getter
public class FieldManager implements IFieldManager {

	private final Map<Integer, FieldTemplate> fieldTemplates;
	private final LoadingCache<Integer, Field> fields;

	public FieldManager(Map<Integer, FieldTemplate> fieldTemplates) {
		this.fieldTemplates = fieldTemplates;
		this.fields = CacheBuilder.newBuilder()
			.build(new CacheLoader<Integer, Field>() {

				@Override
				public Field load(Integer key) throws Exception {
					return new Field(getFieldTemplates().get(key));
				}

			});
	}

	public static FieldManager load(String path) throws IOException, ClassNotFoundException {
		Instant start = Instant.now();
		SerializeImporter<Map<Integer, FieldTemplate>> importer = new SerializeImporter<>();
		Map<Integer, FieldTemplate> templates = importer.read(path);
		log.info("Loaded {} Field Templates in {}ms", templates.size(), ChronoUnit.MILLIS.between(start, Instant.now()));
		return new FieldManager(templates);
	}

	@Override
	public Map<Integer, IField> getFields() {
		return Maps.newHashMap(fields.asMap());
	}

	@Override
	public FieldTemplate getFieldTemplate(int key) {
		return fieldTemplates.get(key);
	}

	@Override
	public IField getField(int key) throws ExecutionException {
		return fields.get(key);
	}

}
