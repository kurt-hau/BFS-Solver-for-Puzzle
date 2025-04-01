package p1;

import java.awt.Color;
import dsUtils.Draw;

public class Display {
	public static final Color RED = new Color(255,0,0);
	public static final Color ORANGE = new Color(255, 127, 0);
	public static final Color YELLOW = new Color(255, 255, 0);
	public static final Color GREEN = new Color(0, 255, 0);
	public static final Color BLUE = new Color(0, 50, 255);
	public static final Color VIOLET = new Color(150 ,0, 255);
	
	private Draw d;
	
	public Display() {
		d = new Draw();
		d.enableDoubleBuffering();
		d.setCanvasSize(800, 270);
		d.setXscale(-100, 699);
		d.setYscale(0, 269);
		d.setFont(d.getFont().deriveFont((float)48.0));
		d.clear(Color.BLACK);
		d.setPenColor(Color.WHITE);
		d.text(-50, 150, "a");
		d.text(-50, 50, "c");
		d.text(650, 150, "b");
		d.text(650, 50, "d");
		for(int i = 1; i <= 6; i++)
			d.text(i*100-50, 220, ""+ i);
	}
	
	public void drawBoard(Color[][] grid) {
		for(int i = 0; i < 6; i++) {
			d.setPenColor(grid[0][i]);
			d.filledRectangle(i*100+50, 150, 40, 40);
			d.setPenColor(grid[1][i]);
			d.filledRectangle(i*100+50, 50, 40, 40);
			d.setPenColor(Color.BLACK);
			d.text(i*100+50, 150, colorLetter(grid[0][i]));
			d.text(i*100+50, 50, colorLetter(grid[1][i]));
			d.show();
		}
	}
	
	private String colorLetter(Color c) {
		if (c.equals(RED))
			return "R";
		if (c.equals(ORANGE))
			return "O";
		if (c.equals(YELLOW))
			return "Y";
		if (c.equals(GREEN))
			return "G";
		if (c.equals(BLUE))
			return "B";
		if (c.equals(VIOLET))
			return "V";
		throw new IllegalArgumentException("Illegal Color");
	}
	
	public char getKeyStroke() {
		while (!d.hasNextKeyTyped())
			d.pause(100);
		char key = d.nextKeyTyped();
		return key;
	}
}
