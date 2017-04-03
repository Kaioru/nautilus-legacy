package co.kaioru.nautilus.core.field;

import java.util.List;

public interface IFieldSplit extends IField {

	List<IFieldObject> getFieldObjects();

	int getRow();

	int getCol();

	int getIndex();

}
