package org.multimedia.test;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

public class TestJToolBar {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Exemple JToolBar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);

        // Création de la barre d'outils horizontale
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(true); // Permet le déplacement

        // Ajout des boutons à la barre d'outils
        toolbar.add(new JButton("Bouton 1"));
        toolbar.add(new JButton("Bouton 2"));
        toolbar.add(new JButton("Bouton 3"));

        // Ajout des composants au JFrame
        frame.setLayout(new BorderLayout());
        frame.add(toolbar, BorderLayout.NORTH); // Positionnement en haut
        frame.add(new JTextArea(), BorderLayout.CENTER);

        frame.setVisible(true);
    }
	
}