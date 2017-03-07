package co.kaioru.nautilus.data.util;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class Rect {

	private int left;
	private int right;
	private int bottom;
	private int top;

	public Rect() {
		this.left = 0;
		this.top = 0;
		this.right = 0;
		this.bottom = 0;
	}

	public Rect(int xLeft, int yTop, int xRight, int yBottom) {
		this.left = xLeft;
		this.top = yTop;
		this.right = xRight;
		this.bottom = yBottom;
	}

	public void inflate(int dx, int dy) {
		this.left -= dx;
		this.right += dx;
		this.bottom += dy;
		this.top -= dy;
	}

	public boolean contains(Point p) {
		return contains(p.x, p.y);
	}

	public boolean contains(int x, int y) {
		return left <= x && x <= right && top <= y && y <= bottom;
	}

	public static Rect copy(Rect rect) {
		return new Rect(rect.left, rect.top, rect.right, rect.bottom);
	}

}
