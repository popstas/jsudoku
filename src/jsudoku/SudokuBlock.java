package jsudoku;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Domovoy
 * Date: 03.03.2007
 * Time: 15:11:55
 */

public class SudokuBlock extends JPanel {
	private SudokuCell cells[][];

	private final int X;
	private final int Y;

	public SudokuBlock(int posX, int posY) {
		super(new GridLayout(3, 3));
		X = posX;
		Y = posY;
		setBorder(BorderFactory.createLineBorder(Color.black));
		setBackground(Color.BLACK);
		cells = new SudokuCell[3][3];
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				add(SudokuPanel.getCells()[posX*3+x][posY*3+y]);
			}
		}
	}

	public SudokuCell[][] getCells() {
		return cells;
	}

}

