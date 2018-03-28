package jsudoku;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Domovoy
 * Date: 04.03.2007
 * Time: 13:51:53
 */

public class RatioGridLayout extends GridLayout {
	private int minCellSize;

	// ratio Height/Width
	private double ratio = 1;

	public RatioGridLayout() {
		this(1, 0, 0, 0);
	}

	public RatioGridLayout(int rows, int cols) {
		this(rows, cols, 0, 0);
	}

	public RatioGridLayout(int rows, int cols, int minCellSize, double ratio) {
		this(rows, cols, 0, 0, minCellSize, ratio);
	}

	public RatioGridLayout(int rows, int cols, int hgap, int vgap, int minCellSize, double ratio) {
		super(rows, cols, hgap, vgap);
		this.setMinCellSize(minCellSize);
		this.setRatio(ratio);
	}

	public int getMinCellSize() {
		return minCellSize;
	}

	public void setMinCellSize(int minCellSize) {
		this.minCellSize = minCellSize;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	public void layoutContainer(Container parent) {
		synchronized (parent.getTreeLock()) {
			Insets insets = parent.getInsets();
			int ncomponents = parent.getComponentCount();
			int nrows = getRows();
			int ncols = getColumns();
			boolean ltr = parent.getComponentOrientation().isLeftToRight();

			if (ncomponents == 0) {
				return;
			}
			if (nrows > 0) {
				ncols = (ncomponents + nrows - 1) / nrows;
			} else {
				nrows = (ncomponents + ncols - 1) / ncols;
			}

			int w = parent.getWidth() - (insets.left + insets.right);
			int h = parent.getHeight() - (insets.top + insets.bottom);

			int cw = Math.max((w - (ncols - 1) * getHgap()) / ncols, minCellSize + getHgap());
			int ch = (int) (cw * ratio);

			if (ltr) {
				for (int c = 0, x = insets.left; c < ncols; c++, x += cw + getHgap()) {
					for (int r = 0, y = insets.top; r < nrows; r++, y += ch + getVgap()) {
						int i = r * ncols + c;
						if (i < ncomponents) {
							parent.getComponent(i).setBounds(x, y, cw, ch);
						}
					}
				}
			} else {
				for (int c = 0, x = parent.getWidth() - insets.right - cw; c < ncols; c++, x -= cw + getHgap()) {
					for (int r = 0, y = insets.top; r < nrows; r++, y += ch + getVgap()) {
						int i = r * ncols + c;
						if (i < ncomponents) {
							parent.getComponent(i).setBounds(x, y, cw, ch);
						}
					}
				}
			}
		}
	}

}
