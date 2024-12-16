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
		mnuEdit.setMnemonic( 'E' );
		
		JMenuItem mnuUndo = new JMenuItem( "Rotation à gauche" );
		mnuUndo.setIcon( new ImageIcon( ImageUtils.openImg("/organiser.png", true) ) );
		mnuUndo.setMnemonic( 'U' );
		mnuUndo.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK) );
		mnuEdit.add(mnuUndo);

		JMenuItem mnuRedo = new JMenuItem( "Rotation à droite" );
		mnuRedo.setIcon( new ImageIcon( ImageUtils.openImg("/organiser.png", true) ) );
		mnuRedo.setMnemonic( 'R' );
		mnuRedo.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK) );
		mnuEdit.add(mnuRedo);
		
		mnuEdit.addSeparator();
		
		JMenuItem mnuCopy = new JMenuItem( "Copier" );
		mnuCopy.setIcon( new ImageIcon( ImageUtils.openImg("/copy.png", true) ) );
		mnuCopy.setMnemonic( 'C' );
		mnuCopy.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK) );
		mnuEdit.add(mnuCopy);
		
		JMenuItem mnuCut = new JMenuItem( "Couper" );
		mnuCut.setIcon( new ImageIcon( ImageUtils.openImg("/cut.png", true) ) );
		mnuCut.setMnemonic( 't' );
		mnuCut.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK) );
		mnuEdit.add(mnuCut);
		
		JMenuItem mnuPaste = new JMenuItem( "Coller" );
		mnuPaste.setIcon( new ImageIcon( ImageUtils.openImg("/paste.png", true) ) );
		mnuPaste.setMnemonic( 'P' );
		mnuPaste.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK) );
		mnuEdit.add(mnuPaste);

		menuBar.add(mnuEdit);

		
		// Définition du menu déroulant "Edition de texte" et de son contenu
		JMenu mnuTexte = new JMenu( "Edition de texte" );
		mnuTexte.setMnemonic( 'E' );
		
		JMenuItem mnuAjTe = new JMenuItem( "Ajouter un texte" );
		mnuAjTe.setIcon( new ImageIcon( ImageUtils.openImg("/ajoutZoneTexte.png", true) ) );
		mnuAjTe.setMnemonic( 'U' );
		mnuAjTe.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK) );
		mnuTexte.add(mnuAjTe);

		JMenuItem mnuTailTe = new JMenuItem( "Taille du texte" );
		mnuTailTe.setIcon( new ImageIcon( ImageUtils.openImg("/redo.png", true) ) );
		mnuTailTe.setMnemonic( 'R' );
		mnuTailTe.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK) );
		mnuTexte.add(mnuTailTe);

		JMenuItem mnuCoulTe = new JMenuItem( "Couleur du texte" );
		mnuCoulTe.setIcon( new ImageIcon( ImageUtils.openImg("/redo.png", true) ) );
		mnuCoulTe.setMnemonic( 'p' );
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


