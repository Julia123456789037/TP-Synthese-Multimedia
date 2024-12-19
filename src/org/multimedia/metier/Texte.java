package org.multimedia.metier;

import java.awt.image.BufferedImage;

import org.multimedia.composants.FormeFigure;
import org.multimedia.util.Text;

public class Texte extends Rectangle {
	
	private Text texte;
	
	public Texte(Text texte, int centreX, int centreY, int tailleX, int tailleY) {
		super(centreX, centreY, tailleX, tailleY, FormeFigure.TEXTE, null);
		this.texte = texte;
//		new BufferedImage(centreY, tailleX, tailleY).getSampleModel().get;
	}
	
	@Override
	public void selectPixels(BufferedImage background) {
        
    }
	
}