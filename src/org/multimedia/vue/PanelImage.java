package org.multimedia.vue;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.Serial;

import javax.swing.*;

import org.multimedia.composants.ImageTransform;
import org.multimedia.main.Controleur;

public class PanelImage extends JPanel {
	
	@Serial
	private static final long serialVersionUID = 8341091164745892107L;
	
	private Controleur ctrl;
	private BufferedImage image;
	private boolean pipetteMode = false; // Mode pipette activé/désactivé
	@SuppressWarnings("unused")
	private Color couleurSelectionnee = Color.BLACK;
	private Cursor cursorPipette;
	
	final ImageTransform transform;

	public PanelImage(Controleur ctrl) {
		this.ctrl = ctrl;
		this.image = null;
		this.transform = new ImageTransform();

		// Charger l'icône de pipette personnalisée pour le curseur
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image pipetteImage = toolkit.getImage(getClass().getResource("/pipette.png"));
		Point hotSpot = new Point(0, 0); // Position active de la pipette (pointe)
		this.cursorPipette = toolkit.createCustomCursor(pipetteImage, hotSpot, "Pipette");

		// Ajouter un écouteur de souris pour récupérer la couleur
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (pipetteMode && image != null) {
					pickColor(e.getX(), e.getY());
				}
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		final int margin = 20;
		
		// Dessiner l'image si elle existe
		if (this.image != null) {
			BufferedImage image = this.transform.applyTransforms(this.image);
			int x = (getWidth() - image.getWidth()) / 2; // Centrer l'image horizontalement
			int y = (getHeight() - image.getHeight()) / 2; // Centrer l'image verticalement
			
			g.setColor(Color.WHITE);
			g.fillRect(x - margin, y - margin, image.getWidth() + (margin * 2), image.getHeight() + (margin * 2));
			
			// Dessiner l'image avec ses dimensions d'origine
			g.drawImage(image, x, y, this);
		}
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
		this.pipetteMode = enable;
		setCursor(this.pipetteMode ? this.cursorPipette : Cursor.getDefaultCursor());
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

	public Point getImageLocationOnScreen() { return this.getLocationOnScreen(); }
}
