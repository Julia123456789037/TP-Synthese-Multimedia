package org.multimedia.composants;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

public enum ModeEdition {
	NORMAL          (Cursor.getDefaultCursor()),
	PIPETTE         (getCursor("/pipette.png",     new Point( 0, 20))),
	POT_DE_PEINTURE	(getCursor("/potPeinture.png", new Point(17, 12))),
	TEXTE           (getCursor("/stylo.png",       new Point(0,  0 ))), 
	SELECTION_RECT  (getCursor("/plus.png",        new Point(12, 12))),  
	SELECTION_ROND  (getCursor("/plus.png",        new Point(12, 12))); 
	
	public final Cursor cursor;
	
	private ModeEdition(Cursor cursor) { this.cursor = cursor; }
	
	private static Cursor getCursor(String file, Point p) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage(ModeEdition.class.getResource(file));
//		image = image.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
		String filename = file.replaceAll("/", "").replaceAll("\\.(png|jpe?g|jfif)", "");
		return toolkit.createCustomCursor(image, p, filename);
	}
	
}