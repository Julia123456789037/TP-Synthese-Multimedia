package org.multimedia.test;

public class TestRotation {
	
	public static void main(String[] args) {
//		final double angle = 45;
//		BufferedImage imageS = ImageUtils.openImg("/blake_decode.png", true);
//
//		int largeur = imageS.getWidth();
//		int hauteur = imageS.getHeight();
//
//		// Convertir l'angle en radians
//		double angleRadians = Math.toRadians(angle);
//
//		// Calcul des dimensions de l'image après rotation
//		int nouvelleLargeur = (int) Math.abs(largeur * Math.cos(angleRadians)) + (int) Math.abs(hauteur * Math.sin(angleRadians));
//		int nouvelleHauteur = (int) Math.abs(hauteur * Math.cos(angleRadians)) + (int) Math.abs(largeur * Math.sin(angleRadians));
//
//		BufferedImage combinedImage = new BufferedImage(nouvelleLargeur, nouvelleHauteur, BufferedImage.TYPE_INT_ARGB);
//
//		// Centre des images source et destination
//		int centreXSource = largeur / 2;
//		int centreYSource = hauteur / 2;
//		int centreXDest = nouvelleLargeur / 2;
//		int centreYDest = nouvelleHauteur / 2;
//
//		// Parcourir chaque pixel de l'image destination
//		for (int xDest = 0; xDest < nouvelleLargeur; xDest++) {
//		for (int yDest = 0; yDest < nouvelleHauteur; yDest++) {
//		// Coordonnées par rapport au centre de l'image destination
//		int dx = xDest - centreXDest;
//		int dy = yDest - centreYDest;
//
//		// Calcul des coordonnées inverses dans l'image source
//		double xSourceF = dx * Math.cos(-angleRadians) - dy * Math.sin(-angleRadians) + centreXSource;
//		double ySourceF = dx * Math.sin(-angleRadians) + dy * Math.cos(-angleRadians) + centreYSource;
//
//		// Calcul des indices entiers pour les 4 pixels sources voisins
//		int xSource = (int) Math.floor(xSourceF);
//		int ySource = (int) Math.floor(ySourceF);
//
//		// Facteurs pour l'interpolation pondérée (fraction de sous-pixels)
//		double xFrac = xSourceF - xSource;
//		double yFrac = ySourceF - ySource;
//
//		if (xSource >= 0 && xSource < largeur - 1 && ySource >= 0 && ySource < hauteur - 1) {
//		// Récupérer les 4 pixels voisins
//		int pixelHG = imageS.getRGB(xSource, ySource);        // Haut gauche (HG)
//		int pixelHD = imageS.getRGB(xSource + 1, ySource);    // Haut droite (HD)
//		int pixelBG = imageS.getRGB(xSource, ySource + 1);    // Bas gauche (BG)
//		int pixelBD = imageS.getRGB(xSource + 1, ySource + 1);// Bas droite (BD)
//
//		// Calcul de la valeur moyenne pour chaque canal (R, G, B, A)
//		int r = (int) (getChannelValue(pixelHG, 'r') * (1 - xFrac) * (1 - yFrac) +
//		getChannelValue(pixelHD, 'r') * xFrac * (1 - yFrac) +
//		getChannelValue(pixelBG, 'r') * (1 - xFrac) * yFrac +
//		getChannelValue(pixelBD, 'r') * xFrac * yFrac);
//
//		int g = (int) (getChannelValue(pixelHG, 'g') * (1 - xFrac) * (1 - yFrac) +
//		getChannelValue(pixelHD, 'g') * xFrac * (1 - yFrac) +
//		getChannelValue(pixelBG, 'g') * (1 - xFrac) * yFrac +
//		getChannelValue(pixelBD, 'g') * xFrac * yFrac);
//
//		int b = (int) (getChannelValue(pixelHG, 'b') * (1 - xFrac) * (1 - yFrac) +
//		getChannelValue(pixelHD, 'b') * xFrac * (1 - yFrac) +
//		getChannelValue(pixelBG, 'b') * (1 - xFrac) * yFrac +
//		getChannelValue(pixelBD, 'b') * xFrac * yFrac);
//
//		int a = (int) (getChannelValue(pixelHG, 'a') * (1 - xFrac) * (1 - yFrac) +
//		getChannelValue(pixelHD, 'a') * xFrac * (1 - yFrac) +
//		getChannelValue(pixelBG, 'a') * (1 - xFrac) * yFrac +
//		getChannelValue(pixelBD, 'a') * xFrac * yFrac);
//
//		// Combiner les canaux dans un pixel ARGB
//		int rgba = (a << 24) | (r << 16) | (g << 8) | b;
//
//		// Définir le pixel destination
//		combinedImage.setRGB(xDest, yDest, rgba);
//		} else {
//		// Si hors de l'image source, garder la transparence
//		combinedImage.setRGB(xDest, yDest, 0x00000000); // Transparence
//		}
//		}
//		}
//
//		// Recadrage de l'image pour enlever les parties transparentes
//		BufferedImage croppedImage = cropTransparent(combinedImage);
//
//		// Sauvegarder l'image après rotation et recadrage
//		ImageIO.write(croppedImage, "png", new File(outputImagePath));
//		System.out.println("Image rotation et sauvegardée sous : " + outputImagePath);
	}
	
}