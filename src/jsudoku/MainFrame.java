package jsudoku;

import java.awt.*;
import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: Domovoy
 * Date: 03.03.2007
 * Time: 15:00:58
 */

public class MainFrame extends JFrame {
	private static MainFrame MainFrame;
	private SudokuPanel sudokuPanel;
	private Generator generator;
	private SudokuControls controls;

	private MainFrame() {
		super();
		setTitle("jsudoku.JSudoku");
		setSize(640, 480);
		setResizable(false);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().createImage("sudoku.png"));
		Container c = getContentPane();
		c.setLayout(new BorderLayout(20, 20));
		generator = new Generator();

		sudokuPanel = new SudokuPanel();
		c.add(getSudokuPanel());

		int difficulty = (int)(Math.random()*11)+JSudoku.VERY_HARD;
		controls = new SudokuControls(difficulty);
		c.add(controls, BorderLayout.SOUTH);
		newGame(difficulty);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public boolean getShowErrors(){
		return controls.getShowErrors();
	}

	public static MainFrame getMainFrame() {//todo export in pdf/word/rtf...
		if (MainFrame == null) {
			MainFrame = new MainFrame();
		}
		return MainFrame;
	}

	public void newGame(int difficulty) {
		getGenerator().generate();
		getSudokuPanel().setSudokuModel(getGenerator().createStartField(difficulty));
	}

	public void reset() {
		getSudokuPanel().setSudokuModel(getGenerator().getStartField());
	}

	public void solve() {
		getSudokuPanel().setSudokuModel(getGenerator().getSolveField());
	}

	public Generator getGenerator() {
		return generator;
	}

	public SudokuPanel getSudokuPanel() {
		return sudokuPanel;
	}
}
