package co.kaioru.nautilus.core.field;

import java.util.List;

public interface IFieldInstance extends IField {

	IField getField();

	List<IFieldSplit> getFieldSplits();

	int getAvailableObjectId();

}
