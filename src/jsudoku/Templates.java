package jsudoku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Domovoy
 * Date: 05.03.2007
 * Time: 0:30:56
 * <p/>
 * This templates been taken from Sudoku by Michael Kennett (mike@laurasia.com.au)
 */

public class Templates {
	//	int templates[][] = new int[2][2];
	private HashMap<Integer, ArrayList<boolean[]>> templates;
	private static Templates templ;
	private Random rand;

	private Templates() {
		readTemplates();
//		for (int i = 20; i < 40; i++) {
//			System.out.println(i + ": " + templates.get(i).size());
//		}

		rand = new Random();
	}

	private void readTemplates() {
		templates = new HashMap<Integer, ArrayList<boolean[]>>();
		for (int i = 20; i < 41; i++) {
			ArrayList<boolean[]> list = new ArrayList<boolean[]>();
			templates.put(i, list);
		}
		Scanner scan = null;
		try {
			scan = new Scanner(new FileInputStream("templates"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int num = 0;
		int current;
		while (scan.hasNextInt()) {
			current = scan.nextInt();
			if (current > 1) {
				num = current;
				continue;
			}

			boolean field[] = new boolean[81];
			field[0] = current == 1;
			for (int i = 1; i < 81; i++) {
				field[i] = scan.nextInt() == 1;
			}
			templates.get(num).add(field);
		}
	}

	public static Templates getTemplates() {
		if (templ == null) {
			templ = new Templates();
		}
		return templ;
	}

	public boolean[] getTemplate(int difficulty) {
		ArrayList difficultyTemplates = templates.get(difficulty);
		if (difficultyTemplates.size() == 0) {
//			throw new IndexOutOfBoundsException("Difficulty " + difficulty + " not found in templates");
			return null;
		}
		int index = rand.nextInt(difficultyTemplates.size());
//		if (countOnes((boolean[]) difficultyTemplates.get(index)) == difficulty) {todo fix
			return (boolean[]) difficultyTemplates.get(index);
//		}
//		System.out.println(countOnes((boolean[]) difficultyTemplates.get(index))+" "+ difficulty);
//		return null;
	}

	private int countOnes(boolean array[]) {
		int count = 0;
		for (boolean flag : array) {
			if (flag) {
				count++;
			}
		}
		return count;
	}
}
