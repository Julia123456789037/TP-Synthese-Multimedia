package org.multimedia.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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


public class FramePrinc extends JFrame implements KeyListener, WindowListener
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
	
	private boolean isSaved;
	
	public final String titre;
	
	private File fichierOuvert;
	
	public FramePrinc(Controleur ctrl)
	{
		this.ctrl = ctrl;
		this.angle = 0;
		this.isSaved = false;
		this.titre = "Gestion image (contrefaçon de paint)";

		this.setTitle  ( this.titre  );
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
		
		this.addKeyListener(this);
		this.addWindowListener(this);
		
		this.setVisible ( true );
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	private JMenuBar createMenuBar() {

		// La barre de menu à proprement parler
		JMenuBar menuBar = new JMenuBar();

		// Définition du menu déroulant "File" et de son contenu
		JMenu mnuFile = new JMenu( "Fichier" );
		mnuFile.setMnemonic(KeyEvent.VK_F);

		JMenuItem mnuOpenFile = new JMenuItem( "Ouvrir..." );
		mnuOpenFile.setIcon( new ImageIcon( ImageUtils.openImg("/open.png", true) ) );
		mnuOpenFile.setMnemonic( 'O' );
		mnuOpenFile.addActionListener( this::mnuOpenFileListener );
		mnuOpenFile.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK) );
		mnuFile.add(mnuOpenFile);

		JMenuItem mnuSaveFile = new JMenuItem( "Enregistrer" );
		mnuSaveFile.setIcon( new ImageIcon( ImageUtils.openImg("/save.png", true) ) );
		mnuSaveFile.setMnemonic(KeyEvent.VK_S);
		mnuSaveFile.addActionListener(this::save);
		mnuSaveFile.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK ) );
		mnuFile.add(mnuSaveFile);

		JMenuItem mnuSaveFileAs = new JMenuItem( "Enregistrer sous..." );
		mnuSaveFileAs.setIcon( new ImageIcon( ImageUtils.openImg("/save_as.png", true) ) );
		mnuSaveFileAs.setMnemonic(KeyEvent.VK_A);
		mnuSaveFileAs.addActionListener(this::saveAs);
		mnuSaveFile.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK) );
		mnuFile.add(mnuSaveFileAs);

		mnuFile.addSeparator();

		JMenuItem mnuExit = new JMenuItem( "Quitter" );
		mnuExit.setIcon( new ImageIcon( ImageUtils.openImg("/exit.png", true) ) );
		mnuExit.setMnemonic(KeyEvent.VK_X);
		mnuExit.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_DOWN_MASK) );
		mnuFile.add(mnuExit);
		
		menuBar.add(mnuFile);
		
		// Définition du menu déroulant "Edition d'image" et de son contenu
		JMenu mnuEdit = new JMenu( "Edition" );
		mnuEdit.setMnemonic(KeyEvent.VK_I);
		
		JMenuItem mnuRotG = new JMenuItem( "Rotation à gauche" );
		mnuRotG.setIcon( new ImageIcon( ImageUtils.openImg("/undo.png", true) ) );
		mnuRotG.setMnemonic(KeyEvent.VK_G);
		mnuRotG.addActionListener( this::mnuRotGListener );
		mnuRotG.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK) );
		mnuEdit.add(mnuRotG);

		JMenuItem mnuRotD = new JMenuItem( "Rotation à droite" );
		mnuRotD.setIcon( new ImageIcon( ImageUtils.openImg("/redo.png", true) ) );
		mnuRotD.setMnemonic(KeyEvent.VK_D);
		mnuRotD.addActionListener( this::mnuRotDListener );
		mnuRotD.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK) );
		mnuEdit.add(mnuRotD);
		
		mnuEdit.addSeparator();

		JMenuItem mnuMirGD = new JMenuItem( "Miroir gauche droite" );
		mnuMirGD.setIcon( new ImageIcon( ImageUtils.openImg("/miroirGD.png", true) ) );
		mnuMirGD.setMnemonic(KeyEvent.VK_L);
		mnuMirGD.addActionListener( this::mnuMirGDListener );
		mnuMirGD.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK) );
		mnuEdit.add(mnuMirGD);

		JMenuItem mnuMirHB = new JMenuItem( "Miroir haut bas" );
		mnuMirHB.setIcon( new ImageIcon( ImageUtils.openImg("/miroirHB.png", true) ) );
		mnuMirHB.setMnemonic(KeyEvent.VK_P);
		mnuMirHB.addActionListener( this::mnuMirHBListener );
		mnuMirHB.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK) );
		mnuEdit.add(mnuMirHB);
		
		mnuEdit.addSeparator();

        JMenuItem mnuLumineux = new JMenuItem( "Rendre plus lumineux" );
		mnuLumineux.setIcon( new ImageIcon( ImageUtils.openImg("/luminosite.png", true) ) );
		mnuLumineux.setMnemonic(KeyEvent.VK_U);
		mnuLumineux.addActionListener( this::mnuLumineuxListener );
		mnuLumineux.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK) );
		mnuEdit.add(mnuLumineux);

		JMenuItem mnuSombre = new JMenuItem( "Rendre plus Sombre" );
		mnuSombre.setIcon( new ImageIcon( ImageUtils.openImg("/Assombrir.png", true) ) );
		mnuSombre.setMnemonic(KeyEvent.VK_Y);
		mnuSombre.addActionListener( this::mnuSombreListener );
		mnuSombre.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK) );
		mnuEdit.add(mnuSombre);
		
		mnuEdit.addSeparator();

        JMenuItem mnuNoirBlanc = new JMenuItem( "Noir et Blanc" );
		mnuNoirBlanc.setIcon( new ImageIcon( ImageUtils.openImg("/noirblanc.png", true) ) );
		mnuNoirBlanc.setMnemonic(KeyEvent.VK_N);
		mnuNoirBlanc.addActionListener( this::mnuNoirBlancListener );
		mnuNoirBlanc.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK) );
		mnuEdit.add(mnuNoirBlanc);
		
		mnuEdit.addSeparator();
		
		JMenuItem mnuCopy = new JMenuItem( "Copier" );
		mnuCopy.setIcon( new ImageIcon( ImageUtils.openImg("/copy.png", true) ) );
		mnuCopy.setMnemonic(KeyEvent.VK_C);
		mnuCopy.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK) );
		mnuEdit.add(mnuCopy);
		
		JMenuItem mnuCut = new JMenuItem( "Couper" );
		mnuCut.setIcon( new ImageIcon( ImageUtils.openImg("/cut.png", true) ) );
		mnuCut.setMnemonic(KeyEvent.VK_X);
		mnuCut.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK) );
		mnuEdit.add(mnuCut);
		
		JMenuItem mnuPaste = new JMenuItem( "Coller" );
		mnuPaste.setIcon( new ImageIcon( ImageUtils.openImg("/paste.png", true) ) );
		mnuPaste.setMnemonic(KeyEvent.VK_V);
		mnuPaste.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK) );
		mnuEdit.add(mnuPaste);

		menuBar.add(mnuEdit);

		
		// Définition du menu déroulant "Edition de texte" et de son contenu
		JMenu mnuTexte = new JMenu( "Texte" );
		mnuTexte.setMnemonic(KeyEvent.VK_T);
		
		JMenuItem mnuAjTe = new JMenuItem( "Ajouter du texte" );
		mnuAjTe.setIcon( new ImageIcon( ImageUtils.openImg("/ajoutZoneTexte.png", true) ) );
		mnuAjTe.setMnemonic(KeyEvent.VK_A);
		mnuAjTe.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK) );
		mnuTexte.add(mnuAjTe);

		JMenuItem mnuTailTe = new JMenuItem( "Taille du texte" );
		mnuTailTe.setIcon( new ImageIcon( ImageUtils.openImg("/redo.png", true) ) );
		mnuTailTe.setMnemonic(KeyEvent.VK_T);
		mnuTailTe.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK) );
		mnuTexte.add(mnuTailTe);

		JMenuItem mnuCoulTe = new JMenuItem( "Couleur du texte" );
		mnuCoulTe.setIcon( new ImageIcon( ImageUtils.openImg("/redo.png", true) ) );
		mnuCoulTe.setMnemonic(KeyEvent.VK_C);
		mnuCoulTe.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK) );
		mnuTexte.add(mnuCoulTe);

		menuBar.add(mnuTexte);


		// Définition du menu déroulant "Help" et de son contenu
		JMenu mnuHelp = new JMenu( "Help" );
		mnuHelp.setMnemonic(KeyEvent.VK_H);
		
		menuBar.add( mnuHelp );
		
		return menuBar;
	}

	public void mnuOpenFileListener(ActionEvent event) {
		if (this.isSaved == false && this.isModified()) {
			int status = JOptionPane.showInternalConfirmDialog(this.getContentPane(), "Votre travail n'est pas sauvegardé, voulez-vous vraiment en disposer ?");
			if (status != JOptionPane.OK_OPTION)
				return;
		}
		this.openFileChooser("Sélectionnez une image", (fileChooser, result) -> {
			if (result == JFileChooser.APPROVE_OPTION) {
				try {
					this.fichierOuvert = fileChooser.getSelectedFile();
					
					// Mettre à jour l'image dans PanelImage
					this.panelImage.loadImage(ImageIO.read(this.fichierOuvert));
					this.panelImage.transform.reset();
					this.setTitle(this.titre);
					this.panelImage.updateUI();
					
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(this, "Erreur lors du chargement de l'image.");
				}
			}
		});
	}
	
	public void openFileChooser(String titre, IFileChooser action) {
		// Créer un JFileChooser pour sélectionner un fichier
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle(titre);
		
		// Filtrer pour n'autoriser que les fichiers image
		fileChooser.setFileFilter(new FileNameExtensionFilter(
			"Fichiers Image (JPG, PNG, GIF)", "jpg", "jpeg", "png", "gif"
		));
		
		// Afficher la boîte de dialogue et vérifier si l'utilisateur a sélectionné un fichier
		int result = fileChooser.showOpenDialog(this);
		action.onResult(fileChooser, result);
	}
	
	private static interface IFileChooser {
		public void onResult(JFileChooser fileChooser, int result);
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

	@Override
	public void keyTyped(KeyEvent e) {}
	
	private boolean isCtrl = false;
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_CONTROL -> this.isCtrl = true;
			case KeyEvent.VK_Z -> {
				if (this.isCtrl) {
					this.panelImage.transform.undo();
				}
			}
			case KeyEvent.VK_Y -> {
				if (this.isCtrl) {
					this.panelImage.transform.redo();
				}
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_CONTROL -> this.isCtrl = false;
		}
	}
	
	public boolean isModified() {
		return this.panelImage.getImage() != null && this.panelImage.transform.hasOperations();
	}
	
	public void setModified() {
		this.isSaved = false;
		this.setTitle(this.titre + "*");
	}
	
	public void save(ActionEvent e) {
		if (this.fichierOuvert == null)
			return;
		BufferedImage image = this.panelImage.transform.applyTransforms(this.panelImage.getImage());
		BufferedImage out = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		Graphics2D g2 = out.createGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
		try {
			ImageIO.write(out, this.getFileExtension(this.fichierOuvert), this.fichierOuvert);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		this.isSaved = true;
		this.setTitle(this.titre);
	}
	
	public void saveAs(ActionEvent e) {
		if (this.fichierOuvert == null)
			return;
		this.openFileChooser("Enregistrer sous", (fileChooser, result) -> {
			if (result == JFileChooser.APPROVE_OPTION) {
				this.fichierOuvert = fileChooser.getSelectedFile();
			}
		});
		this.save(e);
	}
	
	private String getFileExtension(File file) {
		if (file == null)
			return null;
		if (file.isDirectory())
			return null;
		String name = this.fichierOuvert.getName();
		return name.substring(name.indexOf(".") + 1);
	}
	
	@Override
	public void windowOpened(WindowEvent e) {}
	
	@Override
	public void windowClosing(WindowEvent e) {
		if (this.isSaved == false && this.isModified()) {
			int status = JOptionPane.showInternalConfirmDialog(this.getContentPane(), "Votre travail n'est pas sauvegardé, voulez-vous vraiment quitter ?");
			if (status == JOptionPane.CANCEL_OPTION || status == JOptionPane.NO_OPTION)
				return;
		}
		this.dispose();
	}
	
	@Override
	public void windowClosed(WindowEvent e) {}
	
	@Override
	public void windowIconified(WindowEvent e) {}
	
	@Override
	public void windowDeiconified(WindowEvent e) {}
	
	@Override
	public void windowActivated(WindowEvent e) {}
	
	@Override
	public void windowDeactivated(WindowEvent e) {}
	
}