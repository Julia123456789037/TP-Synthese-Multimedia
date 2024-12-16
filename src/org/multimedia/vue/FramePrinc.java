package org.multimedia.vue;
import org.multimedia.main.Controleur;
import org.multimedia.util.ImageUtils;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

public class FramePrinc extends JFrame 
{
	Controleur ctrl;

	PanelBouton	panelBouton;
	PanelImage	panelImage;

	JMenu mnuFile;
	JMenuItem mnuNewFile;

	public FramePrinc(Controleur ctrl)
	{
		this.ctrl = ctrl;


		this.setTitle  ( "Gestion image (contrefaçon de paint)"  );
		this.setSize   ( 1500, 950 );
		this.setLocationRelativeTo( null );

		/*-------------------------------*/
		/* Création des composants       */
		/*-------------------------------*/
		this.panelBouton  = new PanelBouton  (this.ctrl);
		this.panelImage = new PanelImage (this.ctrl);

		this.setJMenuBar( this.createMenuBar() );


		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/
		this.add ( this.panelBouton,  BorderLayout.NORTH );
		this.add ( this.panelImage, BorderLayout.CENTER  );

		this.setVisible ( true );
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private JMenuBar createMenuBar() {

		// La barre de menu à proprement parler
		JMenuBar menuBar = new JMenuBar();

		// Définition du menu déroulant "File" et de son contenu
		JMenu mnuFile = new JMenu( "Fichier" );
		mnuFile.setMnemonic( 'F' );


		JMenuItem mnuOpenFile = new JMenuItem( "Ouvrir un fichier ..." );
		mnuOpenFile.setIcon( new ImageIcon( ImageUtils.openImg("/open.png", true) ) );
		mnuOpenFile.setMnemonic( 'O' );
		mnuOpenFile.addActionListener( this::mnuNewListener );
		mnuOpenFile.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK) );
		mnuFile.add(mnuOpenFile);

		JMenuItem mnuSaveFile = new JMenuItem( "Sauvegarder ..." );
		mnuSaveFile.setIcon( new ImageIcon( ImageUtils.openImg("/save.png", true) ) );
		mnuSaveFile.setMnemonic( 'S' );
		mnuSaveFile.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK) );
		mnuFile.add(mnuSaveFile);

		JMenuItem mnuSaveFileAs = new JMenuItem( "Sauvegarder dans le dossier..." );
		mnuSaveFileAs.setIcon( new ImageIcon( ImageUtils.openImg("/save_as.png", true) ) );
		mnuSaveFileAs.setMnemonic( 'A' );
		mnuSaveFile.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK) );
		mnuFile.add(mnuSaveFileAs);

		mnuFile.addSeparator();

		JMenuItem mnuExit = new JMenuItem( "Quitter" );
		mnuExit.setIcon( new ImageIcon( ImageUtils.openImg("/exit.png", true) ) );
		mnuExit.setMnemonic( 'x' );
		mnuExit.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_DOWN_MASK) );
		mnuFile.add(mnuExit);
		
		menuBar.add(mnuFile);
		
		// Définition du menu déroulant "Edition d'image" et de son contenu
		JMenu mnuEdit = new JMenu( "Edition d'image" );
		mnuEdit.setMnemonic( 'I' );
		
		JMenuItem mnuRotG = new JMenuItem( "Rotation à gauche" );
		mnuRotG.setIcon( new ImageIcon( ImageUtils.openImg("/undo.png", true) ) );
		mnuRotG.setMnemonic( 'G' );
		mnuRotG.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK) );
		mnuEdit.add(mnuRotG);

		JMenuItem mnuRotD = new JMenuItem( "Rotation à droite" );
		mnuRotD.setIcon( new ImageIcon( ImageUtils.openImg("/redo.png", true) ) );
		mnuRotD.setMnemonic( 'D' );
		mnuRotD.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK) );
		mnuEdit.add(mnuRotD);
		
		mnuEdit.addSeparator();

		JMenuItem mnuMirGD = new JMenuItem( "Miroir gauche droite" );
		mnuMirGD.setIcon( new ImageIcon( ImageUtils.openImg("/miroirGD.png", true) ) );
		mnuMirGD.setMnemonic( 'L' );
		mnuMirGD.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK) );
		mnuEdit.add(mnuMirGD);

		JMenuItem mnuMirHB = new JMenuItem( "Miroir haut bas" );
		mnuMirHB.setIcon( new ImageIcon( ImageUtils.openImg("/miroirHB.png", true) ) );
		mnuMirHB.setMnemonic( 'P' );
		mnuMirHB.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK) );
		mnuEdit.add(mnuMirHB);
		
		mnuEdit.addSeparator();
		
		JMenuItem mnuCopy = new JMenuItem( "Copier" );
		mnuCopy.setIcon( new ImageIcon( ImageUtils.openImg("/copy.png", true) ) );
		mnuCopy.setMnemonic( 'C' );
		mnuCopy.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK) );
		mnuEdit.add(mnuCopy);
		
		JMenuItem mnuCut = new JMenuItem( "Couper" );
		mnuCut.setIcon( new ImageIcon( ImageUtils.openImg("/cut.png", true) ) );
		mnuCut.setMnemonic( 'X' );
		mnuCut.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK) );
		mnuEdit.add(mnuCut);
		
		JMenuItem mnuPaste = new JMenuItem( "Coller" );
		mnuPaste.setIcon( new ImageIcon( ImageUtils.openImg("/paste.png", true) ) );
		mnuPaste.setMnemonic( 'V' );
		mnuPaste.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK) );
		mnuEdit.add(mnuPaste);

		menuBar.add(mnuEdit);

		
		// Définition du menu déroulant "Edition de texte" et de son contenu
		JMenu mnuTexte = new JMenu( "Edition de texte" );
		mnuTexte.setMnemonic( 'T' );
		
		JMenuItem mnuAjTe = new JMenuItem( "Ajouter un texte" );
		mnuAjTe.setIcon( new ImageIcon( ImageUtils.openImg("/ajoutZoneTexte.png", true) ) );
		mnuAjTe.setMnemonic( 'A' );
		mnuAjTe.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK) );
		mnuTexte.add(mnuAjTe);

		JMenuItem mnuTailTe = new JMenuItem( "Taille du texte" );
		mnuTailTe.setIcon( new ImageIcon( ImageUtils.openImg("/redo.png", true) ) );
		mnuTailTe.setMnemonic( 'T' );
		mnuTailTe.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK) );
		mnuTexte.add(mnuTailTe);

		JMenuItem mnuCoulTe = new JMenuItem( "Couleur du texte" );
		mnuCoulTe.setIcon( new ImageIcon( ImageUtils.openImg("/redo.png", true) ) );
		mnuCoulTe.setMnemonic( 'C' );
		mnuCoulTe.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK) );
		mnuTexte.add(mnuCoulTe);

		menuBar.add(mnuTexte);


		// Définition du menu déroulant "Help" et de son contenu
		JMenu mnuHelp = new JMenu( "Help" );
		mnuHelp.setMnemonic( 'H' );
		
		menuBar.add( mnuHelp );
		

		
		return menuBar;
	}

	public void mnuNewListener( ActionEvent event ) {
		JOptionPane.showMessageDialog( this, "Button clicked !" );
	}

}


