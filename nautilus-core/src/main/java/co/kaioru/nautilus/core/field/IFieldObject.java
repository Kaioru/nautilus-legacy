package co.kaioru.nautilus.core.field;

import java.awt.*;
import java.util.List;

public interface IFieldObject {

	IFieldInstance getFieldInstance();

	IFieldSplit getFieldSplit();

	void setFieldSplit(IFieldSplit fieldSplit);

	List<IFieldSplit> getFieldSplits();

	int getObjectId();

	Point getPosition();

}
