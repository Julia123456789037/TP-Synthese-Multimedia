package org.multimedia.vue;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.*;

import org.multimedia.main.Controleur;
import org.multimedia.metier.Figure;
import java.util.Collections;

public class PanelImport extends PanelImage {
	private ArrayList<Figure> lstImport;

	public PanelImport(Controleur ctrl) {
		super(ctrl);
		this.lstImport = new ArrayList<>(); // Initialize with an empty list of figures specific to PanelImport

		this.removeMouseListener(this.gereSouris);
		this.removeMouseMotionListener(this.gereSouris);
		this.gereSouris = null;

		GereSouris gereSourisImport = new GereSouris();
		this.addMouseListener(gereSourisImport);
		this.addMouseMotionListener(gereSourisImport);

	}

	// Add figures to the secondary list and shared controller
	public void addToSecondaryList(int x, int y, int tx, int ty, char c, BufferedImage bi) {
		// This adds the figure only to lstImport, not to the global figure list in ctrl
		this.ctrl.ajouterFigure(x, y, tx, ty, c, bi);
		lstImport.add(this.ctrl.getFigure(this.ctrl.getNbFigure() - 1));
		repaint();
		System.out.println("Figure added to import list");
	}

	@Override
	protected void paintComponent(Graphics g) {
		// super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		// 1. Draw the image if it exists
		if (this.image != null) {
			int x = (getWidth() - this.image.getWidth()) / 2; // Center the image horizontally
			int y = (getHeight() - this.image.getHeight()) / 2; // Center the image vertically

			// Draw the image with its original dimensions
			g.drawImage(this.image, x, y, this);
		}

		// 2. Draw all figures from the secondary list (lstImport)
		for (int i = 0; i < this.lstImport.size(); i++) {
			Figure fig = this.lstImport.get(i);

			if (fig != null) {
				int left = fig.getCentreX() - fig.getTailleX() / 2;
				int top = fig.getCentreY() - fig.getTailleY() / 2;

				BufferedImage figureImage = fig.getFigureImage();
				if (figureImage != null) {
					g2.drawImage(figureImage, left, top, null);
				}

				if (fig.isSelected()) {
					g2.setColor(this.couleurSelectionnee);
					switch (fig.getType()) {
						case 'r':
							g2.drawRect(left, top, fig.getTailleX(), fig.getTailleY());
							break;

						case 'o':
							g2.drawOval(left, top, fig.getTailleX(), fig.getTailleY());
							break;
					}
				}
			}
		}

		// 3. Draw the preview of the figure being created (if in create mode)
		if (this.creationFigure != ' ') {
			int left = Math.min(startX, currentX);
			int top = Math.min(startY, currentY);
			int width = Math.abs(currentX - startX);
			int height = Math.abs(currentY - startY);

			g2.setColor(Color.GRAY);
			g2.setStroke(new BasicStroke(2));

			if (JcbFigure.getSelectedItem() == lblRect) {
				g2.drawRect(left, top, width, height);
			} else if (JcbFigure.getSelectedItem() == lblOval) {
				g2.drawOval(left, top, width, height);
			}
		}
	}

	private class GereSouris extends MouseAdapter {

		private Figure figSelected;

		@Override
		public void mousePressed(MouseEvent e) {
			if (creationFigure != ' ') {
				startX = e.getX();
				startY = e.getY();
				currentX = startX;
				currentY = startY;
			} else {
				boolean foundFigure = false;
				for (int i = 0; i < PanelImport.this.lstImport.size(); i++) {
					Figure fig = PanelImport.this.lstImport.get(i);
					if (fig != null && fig.possede(e.getX(), e.getY())) {
						this.figSelected = fig;
						startX = e.getX();
						startY = e.getY();
						foundFigure = true;
					} else {
						fig.setSelected(false);
					}
				}
				if (this.figSelected != null)
					this.figSelected.setSelected(true);

				if (!foundFigure) {
					for (int i = 0; i < PanelImport.this.ctrl.getNbFigure(); i++) {
						PanelImport.this.ctrl.getFigure(i).setSelected(false);
					}
					this.figSelected = null;
				}
				PanelImport.this.repaint();
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (creationFigure != ' ') {
				currentX = e.getX();
				currentY = e.getY();
				PanelImport.this.repaint(); // Repaint to update the preview
			} else if (this.figSelected != null) {
				int deltaX = e.getX() - startX;
				int deltaY = e.getY() - startY;
				PanelImport.this.ctrl.deplacerFigure(PanelImport.this.ctrl.getIndiceFigure(this.figSelected), deltaX,
						deltaY);
				startX = e.getX();
				startY = e.getY();
				PanelImport.this.repaint();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (creationFigure != ' ') {
				// Calculate the width and height of the figure
				int width = Math.abs(currentX - startX);
				int height = Math.abs(currentY - startY);

				// Calculate the center coordinates of the figure relative to the image
				int x = (startX + currentX) / 2;
				int y = (startY + currentY) / 2;

				// Calculate the offset due to the centering of the image
				int offsetX = (getWidth() - image.getWidth()) / 2;
				int offsetY = (getHeight() - image.getHeight()) / 2;

				// Adjust the coordinates to account for the image offset
				x -= offsetX;
				y -= offsetY;

				// Create the figure based on the selected type

				PanelImport.this.ctrl.ajouterFigure(x, y, width, height, creationFigure, image);
				PanelImport.this.lstImport.add(PanelImport.this.ctrl.getFigure(PanelImport.this.ctrl.getNbFigure()-1));

				PanelImport.this.enableSelectionRect(false);
				PanelImport.this.enableSelectionRond(false);
				PanelImport.this.setCreationFigure(' ');
				PanelImport.this.repaint();
			}
		}
	}

}
