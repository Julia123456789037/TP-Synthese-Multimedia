package org.multimedia.composants;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

public enum ModeEdition {
	NORMAL			(Cursor.getDefaultCursor()),
	PIPETTE			(getCursor("/pipette.png",     new Point( 0, 20))),
	POT_DE_PEINTURE	(getCursor("/potPeinture.png", new Point(15, 10))),
	STYLO			(getCursor("/stylo.png", new Point(0, 0))), //TODO changer les points
	SELECTION_RECT	(getCursor("/plus.png", new Point(0, 0))),
	SELECTION_ROND	(getCursor("/plus.png", new Point(0, 0)));
	
	public final Cursor cursor;
	
	private ModeEdition(Cursor cursor) { this.cursor = cursor; }
	
//	private static Cursor getCursor(String file) {
//		return ModeEdition.getCursor(file, new Point(0, 0));
//	}
	
	private static Cursor getCursor(String file, Point p) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage(ModeEdition.class.getResource(file));
		String filename = file.replaceAll("/", "").replaceAll("\\.(png|jpe?g|jfif)", "");
		return toolkit.createCustomCursor(image, p, filename);
	}
	
}