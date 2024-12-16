package org.multimedia.vue;
import org.multimedia.main.Controleur;

import java.io.Serial;

import javax.swing.*;
import java.awt.Image;
import java.awt.Graphics;


public class PanelImage extends JPanel
{
	@Serial
	private static final long serialVersionUID = -8471532996846330745L;
	
	Controleur ctrl;
	private Image image; // Stocke l'image actuellement affichée

	public PanelImage( Controleur ctrl ) {
		this.ctrl = ctrl;
		this.image = null;
	}

	// Méthode pour mettre à jour l'image
	public void setImage(Image image) {
		this.image = image;
		repaint(); // Redessiner le panel
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Dessiner l'image si elle existe
		if (image != null) {
			int x = (getWidth() - image.getWidth(this)) / 2; // Centrer l'image horizontalement
			int y = (getHeight() - image.getHeight(this)) / 2; // Centrer l'image verticalement

			// Dessiner l'image avec ses dimensions d'origine
			g.drawImage(image, x, y, this);
		}
	}
}
