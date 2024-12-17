package org.multimedia.vue;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.*;

import org.multimedia.main.Controleur;

public class PanelImage extends JPanel {
	private Controleur ctrl;
	private BufferedImage image;
	private boolean pipetteMode = false; // Mode pipette activé/désactivé
	private boolean potPeintureMode = false;
	private Color couleurSelectionnee = Color.BLACK;
	private Cursor cursorPipette;
	private Cursor cursorPotPeinture;


	public PanelImage(Controleur ctrl) {
		this.ctrl = ctrl;
		this.image = null;

		// Charger l'icône de pipette personnalisée pour le curseur
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image pipetteImage = toolkit.getImage(getClass().getResource("/pipette.png"));
		Image potPeintImage = toolkit.getImage(getClass().getResource("/potPeinture.png"));
		Point hotSpot = new Point(0, 0); // Position active de la pipette (pointe)
		this.cursorPipette = toolkit.createCustomCursor(pipetteImage, hotSpot, "Pipette");
		this.cursorPotPeinture = toolkit.createCustomCursor(potPeintImage, hotSpot, "potPeinture");

		// Ajouter un écouteur de souris pour récupérer la couleur
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (pipetteMode && image != null) { pickColor(e.getX(), e.getY()); }
				if (potPeintureMode && image != null) { paintColor(e.getX(), e.getY()); }
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
			int x = (getWidth() - this.image.getWidth()) / 2; // Centrer l'image horizontalement
			int y = (getHeight() - this.image.getHeight()) / 2; // Centrer l'image verticalement

			// Dessiner l'image avec ses dimensions d'origine
			g.drawImage(this.image, x, y, this);
		}
	}

	public void enablePipetteMode(boolean enable) {
		this.pipetteMode = enable;
		if (enable) { setCursor(cursorPipette); } 
		else { setCursor(Cursor.getDefaultCursor()); }
	}

	public void enablePotPeintureMode(boolean enable) {
		this.potPeintureMode = enable;
		if (enable) { setCursor(cursorPotPeinture); } 
		else { setCursor(Cursor.getDefaultCursor()); }
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

	private void paintColor(int x, int y) {
		// Calculer la position de l'image dans le panneau
		int imageX = x - (getWidth() - this.image.getWidth()) / 2;  // Décalage horizontal
		int imageY = y - (getHeight() - this.image.getHeight()) / 2; // Décalage vertical

		// Vérification que les coordonnées sont dans les limites de l'image
		if (imageX >= 0 && imageY >= 0 && imageX < image.getWidth() && imageY < image.getHeight()) {
			FramePrinc frame = this.ctrl.getFramePrinc();
			if (frame != null) {
				frame.PotPeint( imageX, imageY,couleurSelectionnee);
				System.out.println("Pot de peinture appliqué à (" + imageX + ", " + imageY + ")");
			}
		} else { System.out.println("Les coordonnées sont en dehors de l'image."); }
	}
}
