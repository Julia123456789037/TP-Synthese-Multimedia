package org.multimedia.vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.Serial;

import javax.swing.JPanel;

import org.multimedia.composants.ImageTransform;
import org.multimedia.composants.ModeEdition;
import org.multimedia.main.Controleur;
import org.multimedia.util.Arithmetique;

public class PanelImage extends JPanel {
	
	@Serial
	private static final long serialVersionUID = 8341091164745892107L;
	
	private Controleur ctrl;
	private BufferedImage image;
	
	private ModeEdition mode;
	
	final ImageTransform transform;

	public PanelImage( Controleur ctrl ) {
		this.ctrl = ctrl;
		this.image = null;
		this.setFocusable(true);
		
		this.transform = new ImageTransform(ctrl);
		this.mode = ModeEdition.NORMAL;

		// Ajouter un écouteur de souris pour récupérer la couleur
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked( MouseEvent e ) {
				if ( PanelImage.this.image != null ) {
					int x = e.getX(), y = e.getY();
					switch ( PanelImage.this.mode ) {
					case NORMAL				-> {}
					case PIPETTE			-> pickColor(x, y);
					case POT_DE_PEINTURE	-> paintColor(x, y);
					case TEXTE 				-> paintTexte(x, y);
					//case SELECTION_RECT 			-> fRect(x, y); //TODO fonction fRect est la fonctionde seb pour dessiner le rectangle
					//case SELECTION_ROND 			-> fRond(x, y); //TODO fonction fRond est la fonctionde seb pour dessiner le rond
					default              	-> {}
					}
				}
			}
		});
	}
	
	public boolean isPotPeintureMode()		{  return this.mode == ModeEdition.POT_DE_PEINTURE; }
	public boolean isPipetteMode()			{  return this.mode == ModeEdition.PIPETTE; }
	public boolean isStyloMode()			{  return this.mode == ModeEdition.TEXTE; }
	public boolean isSelectionRectMode()	{  return this.mode == ModeEdition.SELECTION_RECT; }
	public boolean isSelectionRondMode() 	{  return this.mode == ModeEdition.SELECTION_ROND; }
	public Point getImageLocationOnScreen()	{ return this.getLocationOnScreen(); }

	@Override
	protected void paintComponent( Graphics g ) {
		super.paintComponent( g );
		
		// Dessiner l'image si elle existe
		if ( this.image != null ) {
			BufferedImage image = this.transform.applyTransforms( this.image );
			int x = ( getWidth()  - image.getWidth()  ) / 2;
			int y = ( getHeight() - image.getHeight() ) / 2;
			
			g.drawImage(this.drawCheckerBoard(image.getWidth(), image.getHeight()), x, y, this);
			
			// Dessiner l'image avec ses dimensions d'origine
			g.drawImage( image, x, y, this );
		}
		g.dispose();
	}
	
	private BufferedImage drawCheckerBoard(int width, int height) {
		BufferedImage res = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		int squareW = Arithmetique.commDiv(width, height) * 4;
		
		Graphics2D g2d = res.createGraphics();
		
		final Color darker   = new Color(125, 125, 125, 255 / 2);
		final Color brighter = new Color(200, 200, 200, 255 / 2);
		
		for (int y = 0; y < height / squareW; y++) {
			for (int x = 0; x < width / squareW; x++) {
				g2d.setColor((x + y) % 2 == 0 ? brighter : darker);
				g2d.fillRect(x * squareW, y * squareW, squareW, squareW);
			}
		}
		g2d.dispose();
		
		return res;
	}
	
	@Override
	public void updateUI() {
		super.updateUI();
		this.repaint();
	}
	
	public void loadImage( BufferedImage image ) { this.image = image; }

	public void enablePipetteMode( boolean enable ) {
		this.mode = enable ? ModeEdition.PIPETTE : ModeEdition.NORMAL;
		this.setCursor( this.mode.cursor );
	}
	
	public void enablePotPeintureMode( boolean enable ) {
		this.mode = enable ? ModeEdition.POT_DE_PEINTURE : ModeEdition.NORMAL;
		this.setCursor( this.mode.cursor );
	}
	
	public BufferedImage getImage() {
		return this.image;
	}

	public void enableStylo(boolean enable) {
		this.mode = enable ? ModeEdition.TEXTE : ModeEdition.NORMAL;
		this.setCursor(this.mode.cursor);
	}

	public void enableSelectionRect( boolean enable ) {
		this.mode = enable ? ModeEdition.SELECTION_RECT : ModeEdition.NORMAL;
		this.setCursor( this.mode.cursor );
	}

	public void enableSelectionRond( boolean enable ) {
		this.mode = enable ? ModeEdition.SELECTION_ROND : ModeEdition.NORMAL;
		this.setCursor( this.mode.cursor );
	}

	public void curseurMode( ) { 
		this.mode = ModeEdition.NORMAL;
		this.setCursor( this.mode.cursor ); 
	}

	private void pickColor(int x, int y) {
		BufferedImage image = this.transform.applyTransforms(this.image);
		int imageX = x - ( getWidth()  - image.getWidth()  ) / 2;
		int imageY = y - ( getHeight() - image.getHeight() ) / 2;

		// Vérification que les coordonnées sont dans les limites de l'image
		if ( imageX >= 0 && imageY >= 0 && imageX < image.getWidth() && imageY < image.getHeight() ) {
			int rgb = image.getRGB( imageX, imageY );
			Color selectedColor = new Color( rgb );

			// Mettre à jour la couleur dans la barre d'outils
			FramePrinc frame = this.ctrl.getFramePrinc();
			if ( frame != null ) { frame.getBarreOutils().setCouleurSelectionnee( selectedColor ); }
		} else { System.out.println( "Les coordonnées sont en dehors de l'image." ); }

		// Désactiver le mode pipette après avoir sélectionné la couleur
		enablePipetteMode(false);
	}

	private synchronized void paintColor(int x, int y) {
		BufferedImage image = this.transform.applyTransforms(this.image);
		
		// Calculer la position de l'image dans le panneau
		int imageX = x - (getWidth() - image.getWidth()) / 2;  // Décalage horizontal
		int imageY = y - (getHeight() - image.getHeight()) / 2; // Décalage vertical

		// Vérification que les coordonnées sont dans les limites de l'image
		if ( imageX >= 0 && imageY >= 0 && imageX < image.getWidth() && imageY < image.getHeight() ) {
			FramePrinc frame = this.ctrl.getFramePrinc();
			if (frame != null) { frame.potPeint( imageX, imageY ); }
		} else { System.out.println("Les coordonnées sont en dehors de l'image."); }
	}

	private synchronized void paintTexte(int x, int y) {
		BufferedImage image = this.transform.applyTransforms(this.image);
		
		// Calculer la position de l'image dans le panneau
		int imageX = x - (getWidth() - image.getWidth()) / 2;  // Décalage horizontal
		int imageY = y - (getHeight() - image.getHeight()) / 2; // Décalage vertical

		// Vérification que les coordonnées sont dans les limites de l'image
		if ( imageX >= 0 && imageY >= 0 && imageX < image.getWidth() && imageY < image.getHeight() ) {
			FramePrinc frame = this.ctrl.getFramePrinc();
			if ( frame != null ) { frame.writeText( imageX, imageY ); }
		} else { System.out.println( "Les coordonnées sont en dehors de l'image." ); }
	}
}
