package util;

import javafx.scene.paint.Color;

public class TextColorChecker {
	public static Color getTextColor(Color bgColor) {
		double brightness =
				bgColor.getRed() * 0.299 +
				bgColor.getGreen() * 0.587 +
				bgColor.getBlue() * 0.114;

		    return brightness > 0.5 ? Color.BLACK : Color.WHITE;
	}
}
