package org.multimedia.vue;
import org.multimedia.main.Controleur;

import javax.swing.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelImage extends JPanel {
	Controleur ctrl;
	private Image image; // Stocke l'image actuellement affichée
	private BufferedImage bFimage; 
	private boolean pipetteMode = false;

	public PanelImage(Controleur ctrl) {
		this.ctrl = ctrl;
		this.image = null;
		this.bFimage = null;

		// Ajouter un écouteur de souris pour gérer le mode pipette
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (pipetteMode && bFimage != null) {
					pickColor(e.getX(), e.getY()); // Utilisation correcte de getX() et getY()
				}
			}
		});
	}

	// Méthode pour mettre à jour l'image
	public void setImage(BufferedImage image) {
		this.bFimage = image;
		repaint(); // Redessiner le panel
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Dessiner l'image si elle existe
		if (this.bFimage != null) {
			int x = (getWidth() - this.bFimage.getWidth()) / 2; // Centrer l'image horizontalement
			int y = (getHeight() - this.bFimage.getHeight()) / 2; // Centrer l'image verticalement

			// Dessiner l'image avec ses dimensions d'origine
			g.drawImage(this.bFimage, x, y, this);
		}
	}

	// Permet d'activer ou désactiver le mode pipette
	public void enablePipetteMode(boolean enable) {
		this.pipetteMode = enable;
	}

	// Méthode pour récupérer la couleur d'un pixel
	private void pickColor(int mouseX, int mouseY) {
		// Vérifier si l'image est présente
		if (this.bFimage == null) return;

		// Obtenir les dimensions de l'image réelle
		int imgWidth = this.bFimage.getWidth();
		int imgHeight = this.bFimage.getHeight();
		int panelWidth = getWidth();
		int panelHeight = getHeight();

		// Calculer l'échelle de l'image affichée
		double scaleX = (double) imgWidth / panelWidth;
		double scaleY = (double) imgHeight / panelHeight;

		// Adapter les coordonnées du clic à l'image réelle
		int imgX = (int) (mouseX * scaleX);
		int imgY = (int) (mouseY * scaleY);

		// Limiter les coordonnées pour éviter des erreurs
		if (imgX < 0 || imgY < 0 || imgX >= imgWidth || imgY >= imgHeight) return;

		// Récupérer la couleur depuis l'image
		int rgb = this.bFimage.getRGB(imgX, imgY);
		Color color = new Color(rgb);

		// Stocker la couleur dans FramePrinc
		FramePrinc frame = (FramePrinc) SwingUtilities.getWindowAncestor(this);
		frame.setSelectedColor(color);

		// Afficher la couleur dans une boîte de dialogue
		JOptionPane.showMessageDialog(this, "Couleur sélectionnée : " + color);
	}
}