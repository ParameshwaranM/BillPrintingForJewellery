package com.billprint.start;
import java.awt.Graphics;
public class PrintAlignment {
	public static void printCall(Graphics graphics, String data,int x,int y,TextAlignment textAlignment) {
		if(TextAlignment.right.equals(textAlignment)) {
			x=x-graphics.getFontMetrics().stringWidth(data);
			graphics.drawString(data, x, y);
		}
		else if (TextAlignment.center.equals(textAlignment)) {
			x=x-graphics.getFontMetrics().stringWidth(data)/2;
			graphics.drawString(data, x, y);
		}
		else {
			graphics.drawString(data, x, y);
		}
		
	}
}
