package jsudoku;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Domovoy
 * Date: 03.03.2007
 * Time: 15:11:29
 */

public class SudokuPanel extends JPanel {
	private SudokuBlock[][] blocks;
	private static SudokuCell scells[][];
	private static int num;
	private static int xx;
	private static int yy;

	public SudokuPanel() {
		super(new RatioGridLayout(3, 3, 90, 1));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
//		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(480, 480));

		scells = new SudokuCell[9][9];
		createCells(new int[9][9]);

		blocks = new SudokuBlock[3][3];
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				blocks[x][y] = new SudokuBlock(x, y);
				add(blocks[x][y]);
			}
		}
	}

	public SudokuPanel(int model[][]) {
		this();
		setSudokuModel(model);
	}

	public static void check(boolean isCheck) {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
//				System.out.println(isRight(x, y)+", "+!isCheck+", ");
				if (isRight(x, y) || !isCheck) {
					scells[x][y].setForeground(scells[x][y].getNumColor());
				} else {
					scells[x][y].setForeground(scells[x][y].getErrorColor());
				}
			}
		}
	}

	public static boolean isRight(int x, int y) {
		xx = x;
		yy = y;
		num = scells[x][y].getNum();
		if (num == 0) {
			return true;
		}
		boolean isRight = false;
		if (checkX(y) && checkY(x) && checkBlock(x, y)) {
			isRight = true;
		}
		return isRight;
	}//todo make layout

	private static boolean checkX(int y) {
		for (int x = 0; x < 9; x++) {
			if (x == xx) {
				continue;
			}
//			System.out.println(scells[x][y].getNum()+" = "+scells[xx][yy].getNum());
			if (scells[x][y].getNum() == num) {
//				System.out.println("X: "+x+","+y+"("+scells[x][y].getNum()+") = "+xx+","+yy+"("+scells[xx][yy].getNum()+")");
				return false;
			}
		}
		return true;
	}

	private static boolean checkY(int x) {
		for (int y = 0; y < 9; y++) {
			if (x == xx && y == yy) continue;
			if (scells[x][y].getNum() == num) {
//				System.out.println("X: "+x+","+y+"("+scells[x][y].getNum()+") = "+xx+","+yy+"("+scells[xx][yy].getNum()+")");
				return false;
			}
		}
		return true;
	}

	private static boolean checkBlock(int x1, int y1) {
		int startX = x1 / 3 * 3;
		int startY = y1 / 3 * 3;
		for (int x = startX; x < startX + 3; x++) {
			for (int y = startY; y < startY + 3; y++) {
				if (x == xx && y == yy) continue;
				if (scells[x][y].getNum() == num) {
//					System.out.println("checkZ false");
					return false;
				}
			}
		}
		return true;
	}

	public static void focus(int x, int y) {
		scells[x][y].requestFocus();
	}

	private void createCells(int[][] model) {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				scells[x][y] = new SudokuCell(x, y, model[x][y]);
			}
		}
	}

	public static SudokuCell[][] getCells() {
		return scells;
	}

	public SudokuBlock getBlock(int x, int y) {
		return blocks[x][y];
	}

	public void setSudokuModel(int[][] model) {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (model[x][y] == 0) {
					scells[x][y].setFinal(false);
					scells[x][y].setText("");
				} else {
					scells[x][y].setFinal(true);
					scells[x][y].setText(String.valueOf(model[x][y]));
				}
			}
		}
		validate();
	}

	public void toFile(int numberX, int numberY) {
		int offset = 20;
		BufferedImage image = new BufferedImage(getWidth()*numberX - 2*numberX+offset*(numberX-1),
		                                        getHeight()*numberY - 2*numberY+offset*(numberY-1),
		                                        BufferedImage.TYPE_INT_RGB);
		BufferedImage buffer = new BufferedImage(getWidth() - 2, getHeight() - 2, BufferedImage.TYPE_INT_RGB);

		Graphics g = image.getGraphics();
		g.fillRect(0,0,getWidth(), getHeight());
		Random r = new Random();
		int count = 1;
		for (int x = 0; x < image.getWidth(); x+=getWidth()-2+offset) {
			for (int y = 0; y < image.getHeight(); y+=getHeight()-2+offset) {
				int difficulty = 26 + r.nextInt(5);
				MainFrame.getMainFrame().newGame(difficulty);
				Graphics tempG = buffer.getGraphics();
				paint(tempG);
				g.drawImage(buffer, x, y, getWidth()-2, getHeight()-2, Color.WHITE, null);
			}
		}
		try {
			ImageIO.write(image, "jpg", new File("saved/sudoku "+ numberX+"x"+numberY+".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
