package co.kaioru.nautilus.data.template.field;

import co.kaioru.nautilus.data.IDataNode;
import co.kaioru.nautilus.data.template.ITemplate;
import co.kaioru.nautilus.data.util.Rect;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class FieldTemplate implements ITemplate {

	public static int SCREEN_WIDTH = 600, SCREEN_HEIGHT = 450;

	private final int templateID;
	private final int returnMap;

	private final Point leftTop, fieldSize;
	private final int mobCapacityMin, mobCapacityMax;

	public FieldTemplate(final int templateID,
						 final IDataNode info,
						 final IDataNode foothold,
						 final IDataNode portal,
						 final IDataNode life) {
		this.templateID = templateID;
		this.returnMap = info.getInt("returnMap", 999999999);

		Rect bounds = new Rect(Integer.MAX_VALUE, Integer.MAX_VALUE, -Integer.MAX_VALUE, -Integer.MAX_VALUE);
		foothold.getChildren()
			.forEach(r -> r.getChildren()
				.forEach(c -> c.getChildren()
					.forEach(f -> {
						int x1 = f.getInt("x1");
						int y1 = f.getInt("y1");
						int x2 = f.getInt("x2");
						int y2 = f.getInt("y2");

						int left, right, top, bottom;
						right = x1;
						if (x1 >= x2) {
							left = x2;
						} else {
							left = x1;
							right = x2;
						}
						bottom = y2;
						if (y1 >= y2) {
							bottom = y1;
							top = y2;
						} else {
							top = y1;
						}

						if (bounds.getLeft() > left + 30) bounds.setLeft(left + 30);
						if (bounds.getRight() < right - 30) bounds.setRight(right - 30);
						if (bounds.getTop() > top - 300) bounds.setTop(top - 300);
						if (bounds.getBottom() < bottom + 2) bounds.setBottom(bottom + 2);
					})));

		boolean vrLimit = info.getBoolean("VRLimit");

		if (vrLimit) {
			int vrLeft = info.getInt("VRLeft");
			int vrTop = info.getInt("VRTop");
			int vrRight = info.getInt("VRRight");
			int vrBottom = info.getInt("VRBottom");

			if (bounds.getLeft() < vrLeft + 20) bounds.setLeft(vrLeft + 20);
			if (bounds.getRight() > vrRight - 20) bounds.setRight(vrRight - 20);
			if (bounds.getTop() < vrTop + 65) bounds.setTop(vrTop + 65);
			if (bounds.getBottom() > vrBottom) bounds.setBottom(vrBottom);
		}

		bounds.inflate(10, 10);

		this.leftTop = new Point(bounds.getLeft(), bounds.getTop());
		this.fieldSize = new Point(bounds.getRight() - bounds.getLeft(), bounds.getBottom() - bounds.getTop());

		double mobRate = info.getDouble("mobRate");
		int width = SCREEN_WIDTH + 200;
		int height = SCREEN_HEIGHT + 150;

		if (fieldSize.x > width) width = fieldSize.x;
		if (fieldSize.y > height) height = fieldSize.y;

		int mobCapacity = (int) (width * height * mobRate * 1 / 1.28 / 100000);

		if (mobCapacity < 1) mobCapacity = 1;
		if (mobCapacity > 40) mobCapacity = 40;

		this.mobCapacityMin = mobCapacity;
		this.mobCapacityMax = mobCapacity * 2;
	}

}
