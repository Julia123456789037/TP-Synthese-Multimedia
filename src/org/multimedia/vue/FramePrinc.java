package org.multimedia.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serial;
import java.awt.Component;

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

	private JMenuBar 	menuBar;
	private JMenu 		mnuFile;
	private JMenuItem 	mnuOpenFile;
	private JMenuItem 	mnuSaveFile;
	private JMenuItem 	mnuSaveFileAs;
	private JMenuItem 	mnuExit;
	private JMenu		mnuEdit;
	private JMenuItem	mnuRotG;
	private JMenuItem 	mnuRotD;
	private JMenuItem 	mnuMirGD;
	private JMenuItem 	mnuMirHB;
	private JMenuItem 	mnuLumineux;
	private JMenuItem 	mnuSombre;
	private JMenuItem 	mnuNoirBlanc;
	private JMenuItem 	mnuCopy;
	private JMenuItem 	mnuCut;
	private JMenuItem 	mnuPaste;
	private JMenu		mnuTexte;
	private JMenuItem 	mnuAjTe;
	private JMenu 		mnuTailleTexte;
	private JMenu 		mnuHelp;


	private BufferedImage bFimage;
	private Color selectedColor = Color.BLACK;
    private int textSize = 12;
    private String textTexte = "";

	public FramePrinc(Controleur ctrl)
	{
		this.ctrl = ctrl;


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
		} catch (Exception e) { e.printStackTrace(); }
		
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
		
		//JDialog w = new JDialog(this);
		//w.add(new JLabel("Testing a Window!!!!!"));
		//w.setLocation(300, 300);
		//w.pack();
		//w.setVisible(true);

		this.setVisible ( true );
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private JMenuBar createMenuBar() {

		// La barre de menu à proprement parler
		this.menuBar = new JMenuBar();

		// Définition du menu déroulant "File" et de son contenu
		this.mnuFile = new JMenu( "Fichier" );
		this.mnuFile.setMnemonic( 'F' );


		this.mnuOpenFile = new JMenuItem( "Ouvrir un fichier ..." );
		this.mnuOpenFile.setIcon( new ImageIcon( ImageUtils.openImg("/open.png", true) ) );
		this.mnuOpenFile.setMnemonic( 'O' );
		this.mnuOpenFile.addActionListener( this::mnuOpenFileListener );
		this.mnuOpenFile.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK) );
		this.mnuFile.add(this.mnuOpenFile);

		this.mnuSaveFile = new JMenuItem( "Sauvegarder ..." );
		this.mnuSaveFile.setIcon( new ImageIcon( ImageUtils.openImg("/save.png", true) ) );
		this.mnuSaveFile.setMnemonic( 'S' );
		this.mnuSaveFile.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK) );
		this.mnuFile.add(this.mnuSaveFile);

		this.mnuSaveFileAs = new JMenuItem( "Sauvegarder dans le dossier..." );
		this.mnuSaveFileAs.setIcon( new ImageIcon( ImageUtils.openImg("/save_as.png", true) ) );
		this.mnuSaveFileAs.setMnemonic( 'A' );
		this.mnuSaveFileAs.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK) );
		this.mnuFile.add(this.mnuSaveFileAs);

		this.mnuFile.addSeparator();

		this.mnuExit = new JMenuItem( "Quitter" );
		this.mnuExit.setIcon( new ImageIcon( ImageUtils.openImg("/exit.png", true) ) );
		this.mnuExit.setMnemonic( 'x' );
		this.mnuExit.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_DOWN_MASK) );
		this.mnuFile.add(this.mnuExit);
		
		this.menuBar.add(this.mnuFile);
		
		// Définition du menu déroulant "Edition d'image" et de son contenu
		this.mnuEdit = new JMenu( "Edition d'image" );
		this.mnuEdit.setMnemonic( 'i' );
		
		this.mnuRotG = new JMenuItem( "Rotation à gauche" );
		this.mnuRotG.setIcon( new ImageIcon( ImageUtils.openImg("/undo.png", true) ) );
		this.mnuRotG.setMnemonic( 'G' );
		this.mnuRotG.addActionListener( this::mnuRotGListener );
		this.mnuRotG.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK) );
		this.mnuEdit.add(this.mnuRotG);

		this.mnuRotD = new JMenuItem( "Rotation à droite" );
		this.mnuRotD.setIcon( new ImageIcon( ImageUtils.openImg("/redo.png", true) ) );
		this.mnuRotD.setMnemonic( 'D' );
		this.mnuRotD.addActionListener( this::mnuRotDListener );
		this.mnuRotD.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK) );
		this.mnuEdit.add(this.mnuRotD);
		
		this.mnuEdit.addSeparator();

		this.mnuMirGD = new JMenuItem( "Miroir gauche droite" );
		this.mnuMirGD.setIcon( new ImageIcon( ImageUtils.openImg("/miroirGD.png", true) ) );
		this.mnuMirGD.setMnemonic( 'L' );
		this.mnuMirGD.addActionListener( this::mnuMirGDListener );
		this.mnuMirGD.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK) );
		this.mnuEdit.add(this.mnuMirGD);

		this.mnuMirHB = new JMenuItem( "Miroir haut bas" );
		this.mnuMirHB.setIcon( new ImageIcon( ImageUtils.openImg("/miroirHB.png", true) ) );
		this.mnuMirHB.setMnemonic( 'P' );
		this.mnuMirHB.addActionListener( this::mnuMirHBListener );
		this.mnuMirHB.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK) );
		this.mnuEdit.add(this.mnuMirHB);
		
		this.mnuEdit.addSeparator();

        this.mnuLumineux = new JMenuItem( "Rendre plus lumineux" );
		this.mnuLumineux.setIcon( new ImageIcon( ImageUtils.openImg("/luminosite.png", true) ) );
		this.mnuLumineux.setMnemonic( 'U' );
		this.mnuLumineux.addActionListener( this::mnuLumineuxListener );
		this.mnuLumineux.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK) );
		this.mnuEdit.add(this.mnuLumineux);

		this.mnuSombre = new JMenuItem( "Rendre plus Sombre" );
		this.mnuSombre.setIcon( new ImageIcon( ImageUtils.openImg("/Assombrir.png", true) ) );
		this.mnuSombre.setMnemonic( 'Y' );
		this.mnuSombre.addActionListener( this::mnuSombreListener );
		this.mnuSombre.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK) );
		this.mnuEdit.add(this.mnuSombre);
		
		this.mnuEdit.addSeparator();

        this.mnuNoirBlanc = new JMenuItem( "Noir et Blanc" );
		this.mnuNoirBlanc.setIcon( new ImageIcon( ImageUtils.openImg("/noirblanc.png", true) ) );
		this.mnuNoirBlanc.setMnemonic( 'N' );
		this.mnuNoirBlanc.addActionListener( this::mnuNoirBlancListener );
		this.mnuNoirBlanc.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK) );
		this.mnuEdit.add(this.mnuNoirBlanc);
		
		this.mnuEdit.addSeparator();
		
		this.mnuCopy = new JMenuItem( "Copier" );
		this.mnuCopy.setIcon( new ImageIcon( ImageUtils.openImg("/copy.png", true) ) );
		this.mnuCopy.setMnemonic( 'C' );
		this.mnuCopy.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK) );
		this.mnuEdit.add(this.mnuCopy);
		
		this.mnuCut = new JMenuItem( "Couper" );
		this.mnuCut.setIcon( new ImageIcon( ImageUtils.openImg("/cut.png", true) ) );
		this.mnuCut.setMnemonic( 'X' );
		this.mnuCut.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK) );
		this.mnuEdit.add(this.mnuCut);
		
		this.mnuPaste = new JMenuItem( "Coller" );
		this.mnuPaste.setIcon( new ImageIcon( ImageUtils.openImg("/paste.png", true) ) );
		this.mnuPaste.setMnemonic( 'V' );
		this.mnuPaste.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK) );
		this.mnuEdit.add(this.mnuPaste);

		this.menuBar.add(this.mnuEdit);

		
		// Définition du menu déroulant "Edition de texte" et de son contenu
		this.mnuTexte = new JMenu( "Edition de texte" );
		this.mnuTexte.setMnemonic( 'T' );
		
		this.mnuAjTe = new JMenuItem( "Ajouter un texte" );
		this.mnuAjTe.setIcon( new ImageIcon( ImageUtils.openImg("/ajoutZoneTexte.png", true) ) );
		this.mnuAjTe.setMnemonic( 'E' );
		this.mnuNoirBlanc.addActionListener( this::mnuAjTeListener );
		this.mnuAjTe.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK) );
		this.mnuTexte.add(this.mnuAjTe);

        this.mnuTailleTexte = new JMenu("Taille du texte");
		this.mnuTailleTexte.setIcon( new ImageIcon( ImageUtils.openImg("/tailleTexte.png", true) ) );
		this.mnuTailleTexte.setMnemonic('T');
		
		String[] tailles = { "8", "9", "10", "11", "12", "14", "16", "18", "20", "24", "30", "36", "48", "60", "70", "96" };

		for (String taille : tailles) {
			JMenuItem menuItem = new JMenuItem(taille);
			menuItem.addActionListener(e -> { setTextSize(Integer.parseInt(taille)); });
			this.mnuTailleTexte.add(menuItem);
		}
		this.mnuTexte.add(this.mnuTailleTexte);
		this.menuBar.add(this.mnuTexte);


		// Définition du menu déroulant "Help" et de son contenu
		this.mnuHelp = new JMenu( "Help" );
		this.mnuHelp.setMnemonic( 'H' );
		this.mnuAjTe.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK) );
		
		this.menuBar.add( this.mnuHelp );
		
		return this.menuBar;
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

	public void mnuAjTeListener(ActionEvent event) { this.panelImage.enableStylo(true); }

	public void activatePipetteMode() { this.panelImage.enablePipetteMode(true); }
	public void setSelectedColor(Color color) { this.selectedColor = color; }
	public void setTextSize(int size) { this.textSize = size; }
	public void setTextTexte(String texte) { this.textTexte = texte; }

	public void PotPeint( int x, int y ) {
		this.panelImage.transform.fillColor(x, y, this.selectedColor);
		this.panelImage.updateUI();
	}

    public void writeText( int x, int y )  {
		this.panelImage.transform.writeText(this.textTexte, x, y, this.textSize, this.selectedColor);
		this.panelImage.updateUI();
	}
	
	public PanelImage getPanelImage() { return this.panelImage; }
	public BarreOutils getBarreOutils() { return this.barreOutils; }
}