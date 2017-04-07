package co.kaioru.nautilus.server.game.manager;

import co.kaioru.nautilus.core.field.IField;
import co.kaioru.nautilus.core.field.template.FieldTemplate;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface IFieldManager {

	Map<Integer, FieldTemplate> getFieldTemplates();

	Map<Integer, IField> getFields();

	FieldTemplate getFieldTemplate(int key);

	IField getField(int key) throws ExecutionException;

}
