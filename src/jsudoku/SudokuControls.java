package jsudoku;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by IntelliJ IDEA.
 * User: Domovoy
 * Date: 04.03.2007
 * Time: 2:30:36
 */

public class SudokuControls extends JPanel implements ActionListener {
	private JSlider difficultySlider;
	private JCheckBox errorBox;
	private JLabel difficultyLabel;
	private int difficulty;
	private JTextField numberField;
	private JTextField numberField2;

	public SudokuControls(int difficulty) {
		super(new GridLayout(3, 2, 5, 5));
		this.difficulty = difficulty;
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		setPreferredSize(new Dimension(480, 160));
		add(createButton("New sudoku"));
		add(difficultySlider = createDifficultySpinner());
		add(createButton("Reset"));

		JPanel panel = new JPanel(new GridLayout(1,2));
		difficultyLabel = new JLabel();
		difficultyLabel.setHorizontalAlignment(SwingConstants.LEADING);
		setDifficultyText(difficultySlider.getValue());
		errorBox = new JCheckBox("Show errors");
		panel.add(errorBox);
		panel.add(difficultyLabel);
		add(panel);
		add(createButton("Solve"));

		JPanel savePanel = new JPanel(new GridLayout(1,2));
		JPanel numPanel = new JPanel(new GridLayout(1,3));
		numberField = new JTextField("3");
		numberField.setHorizontalAlignment(SwingConstants.CENTER);
		numberField2 = new JTextField("4");
		numberField2.setHorizontalAlignment(SwingConstants.CENTER);
		savePanel.add(createButton("Save to file"));
		numPanel.add(numberField);
		numPanel.add(new JLabel("X", SwingConstants.CENTER));
		numPanel.add(numberField2);
		savePanel.add(numPanel);
		add(savePanel);
	}

	private JButton createButton(String label) {
		JButton button = new JButton(label);
		button.addActionListener(this);
		return button;
	}

	public boolean getShowErrors() {
		return errorBox.isSelected();
	}

	private JSlider createDifficultySpinner() {
		JSlider slider = new JSlider(20, 40, difficulty);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int value = ((JSlider) e.getSource()).getValue();
				setDifficultyText(value);
				MainFrame.getMainFrame().getSudokuPanel().setSudokuModel(
						MainFrame.getMainFrame().getGenerator().createStartField(value));
			}
		});
		return slider;
	}

	private void setDifficultyText(int value) {
		String level;
		if (value <= JSudoku.VERY_HARD) {
			level = "Very hard";
		} else if (value <= JSudoku.HARD) {
			level = "Hard";
		} else if (value <= JSudoku.MEDIUM) {
			level = "Medium";
		} else {
			level = "Easy";
		}
		difficultyLabel.setText("Dificulty: " + value + "(" + level + ")");
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if (command.equals("New sudoku")) {
			MainFrame.getMainFrame().newGame(difficultySlider.getValue());
		} else if (command.equals("Reset")) {
			MainFrame.getMainFrame().reset();
		} else if (command.equals("Solve")) {
			MainFrame.getMainFrame().solve();
		}else if (command.equals("Save to file")) {
			MainFrame.getMainFrame().getSudokuPanel().toFile(Integer.parseInt(numberField.getText()),
			                                                 Integer.parseInt(numberField2.getText()));
		}
	}
}
