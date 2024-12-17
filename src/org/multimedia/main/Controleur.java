package org.multimedia.main;

import javax.swing.SwingUtilities;

import org.multimedia.vue.FramePrinc;

public class Controleur {
	private FramePrinc ihm;
	//private dddddddddd  metier;

	// Constructeur
	public Controleur ()
	{
		//this.metier = new dddddddddd ();
		this.ihm	= new FramePrinc(this);

	}
	public FramePrinc getFramePrinc() { return this.ihm; }

	public static void main(String[] a) {
		SwingUtilities.invokeLater(Controleur::new);
	}
	
}
