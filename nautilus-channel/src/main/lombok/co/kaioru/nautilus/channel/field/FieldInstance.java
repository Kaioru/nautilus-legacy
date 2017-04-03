package co.kaioru.nautilus.channel.field;

import co.kaioru.nautilus.core.field.IField;
import co.kaioru.nautilus.core.field.IFieldInstance;
import co.kaioru.nautilus.core.field.IFieldSplit;
import co.kaioru.nautilus.core.field.template.FieldTemplate;
import co.kaioru.nautilus.core.user.User;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static co.kaioru.nautilus.core.field.template.FieldTemplate.SCREEN_HEIGHT;
import static co.kaioru.nautilus.core.field.template.FieldTemplate.SCREEN_WIDTH;

@Getter
@Setter
public class FieldInstance implements IFieldInstance {

	private final IField field;

	private final int splitColCount, splitRowCount;
	private final List<IFieldSplit> fieldSplits;
	private final IFieldSplit splitStart, splitEnd;
	private final AtomicInteger objectIdCounter;

	public FieldInstance(Field field) {
		this.field = field;

		Point fieldSize = field.getTemplate().getFieldSize();
		this.splitColCount = (fieldSize.x + SCREEN_WIDTH - 1) / SCREEN_WIDTH;
		this.splitRowCount = (fieldSize.y + SCREEN_HEIGHT - 1) / SCREEN_HEIGHT;
		this.fieldSplits = Lists.newArrayListWithCapacity(splitColCount * splitRowCount);

		IntStream.range(0, splitRowCount).forEach(r ->
			IntStream.range(0, splitColCount).forEach(c ->
				fieldSplits.add(new FieldSplit(this, c + r * splitColCount, r, c))));

		this.splitStart = fieldSplits.get(0);
		this.splitEnd = fieldSplits.get(fieldSplits.size() - 1);

		this.objectIdCounter = new AtomicInteger(0);
	}

	@Override
	public FieldTemplate getTemplate() {
		return field.getTemplate();
	}

	@Override
	public IFieldInstance getFieldInstance() {
		return this;
	}

	@Override
	public boolean enter(User user) {
		return false;
	}

	@Override
	public boolean leave(User user) {
		return false;
	}

	public IFieldSplit getSplitFromPoint(Point point) {
		Point leftTop = getTemplate().getLeftTop();
		int xDiff = point.x - leftTop.x;
		int yDiff = point.y - leftTop.y;
		int col = xDiff / SCREEN_WIDTH;
		int row = yDiff / SCREEN_HEIGHT;
		if (xDiff < 0 || yDiff < 0 || col >= splitColCount || row >= splitRowCount) {
			return null;
		} else {
			return fieldSplits.get(col + row * splitColCount);
		}
	}

	public List<IFieldSplit> getEnclosingSplit(IFieldSplit centerSplit) {
		List<IFieldSplit> enclosingSplits = Lists.newArrayList();
		int row = centerSplit.getRow();
		int col = centerSplit.getCol();

		enclosingSplits.add(centerSplit);

		for (int i = Math.max(0, row - 1); i < Math.min(splitRowCount, row + 2); i++) {
			for (int j = Math.max(0, col - 1); j < Math.min(splitColCount, col + 2); j++) {
				IFieldSplit split = fieldSplits.get(j + i * splitColCount);
				if (split != centerSplit) {
					enclosingSplits.add(split);
				}
			}
		}

		return enclosingSplits;
	}

	@Override
	public int getAvailableObjectId() {
		if (objectIdCounter.get() > 2100000000) {
			objectIdCounter.set(0);
		}
		return objectIdCounter.getAndIncrement();
	}

}
