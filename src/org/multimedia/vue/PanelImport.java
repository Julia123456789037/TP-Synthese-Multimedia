package org.multimedia.vue;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

import org.multimedia.main.Controleur;
import org.multimedia.metier.Figure;

public class PanelImport extends PanelImage {
	
	@Serial
	private static final long serialVersionUID = 3841243917777198924L;
	
	private List<Figure> lstImport;

	public PanelImport(Controleur ctrl) {
		super(ctrl);
		this.lstImport = new ArrayList<>(); // Initialize with an empty list of figures specific to PanelImport
	}
/*
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
		super.paintComponent(g);
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
		if (chkCreateMode.isSelected()) {
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
	} */

}
