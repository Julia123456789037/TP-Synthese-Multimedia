package org.multimedia.metier;

import java.awt.image.BufferedImage;

public class Ovale extends Figure {
    private boolean pixelsCaptured = false;

    public Ovale(int centreX, int centreY, int tailleX, int tailleY, char c, BufferedImage background) {
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

                // Check if the point is inside the ellipse
                double normalizedX = (x - getTailleX() / 2.0) / (getTailleX() / 2.0);
                double normalizedY = (y - getTailleY() / 2.0) / (getTailleY() / 2.0);

                if (normalizedX * normalizedX + normalizedY * normalizedY <= 1) {
                    if (bgX >= 0 && bgX < background.getWidth() && bgY >= 0 && bgY < background.getHeight()) {
                        int pixel = background.getRGB(bgX, bgY);
                        getFigureImage().setRGB(x, y, pixel);
                    } else {
                        getFigureImage().setRGB(x, y, 0x00FFFFFF); // Transparent for out-of-bounds pixels
                    }
                } else {
                    getFigureImage().setRGB(x, y, 0x00FFFFFF); // Transparent outside the oval
                }
            }
        }
    }

    @Override
    public boolean possede(int x, int y) {
        double normalizedX = (x - getCentreX()) / (getTailleX() / 2.0);
        double normalizedY = (y - getCentreY()) / (getTailleY() / 2.0);
        return (normalizedX * normalizedX + normalizedY * normalizedY) <= 1;
    }
}
