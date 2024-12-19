package org.multimedia.vue;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.multimedia.composants.ToolBarBouton;
import org.multimedia.main.Controleur;
import org.multimedia.util.ImageUtils;

public class BarreOutils extends JToolBar implements ActionListener
{
	@Serial
	protected static final long serialVersionUID = 6326511901738014336L;

	protected Controleur ctrl;
	protected final JPanel  panel;

	protected JButton       btnSauvegarder;
	protected JButton       btnOuvrirImage;
	protected JButton       btnPipette;
	protected JButton       btnPotPeinture;
	protected JButton       btnCouleur;
	protected JButton       btnAjouterTexte;
	protected JButton       btnCreerRectangle;
	protected JButton       btnCreerRond;
	protected JButton       btnUndo;
	protected JButton       btnRedo;
	protected JButton       btnCurseur;
	protected JButton       btnAutreFrame;
	protected JButton       btnFondTransp;
	protected JButton       btnColerForme;
	protected JButton       btnPremPlan;
	protected JButton       btnDeuxPlan;
	protected JButton       btnAvDerPlan;
	protected JButton       btnArrPlan;

	protected JComboBox<String> comboTailleTexte;
	public final JPanel extraToolbar;
	protected Color couleurSelectionnee = Color.BLACK;
	protected JTextField textFieldTexte; 

	public BarreOutils(Controleur ctrl) 
	{
		this.ctrl = ctrl;
		this.setBackground(new Color(200, 200, 200));
		this.setFocusable(false);
		this.setLayout(new BorderLayout());
		
		/*-------------------------------*/
		/* Création des composants       */
		/*-------------------------------*/


		this.btnOuvrirImage = new ToolBarBouton(new ImageIcon(ImageUtils.openImg("/open.png", true) ));
		this.btnOuvrirImage.setToolTipText("changer d'image");
		this.btnOuvrirImage.setActionCommand("changeImage");

		this.btnSauvegarder = new ToolBarBouton(new ImageIcon(ImageUtils.openImg("/save.png", true) ));
		this.btnSauvegarder.setToolTipText("Sauvegarder");
		this.btnSauvegarder.setActionCommand("Sauvegarder");

		this.btnUndo = new ToolBarBouton(new ImageIcon(ImageUtils.openImg("/undo.png", true)));
		this.btnUndo.setToolTipText("Défaire");
		this.btnUndo.setActionCommand("Undo");
		
		this.btnRedo = new ToolBarBouton(new ImageIcon(ImageUtils.openImg("/redo.png", true)));
		this.btnRedo.setToolTipText("Refaire");
		this.btnRedo.setActionCommand("Redo");

		this.btnCurseur = new ToolBarBouton(new ImageIcon(ImageUtils.openImg("/curseur.png", true)));
		this.btnCurseur.setToolTipText("Souris normal");
		this.btnCurseur.setActionCommand("SourisNormal");

		this.btnCouleur = new ToolBarBouton();
		this.btnCouleur.setToolTipText("Couleur Sélectionnée");
		this.btnCouleur.setActionCommand("Couleur");
		this.btnCouleur.setOpaque(true); // Assurez que le bouton est opaque
		this.btnCouleur.setContentAreaFilled(true); // Remplit tout le bouton
		this.btnCouleur.setBorderPainted(false); // Désactive les bordures, si souhaité
		this.btnCouleur.setBackground(this.couleurSelectionnee);

		this.btnPipette = new ToolBarBouton(new ImageIcon(ImageUtils.openImg("/pipette.png", true) ));
		this.btnPipette.setToolTipText("Pipette");
		this.btnPipette.setActionCommand("Pipette");

		this.btnPotPeinture = new ToolBarBouton(new ImageIcon(ImageUtils.openImg("/potPeinture.png", true) ));
		this.btnPotPeinture.setToolTipText("Pot de peinture");
		this.btnPotPeinture.setActionCommand("PotDePeinture");

		this.btnFondTransp = new ToolBarBouton(new ImageIcon(ImageUtils.openImg("/fondTransp.png", true) ));
		this.btnFondTransp.setToolTipText("Fond transparent");
		this.btnFondTransp.setActionCommand("FondTransparent");

		this.btnCreerRectangle = new ToolBarBouton(new ImageIcon(ImageUtils.openImg("/carrePointi.png", true) ));
		this.btnCreerRectangle.setToolTipText("Copier un rectangle");
		this.btnCreerRectangle.setActionCommand("CopierRectangle");

		this.btnCreerRond = new ToolBarBouton(new ImageIcon(ImageUtils.openImg("/rondPointi.png", true) ));
		this.btnCreerRond.setToolTipText("Copier un rond");
		this.btnCreerRond.setActionCommand("CopierRond");

		this.btnAjouterTexte = new ToolBarBouton(new ImageIcon(ImageUtils.openImg("/ajoutZoneTexte.png", true) ));
		this.btnAjouterTexte.setToolTipText("Ajouter du texte");
		this.btnAjouterTexte.setActionCommand("AjouterDuTexte");


		String[] taillesTexte = {"8", "9", "10", "11", "12", "14", "16", "18", "20", "24", "30", "36", "48", "60", "70", "96"};
		this.comboTailleTexte = new JComboBox<>(taillesTexte);
		this.comboTailleTexte.setPreferredSize(new Dimension(80, 40));
		this.comboTailleTexte.setMaximumSize(new Dimension(80, 40));
		this.comboTailleTexte.setMinimumSize(new Dimension(80, 40));
		this.comboTailleTexte.setToolTipText("Taille du texte");
		this.comboTailleTexte.setSelectedItem("12");

		this.textFieldTexte = new JTextField();
		this.textFieldTexte.setPreferredSize(new Dimension(250, 40));
		this.textFieldTexte.setMaximumSize(new Dimension(250, 40));
		this.textFieldTexte.setMinimumSize(new Dimension(250, 40));
		this.textFieldTexte.setToolTipText("Entrez du texte");

		this.textFieldTexte.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate( DocumentEvent e ) { onTextChanged(); }

			@Override
			public void removeUpdate( DocumentEvent e ) { onTextChanged(); }

			@Override
			public void changedUpdate( DocumentEvent e ) { onTextChanged(); }

			// Méthode appelée à chaque changement de texte
			protected void onTextChanged() {
				String texte = textFieldTexte.getText();
				ctrl.getFramePrinc().setTextTexte( texte ); // Appeler la méthode dans FramePrinc
			}
		});

		this.comboTailleTexte.addActionListener(e -> {
			String tailleSelectionnee = ( String ) comboTailleTexte.getSelectedItem();
			if ( tailleSelectionnee != null ) {
				int tailleTexte = Integer.parseInt( tailleSelectionnee );
				this.ctrl.getFramePrinc().setTextSize( tailleTexte );
			}
		});

		this.btnAutreFrame = new ToolBarBouton(new ImageIcon(ImageUtils.openImg("/importeAutreIm.png", true) ));
		this.btnAutreFrame.setToolTipText("charger une autre image");
		this.btnAutreFrame.setActionCommand("changeAutreImage");

		this.btnColerForme = new ToolBarBouton(new ImageIcon(ImageUtils.openImg("/paste.png", true) ));
		this.btnColerForme.setToolTipText("Coller les formes");
		this.btnColerForme.setActionCommand("collerForme");

		this.btnPremPlan = new ToolBarBouton(new ImageIcon(ImageUtils.openImg("/1erPlan.png", true) ));
		this.btnPremPlan.setToolTipText("Passer au 1er plan");
		this.btnPremPlan.setActionCommand("1erPlan");

		this.btnDeuxPlan = new ToolBarBouton(new ImageIcon(ImageUtils.openImg("/passe1niv.png", true) ));
		this.btnDeuxPlan.setToolTipText("Faire avancer d'un niveau");
		this.btnDeuxPlan.setActionCommand("passe1niv");

		this.btnAvDerPlan = new ToolBarBouton(new ImageIcon(ImageUtils.openImg("/reculer1niv.png", true) ));
		this.btnAvDerPlan.setToolTipText("Faire reculer d'un niveau");
		this.btnAvDerPlan.setActionCommand("reculer1niv");

		this.btnArrPlan = new ToolBarBouton(new ImageIcon(ImageUtils.openImg("/dernierPlan.png", true) ));
		this.btnArrPlan.setToolTipText("Passer a l'arrière plan");
		this.btnArrPlan.setActionCommand("dernierPlan");

		
		// Même taille des bouton pour tous 
		uniformiserBouton( this.btnOuvrirImage );
		uniformiserBouton( this.btnSauvegarder );
		uniformiserBouton( this.btnUndo );
		uniformiserBouton( this.btnRedo );
		uniformiserBouton( this.btnCurseur );
		uniformiserBouton( this.btnCouleur );
		uniformiserBouton( this.btnPipette );
		uniformiserBouton( this.btnPotPeinture );
		uniformiserBouton( this.btnFondTransp );
		uniformiserBouton( this.btnCreerRectangle );
		uniformiserBouton( this.btnCreerRond );
		uniformiserBouton( this.btnAjouterTexte );
		uniformiserBouton( this.btnAutreFrame );
		uniformiserBouton( this.btnColerForme );
		uniformiserBouton( this.btnPremPlan );
		uniformiserBouton( this.btnDeuxPlan );
		uniformiserBouton( this.btnAvDerPlan );
		uniformiserBouton( this.btnArrPlan );


		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/
		
		this.panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		panel.add( this.btnOuvrirImage );
		panel.add( this.btnSauvegarder );
		panel.add( this.btnUndo );
		panel.add( this.btnRedo );
		panel.add( this.btnCurseur );
		panel.add( this.btnCouleur );
		panel.add( this.btnPipette );
		panel.add( this.btnPotPeinture );
		panel.add( this.btnFondTransp );
		panel.add( this.btnAjouterTexte );
		panel.add( this.comboTailleTexte );
		panel.add( this.textFieldTexte );
		panel.add( this.btnAutreFrame );
		panel.add( this.btnCreerRectangle );
		panel.add( this.btnCreerRond );
		panel.add( this.btnColerForme );
		panel.add( this.btnPremPlan );
		panel.add( this.btnDeuxPlan );
		panel.add( this.btnAvDerPlan );
		panel.add( this.btnArrPlan );
		
		this.add(panel, BorderLayout.CENTER);
		
		this.extraToolbar = new JPanel();
		this.add(this.extraToolbar, BorderLayout.SOUTH);
		
		/*-------------------------------*/
		/* Activation des composants     */
		/*-------------------------------*/
		this.btnOuvrirImage    .addActionListener(this);
		this.btnSauvegarder    .addActionListener(this);
		this.btnUndo           .addActionListener(this);
		this.btnRedo           .addActionListener(this);
		this.btnCurseur        .addActionListener(this);
		this.btnCouleur        .addActionListener(this);
		this.btnPipette        .addActionListener(this);
		this.btnPotPeinture    .addActionListener(this);
		this.btnFondTransp     .addActionListener(this);
		this.btnAjouterTexte   .addActionListener(this);
		this.btnCreerRectangle .addActionListener(this);
		this.btnCreerRond      .addActionListener(this);
		this.btnAutreFrame     .addActionListener(this);
		this.btnColerForme     .addActionListener(this);
		this.btnPremPlan       .addActionListener(this);
		this.btnDeuxPlan       .addActionListener(this);
		this.btnAvDerPlan      .addActionListener(this);
		this.btnArrPlan        .addActionListener(this);
	}


	public void setCouleurSelectionnee( Color couleur ) {
		this.ctrl.getFramePrinc().setSelectedColor( couleur );
		this.couleurSelectionnee = couleur;
		this.btnCouleur.setBackground( couleur );
	}

	public void updateComboBoxSize(int newSize) {
		String newSizeStr = String.valueOf(newSize);
	
		// Vérifiez si la taille est dans le JComboBox et mettez-la à jour
		for (int i = 0; i < this.comboTailleTexte.getItemCount(); i++) {
			if (this.comboTailleTexte.getItemAt(i).equals(newSizeStr)) {
				this.comboTailleTexte.setSelectedIndex(i);
				break;
			}
		}
	}

	@Override
	public void actionPerformed ( ActionEvent e)
	{
		final PanelImage panelIm = this.ctrl.getFramePrinc().getPanelImage();
		if (panelIm.getImage() == null && !e.getActionCommand().equals("Couleur") && !e.getActionCommand().equals("changeImage")) { return; }
		for (Component c : this.extraToolbar.getComponents()) { this.extraToolbar.remove(c); }
			
		switch (e.getActionCommand()) {
			case "changeImage"   -> this.ctrl.getFramePrinc().mnuOpenFileListener(e);
			case "Sauvegarder"   -> this.ctrl.getFramePrinc().save(e);
			case "Pipette"       -> panelIm.enablePipetteMode( ! panelIm.isPipetteMode() );
			case "PotDePeinture" -> {
				panelIm.enablePotPeintureMode(!panelIm.isPotPeintureMode());
				if (panelIm.isPotPeintureMode()) {
					JSlider contrastSlider = new JSlider(JSlider.HORIZONTAL, 0, 20, 10);
					contrastSlider.setMajorTickSpacing(5);
					contrastSlider.setPaintTicks(true);
					contrastSlider.setPaintLabels(true);
					contrastSlider.addChangeListener(ev -> this.ctrl.getFramePrinc().setTolerance(contrastSlider.getValue()));
					
					this.extraToolbar.add(new JLabel("Tolérance :"));
					this.extraToolbar.add(contrastSlider);
				}
			}
			case "FondTransparent" -> panelIm.paintTransp();
			case "Couleur"         -> {
				// Ouvrir le sélecteur de couleur
				Color nouvelleCouleur = JColorChooser.showDialog(this, "Choisir une couleur", couleurSelectionnee);

				if (nouvelleCouleur != null) {
					this.ctrl.getFramePrinc().setSelectedColor(nouvelleCouleur);
					couleurSelectionnee = nouvelleCouleur;
					this.btnCouleur.setBackground(couleurSelectionnee); // Mettre à jour la couleur du bouton
				}
			}
			case "CopierRectangle" -> panelIm.enableSelectionRect( ! panelIm.isSelectionRectMode() );
			case "CopierRond"      -> panelIm.enableSelectionRond( ! panelIm.isSelectionRondMode() );
			case "AjouterDuTexte"  -> panelIm.enableStylo( ! panelIm.isStyloMode() );
			case "Undo"            -> {
				panelIm.transform.undo();
				panelIm.updateUI();
			}
			case "Redo"            -> {
				panelIm.transform.redo();
				panelIm.updateUI();
			}
			case "SourisNormal"     -> panelIm.curseurMode( );
			case "changeAutreImage" -> panelIm.openSourcePanel();
			case "collerForme"      -> panelIm.saveImageWithOverlap();
			case "1erPlan"          -> panelIm.premPlan();
			case "passe1niv"        -> panelIm.avanverUnPlan();
			case "reculer1niv"      -> panelIm.reculerUnPlan();
			case "dernierPlan"      -> panelIm.dernPlan();
		}
		this.updateUI();
	}

	protected void uniformiserBouton(JButton bouton) {
		bouton.setPreferredSize ( new Dimension( 40, 40 ) );
		bouton.setMaximumSize   ( new Dimension( 40, 40 ) );
		bouton.setMinimumSize   ( new Dimension( 40, 40 ) );
	}
}