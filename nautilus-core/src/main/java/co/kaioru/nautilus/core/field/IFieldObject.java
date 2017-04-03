package co.kaioru.nautilus.core.field;

import java.awt.*;
import java.util.List;

public interface IFieldObject {

	IFieldInstance getFieldInstance();

	List<IFieldSplit> getFieldSplits();

	int getObjectId();

	Point getPosition();

}
