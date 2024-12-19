package org.multimedia.metier;

import java.awt.image.BufferedImage;

import org.multimedia.composants.FormeFigure;

public abstract class Figure {
	private int centreX;
	private int centreY;
	private int tailleX;
	private int tailleY;
	private FormeFigure type;
	private BufferedImage figureImage;
	private boolean pixelsCaptured; // New flag to track pixel capture
	private boolean selected;

	public Figure(int centreX, int centreY, int tailleX, int tailleY, FormeFigure type) {
		this.centreX = centreX;
		this.centreY = centreY;
		this.tailleX = tailleX;
		this.tailleY = tailleY;
		this.type = type;
		this.pixelsCaptured = false; // Initially, pixels are not captured
		this.initializefigureImage(tailleX, tailleY);
	}

	public void initializefigureImage(int width, int height) {
		this.figureImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	
		// Fill all pixels with transparency
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				figureImage.setRGB(x, y, 0x00000000); 
			}
		}
	}

	public BufferedImage getFigureImage() {
		return figureImage;
	}

	public int getCentreX() {
		return centreX;
	}

	public int getCentreY() {
		return centreY;
	}

	public int getTailleX() {
		return tailleX;
	}

	public int getTailleY() {
		return tailleY;
	}

	public FormeFigure getType() {
		return type;
	}

	public void setCentreX(int n)
	{
		this.centreX=n;
	}

	public void setCentreY(int n)
	{
		this.centreY=n;
	}

	public void deplacerX(int x) {
		this.centreX += x;
	}

	public void deplacerY(int y) {
		this.centreY += y;
	}

	public boolean isPixelsCaptured() {
		return pixelsCaptured;
	}

	public void setPixelsCaptured(boolean captured) {
		this.pixelsCaptured = captured;
	}

	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	// Abstract method for selecting pixels, must be overridden in subclasses
	public abstract void selectPixels(BufferedImage background);

	public abstract boolean possede(int x, int y);




}
