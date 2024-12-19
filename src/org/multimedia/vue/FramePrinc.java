package org.multimedia.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serial;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.multimedia.main.Controleur;
import org.multimedia.util.ImageUtils;


public class FramePrinc extends JFrame implements WindowListener, ActionListener
{
	@Serial
	private static final long serialVersionUID = 5106104636891939306L;

	Controleur ctrl;

	BarreOutils	barreOutils;
	PanelImage	panelImage;
	private JMenu mnuTailTe;
	private Color selectedColor = Color.BLACK;
	private boolean isSaved;
	public final String titre;
	private File fichierOuvert;
	private int textSize = 12;
	private String textTexte = "";
	
	public FramePrinc(Controleur ctrl)
	{
		this.ctrl = ctrl;
		this.isSaved = false;
		this.titre = "Swing Painter";

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
		
		this.registerKeyboardEvent(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK), e -> {
			this.panelImage.transform.undo();
			this.panelImage.updateUI();
		});
		this.registerKeyboardEvent(KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK), e -> {
			this.panelImage.transform.redo();
			this.panelImage.updateUI();
		});
		this.registerKeyboardEvent(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), e -> this.save(e));
		this.registerKeyboardEvent(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK), e -> this.saveAs(e));
		
		this.addWindowListener(this);
		
		this.setVisible ( true );
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	
	private void registerKeyboardEvent(KeyStroke key, ActionListener a) {
		this.getRootPane().registerKeyboardAction(a, key, JComponent.WHEN_IN_FOCUSED_WINDOW);
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
		mnuSaveFile.setMnemonic(KeyEvent.VK_E);
		mnuSaveFile.addActionListener(this::save);
		mnuSaveFile.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK ) );
		mnuFile.add(mnuSaveFile);
		
		JMenuItem mnuSaveFileAs = new JMenuItem( "Enregistrer sous..." );
		mnuSaveFileAs.setIcon( new ImageIcon( ImageUtils.openImg("/save_as.png", true) ) );
		mnuSaveFileAs.setMnemonic(KeyEvent.VK_E);
		mnuSaveFileAs.addActionListener(this::saveAs);
		mnuSaveFileAs.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK) );
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
		mnuEdit.setMnemonic(KeyEvent.VK_E);
		
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
		
		
		// Définition du menu "Image"
		JMenu mnuImage = new JMenu("Image");
		mnuImage.setMnemonic(KeyEvent.VK_I);
		
		JMenuItem mnuRotD = new JMenuItem( "Faire pivoter de 90° à droite" );
		mnuRotD.setIcon( new ImageIcon( ImageUtils.openImg("/redo.png", true) ) );
		mnuRotD.setMnemonic(KeyEvent.VK_D);
		mnuRotD.addActionListener(this);
		mnuRotD.setActionCommand("RotationD");
		mnuRotD.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK) );
		mnuImage.add(mnuRotD);
		
		JMenuItem mnuRotG = new JMenuItem( "Faire pivoter de 90° à gauche" );
		mnuRotG.setIcon( new ImageIcon( ImageUtils.openImg("/undo.png", true) ) );
		mnuRotG.setMnemonic(KeyEvent.VK_G);
		mnuRotG.addActionListener(this);
		mnuRotG.setActionCommand("RotationG");
		mnuRotG.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK) );
		mnuImage.add(mnuRotG);
		
		JMenuItem mnuRotL = new JMenuItem("Rotation libre");
		mnuRotL.setIcon(new ImageIcon(ImageUtils.openImg("/undo.png", true)));
		mnuRotL.setMnemonic(KeyEvent.VK_L);
		mnuRotL.addActionListener(this);
		mnuRotL.setActionCommand("RotationL");
		mnuRotL.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK));
		mnuImage.add(mnuRotL);
		
		mnuImage.addSeparator();

		JMenuItem mnuMirGD = new JMenuItem( "Retourner horizontalement" );
		mnuMirGD.setIcon( new ImageIcon( ImageUtils.openImg("/miroirGD.png", true) ) );
		mnuMirGD.setMnemonic(KeyEvent.VK_L);
		mnuMirGD.addActionListener(this);
		mnuMirGD.setActionCommand("MirroirH");
		mnuMirGD.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK) );
		mnuImage.add(mnuMirGD);

		JMenuItem mnuMirHB = new JMenuItem( "Retourner verticalement" );
		mnuMirHB.setIcon( new ImageIcon( ImageUtils.openImg("/miroirHB.png", true) ) );
		mnuMirHB.setMnemonic(KeyEvent.VK_P);
		mnuMirHB.addActionListener(this);
		mnuMirHB.setActionCommand("MirroirV");
		mnuMirHB.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK) );
		mnuImage.add(mnuMirHB);
		
		menuBar.add(mnuImage);
		
		// Définition du menu déroulant "Edition de texte" et de son contenu
		JMenu mnuTexte = new JMenu( "Texte" );
		mnuTexte.setMnemonic(KeyEvent.VK_T);
		
		JMenuItem mnuAjTe = new JMenuItem( "Ajouter du texte" );
		mnuAjTe.setIcon( new ImageIcon( ImageUtils.openImg("/ajoutZoneTexte.png", true) ) );
		mnuAjTe.setMnemonic(KeyEvent.VK_A);
		mnuAjTe.addActionListener( this::mnuAjTeListener );
		mnuAjTe.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK) );
		mnuTexte.add(mnuAjTe);

		this.mnuTailTe = new JMenu("Taille du texte");
		this.mnuTailTe.setIcon( new ImageIcon( ImageUtils.openImg("/tailleTexte.png", true) ) );
		this.mnuTailTe.setMnemonic('T');
		
		String[] tailles = { "8", "9", "10", "11", "12", "14", "16", "18", "20", "24", "30", "36", "48", "60", "70", "96" };

		for (String taille : tailles) {
			JMenuItem menuItem = new JMenuItem(taille);
			menuItem.setOpaque(true);
			menuItem.addActionListener(e -> { setTextSize(Integer.parseInt(taille)); });
			this.mnuTailTe.add(menuItem);
			if ( Integer.parseInt(taille) == this.textSize ) { menuItem.setBackground(Color.GRAY); }
		}

		mnuTexte.add( this.mnuTailTe );
		menuBar.add(mnuTexte);
		
		// Définition de menu "Ajustements"
		
		JMenu menuAjust = new JMenu("Ajustements");
		mnuTexte.setMnemonic(KeyEvent.VK_T);
		
		JMenuItem mnuAjuL = new JMenuItem("Luminosité / Contraste...");
		mnuAjuL.setIcon(new ImageIcon(ImageUtils.openImg("/ajoutZoneTexte.png", true)));
		mnuAjuL.setMnemonic(KeyEvent.VK_A);
		mnuAjuL.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK));
		mnuAjuL.addActionListener(this);
		mnuAjuL.setActionCommand("AjusterLC");
		menuAjust.add(mnuAjuL);
		
		JMenuItem mnuNoirBlanc = new JMenuItem( "Noir et Blanc" );
		mnuNoirBlanc.setIcon( new ImageIcon( ImageUtils.openImg("/noirblanc.png", true) ) );
		mnuNoirBlanc.setMnemonic(KeyEvent.VK_N);
		mnuNoirBlanc.addActionListener(this);
		mnuNoirBlanc.setActionCommand("NoirEtBlanc");
		mnuNoirBlanc.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK) );
		menuAjust.add(mnuNoirBlanc);
		
		menuAjust.addSeparator();

		JMenuItem mnuLumineux = new JMenuItem( "Rendre plus lumineux" );
		mnuLumineux.setIcon( new ImageIcon( ImageUtils.openImg("/luminosite.png", true) ) );
		mnuLumineux.setMnemonic(KeyEvent.VK_U);
		mnuLumineux.addActionListener(this);
		mnuLumineux.setActionCommand("LuminositéPlus");
		mnuLumineux.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK) );
		menuAjust.add(mnuLumineux);

		JMenuItem mnuSombre = new JMenuItem( "Rendre plus Sombre" );
		mnuSombre.setIcon( new ImageIcon( ImageUtils.openImg("/Assombrir.png", true) ) );
		mnuSombre.setMnemonic(KeyEvent.VK_P);
		mnuSombre.addActionListener(this);
		mnuSombre.setActionCommand("LuminositéMoins");
		mnuSombre.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK) );
		menuAjust.add(mnuSombre);

		menuBar.add(menuAjust);
		
		// Définition du menu déroulant "Help" et de son contenu
		JMenu mnuHelp = new JMenu( "Help" );
		mnuHelp.setMnemonic(KeyEvent.VK_H);
		
		menuBar.add(mnuHelp);
		
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
					this.setTitle(this.titre + " - " + this.fichierOuvert.getName());
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
		action.onResult(fileChooser, fileChooser.showOpenDialog(this));
	}
	
	private static interface IFileChooser {
		public void onResult(JFileChooser fileChooser, int result);
	}

	public void mnuAjTeListener(ActionEvent event) { this.panelImage.enableStylo(! this.panelImage.isStyloMode()); }
	public void setSelectedColor(Color color) { this.selectedColor = color; }
	public void setTextSize(int size) { 
		this.textSize = size; 
		this.getBarreOutils().updateComboBoxSize(size);
		this.updateMenuTextSize(size);
	}
	public void setTextTexte(String texte) { this.textTexte = texte; }

	public void updateMenuTextSize(int newSize) {
		for (int i = 0; i < this.mnuTailTe.getItemCount(); i++) {
			JMenuItem item = this.mnuTailTe.getItem(i);
			item.setBackground(null);
	
			if (item.getText().equals(String.valueOf(newSize))) {
				item.setSelected(true); // Mettre en surbrillance l'élément sélectionné
				item.setBackground(Color.GRAY);
			}
		}
	}

	public void potPeint( int x, int y ) {
		this.panelImage.transform.fillColor(x, y, this.selectedColor);
		this.panelImage.updateUI();
	}

    public void transp() {
		this.panelImage.transform.fillTransp( this.selectedColor );
		this.panelImage.updateUI();
	}

	public void writeText( int x, int y )  {
		this.panelImage.transform.writeText(this.textTexte, x, y, this.textSize, this.selectedColor);
		this.panelImage.updateUI();
	}
	
	public PanelImage getPanelImage() { return this.panelImage; }
	public BarreOutils getBarreOutils() { return this.barreOutils; }
	
	public boolean isModified() {
		return this.panelImage.getImage() != null && this.panelImage.transform.hasOperations();
	}
	
	public void setModified() {
		this.isSaved = false;
		this.setTitle(this.titre + (this.panelImage.getImage() != null ? " - " + this.fichierOuvert.getName() : "") + "*");
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
		this.setTitle(this.titre + (this.panelImage.getImage() != null ? " - " + this.fichierOuvert.getName() : ""));
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.fichierOuvert == null)
			return;
		switch (e.getActionCommand()) {
			case "AjusterLC" -> {
				JSlider brightnessSlider = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
				brightnessSlider.setMajorTickSpacing((100 - -100) / 5);
				brightnessSlider.setPaintTicks(true);
				brightnessSlider.setPaintLabels(true);
				
				JSlider contrastSlider = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
				contrastSlider.setMajorTickSpacing((100 - -100) / 5);
				contrastSlider.setPaintTicks(true);
				contrastSlider.setPaintLabels(true);
	
				int result = JOptionPane.showOptionDialog(null, 
					new Object[] {
						"Luminosité :",
						brightnessSlider,
						"Contraste :",
						contrastSlider
					}, 
					"Luminosité / Contraste", 
					JOptionPane.OK_CANCEL_OPTION, 
					JOptionPane.PLAIN_MESSAGE, 
					null, null, null
				);
	
				if (result == JOptionPane.OK_OPTION) {
					this.panelImage.transform.applyBrightness(brightnessSlider.getValue());
					this.panelImage.transform.applyContrast(contrastSlider.getValue());
				}
			}
			case "LuminositéPlus"  -> this.panelImage.transform.applyBrightness( 20);
			case "LuminositéMoins" -> this.panelImage.transform.applyBrightness(-20);
			case "NoirEtBlanc"     -> this.panelImage.transform.toGreyScale();
			case "MirroirV"        -> this.panelImage.transform.invertV();
			case "MirroirH"        -> this.panelImage.transform.invertH();
			case "RotationG"       -> this.panelImage.transform.addRotation(-90);
			case "RotationD"       -> this.panelImage.transform.addRotation( 90);
			case "RotationL"       -> {
				JSlider slider = new JSlider(JSlider.HORIZONTAL, -360, 360, 0);
				slider.setMajorTickSpacing((360 - -360) / 5);
				slider.setPaintTicks(true);
				slider.setPaintLabels(true);
	
				int result = JOptionPane.showOptionDialog(null, 
					new Object[] {"Rotation :", slider}, 
					"Rotation libre", 
					JOptionPane.OK_CANCEL_OPTION, 
					JOptionPane.PLAIN_MESSAGE, 
					null, null, null
				);
	
				if (result == JOptionPane.OK_OPTION) {
					this.panelImage.transform.addRotation(slider.getValue());
				}
			}
		}
		this.panelImage.updateUI();
	}
	
}