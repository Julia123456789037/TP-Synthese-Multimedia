package org.multimedia.main;

import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import org.multimedia.vue.FramePrinc;

public class Controleur {
    private FramePrinc ihm;
	//private dddddddddd  metier;

	// Constructeur
	public Controleur ()
	{
		//this.metier = new dddddddddd ();
		this.ihm    = new FramePrinc(this);

	}

    public static void main(String[] a){ new Controleur(); }
    
}
