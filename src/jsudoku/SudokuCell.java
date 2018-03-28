package jsudoku;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Domovoy
 * Date: 03.03.2007
 * Time: 15:11:46
 */

public class SudokuCell extends JTextField implements FocusListener, KeyListener {

	private final int X;
	private final int Y;
	private boolean isFinal = false;
	private Color numColor;
	private Color errorColor;

	public SudokuCell(int posX, int posY) {
		super(1);
		X = posX;
		Y = posY;
		setHorizontalAlignment(SwingConstants.CENTER);
		setCaret(new DefaultCaret());
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		Font font = new Font(null, Font.BOLD, 18);
		numColor = Color.GRAY;
		errorColor = Color.RED;
		setFont(font);
		setText(String.valueOf(new Random().nextInt(9) + 1));
		setEditable(false);
		setBackground(Color.WHITE);
		addFocusListener(this);
		addKeyListener(this);
	}

	public SudokuCell(int posX, int posY, int num) {
		this(posX, posY);
		if (num == 0) {
			setText("");
		} else {
			setText(String.valueOf(num));
		}
	}

	public void validate() {
		if (getText().equals("0")) {
			setText("");
		}
		super.validate();
	}

	public void focusGained(FocusEvent e) {
		setBackground(Color.LIGHT_GRAY);
	}

	public void focusLost(FocusEvent e) {
		setBackground(Color.WHITE);
	}

	public void keyTyped(KeyEvent e) {
		if(isFinal){
			return;
		}

		int c = e.getKeyChar();
		if (49 <= c && c <= 57) {
			int d = c - 48;
			setText(String.valueOf(d));
			check();
		} else if (c == KeyEvent.VK_0 || c == KeyEvent.VK_BACK_SPACE ||
		           c == KeyEvent.VK_DELETE || c == KeyEvent.VK_SPACE) {
			setText("");
			check();
		}
	}

	private void check(){
		if(MainFrame.getMainFrame().getShowErrors()){
			SudokuPanel.check(true);
		}else{
			SudokuPanel.check(false);
		}
	}

	public int getNum(){
		if(getText().equals("")){
		return 0;
	}
		return Integer.parseInt(getText());
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				SudokuPanel.focus(Math.max(X-1, 0) , Y);
				break;
			case KeyEvent.VK_DOWN:
				SudokuPanel.focus(Math.min(X+1, 8), Y);
				break;
			case KeyEvent.VK_LEFT:
				SudokuPanel.focus(X, Math.max(Y-1, 0));
				break;
			case KeyEvent.VK_RIGHT:
				SudokuPanel.focus(X, Math.min(Y+1, 8));
				break;
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public boolean isFinal() {
		return isFinal;
	}

	public void setFinal(boolean aFinal) {
		isFinal = aFinal;
		if(aFinal){
			numColor = Color.BLACK;
			errorColor = Color.RED;
		}else{
			numColor = Color.GRAY;
			errorColor = Color.PINK;
		}
		setForeground(numColor);
	}

	public Color getNumColor() {
		return numColor;
	}

	public Color getErrorColor() {
		return errorColor;
	}
}
