package org.multimedia.vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.Serial;

import javax.swing.JPanel;

import org.multimedia.composants.ImageTransform;
import org.multimedia.composants.ModeEdition;
import org.multimedia.main.Controleur;

public class PanelImage extends JPanel {
	
	@Serial
	private static final long serialVersionUID = 8341091164745892107L;
	
	private Controleur ctrl;
	private BufferedImage image;
	
	private ModeEdition mode;
	private boolean potPeintureMode = false;
	
	final ImageTransform transform;

	public PanelImage(Controleur ctrl) {
		this.ctrl = ctrl;
		this.image = null;
		
		this.transform = new ImageTransform();
		this.mode = ModeEdition.NORMAL;

		// Ajouter un écouteur de souris pour récupérer la couleur
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (PanelImage.this.image != null) {
					int x = e.getX(), y = e.getY();
					switch (PanelImage.this.mode) {
					case NORMAL          -> {}
					case PIPETTE         -> pickColor(x, y);
					case POT_DE_PEINTURE -> paintColor(x, y);
					default              -> {}
					}
				}
			}
		});
	}

	public void setImage(BufferedImage image) { this.image = image; repaint(); }
	public boolean isPotPeintureMode() { return this.potPeintureMode; }
	public Point getImageLocationOnScreen() { return this.getLocationOnScreen(); }

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Dessiner l'image si elle existe
		if (this.image != null) {
			BufferedImage image = this.transform.applyTransforms(this.image);
			int x = (getWidth() - image.getWidth()) / 2; // Centrer l'image horizontalement
			int y = (getHeight() - image.getHeight()) / 2; // Centrer l'image verticalement
			
			g.setColor(Color.WHITE);
			g.fillRect(x, y, image.getWidth(), image.getHeight());
			
			// Dessiner l'image avec ses dimensions d'origine
			g.drawImage(image, x, y, this);
		}
		g.dispose();
	}
	
	@Override
	public void updateUI() {
		super.updateUI();
		this.repaint();
	}
	
	public void loadImage(BufferedImage image) {
		this.image = image;
	}

	public void enablePipetteMode(boolean enable) {
		this.mode = enable ? ModeEdition.PIPETTE : ModeEdition.NORMAL;
		this.setCursor(this.mode.cursor);
	}
	
	public void enablePotPeintureMode(boolean enable) {
		this.mode = enable ? ModeEdition.POT_DE_PEINTURE : ModeEdition.NORMAL;
		this.setCursor(this.mode.cursor);
	}

	private void pickColor(int x, int y) {
		// Calculer le décalage de l'image dans le panneau
		int imageX = x - (getWidth() - image.getWidth()) / 2;  // Décalage horizontal
		int imageY = y - (getHeight() - image.getHeight()) / 2; // Décalage vertical

		// Vérification que les coordonnées sont dans les limites de l'image
		if (imageX >= 0 && imageY >= 0 && imageX < image.getWidth() && imageY < image.getHeight()) {
			int rgb = image.getRGB(imageX, imageY);
			Color selectedColor = new Color(rgb);

			// Mettre à jour la couleur dans la barre d'outils
			FramePrinc frame = this.ctrl.getFramePrinc();
			if (frame != null) {
				frame.getBarreOutils().setCouleurSelectionnee(selectedColor);
			}
		} else { System.out.println("Les coordonnées sont en dehors de l'image."); }

		// Désactiver le mode pipette après avoir sélectionné la couleur
		enablePipetteMode(false);
	}

	private synchronized void paintColor(int x, int y) {
		// Calculer la position de l'image dans le panneau
		int imageX = x - (getWidth() - this.image.getWidth()) / 2;  // Décalage horizontal
		int imageY = y - (getHeight() - this.image.getHeight()) / 2; // Décalage vertical

		// Vérification que les coordonnées sont dans les limites de l'image
		if (imageX >= 0 && imageY >= 0 && imageX < image.getWidth() && imageY < image.getHeight()) {
			FramePrinc frame = this.ctrl.getFramePrinc();
			if (frame != null) { frame.PotPeint( imageX, imageY ); }
		} else { System.out.println("Les coordonnées sont en dehors de l'image."); }
	}
}
