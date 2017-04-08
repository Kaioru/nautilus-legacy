package co.kaioru.nautilus.core.field;

import java.util.Set;

public interface IFieldSplit {

	Set<IFieldObject> getFieldObjects();

	int getRow();

	int getCol();

	int getIndex();

	boolean enter(IFieldObject object);

	boolean leave(IFieldObject object);

}
