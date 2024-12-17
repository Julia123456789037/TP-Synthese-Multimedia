package org.multimedia.metier;

import java.awt.image.BufferedImage;

public class Rectangle extends Figure {
	private boolean pixelsCaptured = false; // To ensure pixels are captured only once

	public Rectangle(int centreX, int centreY, int tailleX, int tailleY, char c, BufferedImage background) {
		super(centreX, centreY, tailleX, tailleY, c);

		// Capture pixels only when the figure is created
		if (!pixelsCaptured && background != null) {
			selectPixels(background);
			pixelsCaptured = true;
		}
	}

	@Override
	public void selectPixels(BufferedImage background) {
		int left = getCentreX() - getTailleX() / 2;
		int top = getCentreY() - getTailleY() / 2;

		for (int x = 0; x < getTailleX(); x++) {
			for (int y = 0; y < getTailleY(); y++) {
				int bgX = left + x;
				int bgY = top + y;

				if (bgX >= 0 && bgX < background.getWidth() && bgY >= 0 && bgY < background.getHeight()) {
					int pixel = background.getRGB(bgX, bgY);
					getFigureImage().setRGB(x, y, pixel);
				} else {
					getFigureImage().setRGB(x, y, 0x00FFFFFF); // Transparent for out-of-bounds pixels
				}
			}
		}
	}

	@Override
	public boolean possede(int x, int y) {
		int left = getCentreX() - getTailleX() / 2;
		int top = getCentreY() - getTailleY() / 2;
		return x >= left && x <= left + getTailleX() && y >= top && y <= top + getTailleY();
	}
}
