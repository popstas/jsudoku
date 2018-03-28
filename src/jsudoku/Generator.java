package jsudoku;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Domovoy
 * Date: 03.03.2007
 * Time: 22:15:33
 */

public class Generator {
	private int solveField[][];
	private int startField[][];
	private LinkedList variants[][] = new LinkedList[10][9];
	private int block[][] = {{0, 0}, {0, 3}, {0, 6}, {3, 0}, {3, 3}, {3, 6}, {6, 0}, {6, 3}, {6, 6}};
	private Templates templates;
//	private jsudoku.SudokuPanel panel;

	public Generator() {
		templates = Templates.getTemplates();
	}

	int[][] generate() {
		solveField = new int[9][9];
//		JFrame frame = new JFrame("test");
//		panel = new jsudoku.SudokuPanel(solveField);
//		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//		frame.add(panel);
//		frame.pack();
//		frame.setLocationRelativeTo(null);
//		frame.setVisible(true);

		int lastNum = 1;
		for (int num = 1; num < 10; num++) {

			int lastBlock = -1;
			for (int blockIndex = 0; blockIndex < 9; blockIndex++) {

//				System.out.println("blockIndex="+blockIndex+", lastBlock="+lastBlock+", num="+num+", lastNum="+lastNum);
				if (blockIndex > lastBlock && num >= lastNum) {
					variants[num][blockIndex] = getVariants(block[blockIndex], num);
				} else {
					if(blockIndex>6){
					}
					removeNum((int[])variants[num][blockIndex].removeLast());
//					toWindow();
				}
				lastBlock = blockIndex;
				lastNum = num;

				if (variants[num][blockIndex].size() > 0) {
					addNum((int[])variants[num][blockIndex].getLast(), num);
//					toWindow();

				} else {
					if (blockIndex == 0) {
						num--;
						blockIndex = 7;
					} else {
						blockIndex -= 2;
					}
				}
			}
		}
		return solveField;
	}

	public int[][] getSolveField(){
		return solveField;
	}

	public int[][] getStartField(){
		return startField;
	}

	public int[][] createStartField(int difficulty){
		startField = new int[9][9];
		if(createFromTemplate(difficulty)!=null){
			return startField;
		}

		Random r = new Random();
		for(int i = 0; i < difficulty; i++){
			int x=r.nextInt(9);
			int y = r.nextInt(9);
			if(startField[x][y]!=0){
				i--;
				continue;
			}
			startField[x][y] = solveField[x][y];
		}

		return startField;
	}

	private int[][] createFromTemplate(int difficulty){
		boolean template[] = templates.getTemplate(difficulty);
		if(template==null){
			return null;
		}

		int index = 0;
		for(int x = 0; x < 9; x++){
			for(int y = 0; y < 9; y++){
				if(template[index]){
					startField[x][y] = solveField[x][y];
				}
				index++;
			}
		}
		return startField;
	}

	private void toWindow() {
//		panel.setSudokuModel(solveField);
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void addNum(int coord[], int num) {
		solveField[coord[0]][coord[1]] = num;
	}

	private void removeNum(int coord[]) {
		solveField[coord[0]][coord[1]] = 0;
	}

	LinkedList getVariants(int blockStart[], int num) {
		LinkedList list = new LinkedList();
		for (int x = blockStart[0]; x < blockStart[0] + 3; x++) {
			for (int y = blockStart[1]; y < blockStart[1] + 3; y++) {
				if (solveField[x][y] != 0) {
					continue;
				}
				if (isRight(x, y, num)) {
					list.addFirst(new int[]{x, y});
				}
			}
		}
		Collections.shuffle(list);
		return list;
	}

	private boolean isRight(int x, int y, int num) {
		for (int x1 = 0; x1 < 9; x1++) {
			if (x1 == x) {
				continue;
			}
			if (solveField[x1][y] == num) {
				return false;
			}
		}

		for (int y1 = 0; y1 < 9; y1++) {
			if (y1 == y) {
				continue;
			}
			if (solveField[x][y1] == num) {
				return false;
			}
		}

		int startX = x / 3 * 3;
		int startY = y / 3 * 3;
		for (int x1 = startX; x1 < startX + 3; x1++) {
			for (int y1 = startY; y1 < startY + 3; y1++) {
				if (x1 == x && y1 == y) {
					continue;
				}
				if (solveField[x1][y1] == num) {
					return false;
				}
			}
		}

		return true;
	}
}
