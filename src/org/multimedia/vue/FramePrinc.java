package org.multimedia.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serial;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.multimedia.main.Controleur;
import org.multimedia.util.ImageUtils;


public class FramePrinc extends JFrame 
{
	@Serial
	private static final long serialVersionUID = 5106104636891939306L;

	Controleur ctrl;

	BarreOutils	barreOutils;
	PanelImage	panelImage;

	JMenu mnuFile;
	JMenuItem mnuNewFile;
	BufferedImage bFimage;
	int angle;
	private Color selectedColor = Color.BLACK;

	public FramePrinc(Controleur ctrl)
	{
		this.ctrl = ctrl;
		this.angle = 0;


		this.setTitle  ( "Gestion image (contrefaçon de paint)"  );
		this.setSize   ( 1500, 950 );
		this.setLocationRelativeTo( null );
		
		try {
			String lafClassName = switch (System.getProperty("os.name")) {
				case "Linux"   -> "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
				case "Windows" -> "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
				default        -> UIManager.getCrossPlatformLookAndFeelClassName();
			};
			UIManager.setLookAndFeel(lafClassName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*-------------------------------*/
		/* Création des composants       */
		/*-------------------------------*/
		this.barreOutils = new BarreOutils(this.ctrl);
		this.panelImage  = new PanelImage(this.ctrl);

		this.setJMenuBar( this.createMenuBar() );


		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/
		this.add(this.barreOutils, BorderLayout.NORTH);
		this.add(this.panelImage,  BorderLayout.CENTER);
		
//		JDialog w = new JDialog(this);
//	    w.add(new JLabel("Testing a Window!!!!!"));
//	    w.setLocation(300, 300);
//	    w.pack();
//	    w.setVisible(true);

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
		mnuOpenFile.addActionListener( this::mnuOpenFileListener );
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
		mnuRotG.addActionListener( this::mnuRotGListener );
		mnuRotG.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK) );
		mnuEdit.add(mnuRotG);

		JMenuItem mnuRotD = new JMenuItem( "Rotation à droite" );
		mnuRotD.setIcon( new ImageIcon( ImageUtils.openImg("/redo.png", true) ) );
		mnuRotD.setMnemonic( 'D' );
		mnuRotD.addActionListener( this::mnuRotDListener );
		mnuRotD.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK) );
		mnuEdit.add(mnuRotD);
		
		mnuEdit.addSeparator();

		JMenuItem mnuMirGD = new JMenuItem( "Miroir gauche droite" );
		mnuMirGD.setIcon( new ImageIcon( ImageUtils.openImg("/miroirGD.png", true) ) );
		mnuMirGD.setMnemonic( 'L' );
		mnuMirGD.addActionListener( this::mnuMirGDListener );
		mnuMirGD.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK) );
		mnuEdit.add(mnuMirGD);

		JMenuItem mnuMirHB = new JMenuItem( "Miroir haut bas" );
		mnuMirHB.setIcon( new ImageIcon( ImageUtils.openImg("/miroirHB.png", true) ) );
		mnuMirHB.setMnemonic( 'P' );
		mnuMirHB.addActionListener( this::mnuMirHBListener );
		mnuMirHB.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK) );
		mnuEdit.add(mnuMirHB);
		
		mnuEdit.addSeparator();

        JMenuItem mnuLumineux = new JMenuItem( "Rendre plus lumineux" );
		mnuLumineux.setIcon( new ImageIcon( ImageUtils.openImg("/luminosite.png", true) ) );
		mnuLumineux.setMnemonic( 'U' );
		mnuLumineux.addActionListener( this::mnuLumineuxListener );
		mnuLumineux.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK) );
		mnuEdit.add(mnuLumineux);

		JMenuItem mnuSombre = new JMenuItem( "Rendre plus Sombre" );
		mnuSombre.setIcon( new ImageIcon( ImageUtils.openImg("/Assombrir.png", true) ) );
		mnuSombre.setMnemonic( 'Y' );
		mnuSombre.addActionListener( this::mnuSombreListener );
		mnuSombre.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK) );
		mnuEdit.add(mnuSombre);
		
		mnuEdit.addSeparator();

        JMenuItem mnuNoirBlanc = new JMenuItem( "Noir et Blanc" );
		mnuNoirBlanc.setIcon( new ImageIcon( ImageUtils.openImg("/noirblanc.png", true) ) );
		mnuNoirBlanc.setMnemonic( 'N' );
		mnuNoirBlanc.addActionListener( this::mnuNoirBlancListener );
		mnuNoirBlanc.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK) );
		mnuEdit.add(mnuNoirBlanc);
		
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

	public void mnuOpenFileListener(ActionEvent event) {
		// Créer un JFileChooser pour sélectionner un fichier
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Sélectionnez une image");

		// Filtrer pour n'autoriser que les fichiers image
				fileChooser.setFileFilter(new FileNameExtensionFilter(
			"Fichiers Image (JPG, PNG, GIF)", "jpg", "jpeg", "png", "gif"
		));

		// Afficher la boîte de dialogue et vérifier si l'utilisateur a sélectionné un fichier
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				File selectedFile = fileChooser.getSelectedFile();
				@SuppressWarnings("unused")
				String filePath = selectedFile.getAbsolutePath();

				// Charger l'image en tant que BufferedImage
				this.bFimage = ImageIO.read(selectedFile);

				// Mettre à jour l'image dans PanelImage
				this.panelImage.loadImage(this.bFimage);
				this.panelImage.transform.reset();
				this.panelImage.updateUI();

				// Message de confirmation
//				JOptionPane.showMessageDialog(this, "Image chargée : " + filePath);

			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Erreur lors du chargement de l'image.");
			}
		}
	}

	public void mnuRotGListener(ActionEvent event) { 
		this.panelImage.transform.addRotation(-90);
		this.panelImage.updateUI();
	}
	public void mnuRotDListener(ActionEvent event) { 
		this.panelImage.transform.addRotation(90);
		this.panelImage.updateUI();
	}


	public void mnuMirGDListener(ActionEvent event) { 
		this.panelImage.transform.invertH();
		this.panelImage.updateUI();
	}
	public void mnuMirHBListener(ActionEvent event) { 
		this.panelImage.transform.invertV();
		this.panelImage.updateUI();
	}

    public void mnuLumineuxListener(ActionEvent event) { 
		this.panelImage.transform.applyBrightness( 20 );
		this.panelImage.updateUI();
	}
    public void mnuSombreListener(ActionEvent event) { 
		this.panelImage.transform.applyBrightness( -20 );
		this.panelImage.updateUI();
	}

    public void mnuNoirBlancListener(ActionEvent event) { 
		this.panelImage.transform.toGreyScale(  );
		this.panelImage.updateUI();
	}

	public void activatePipetteMode() { this.panelImage.enablePipetteMode(true); }
	public void setSelectedColor(Color color) { this.selectedColor = color; }

	public void PotPeint( int x, int y ) {
		this.panelImage.transform.fillColor(x, y, this.selectedColor);
		this.panelImage.updateUI();
	}
	
	public PanelImage getPanelImage() { return this.panelImage; }
	public BarreOutils getBarreOutils() { return this.barreOutils; }
}