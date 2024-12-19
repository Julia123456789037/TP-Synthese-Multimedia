package org.multimedia.vue;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
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
	private static final long serialVersionUID = 6326511901738014336L;

	private Controleur ctrl;
	private JButton		btnSauvegarder;
	private JButton		btnOuvrirImage;
	private JButton		btnPipette;
	private JButton		btnPotPeinture;
	private JButton		btnCouleur;
	private JButton 	btnAjouterTexte;
	private JButton		btnCreerRectangle;
	private JButton		btnCreerRond;
	private JButton 	btnUndo;
	private JButton 	btnRedo;
	private JButton 	btnCurseur;
	private JButton		btnAutreFrame;
	private JButton		btnFondTransp;

	private JComboBox<String> comboTailleTexte;
	
	private Color couleurSelectionnee = Color.BLACK;
	private JTextField textFieldTexte; 

	public BarreOutils(Controleur ctrl) 
	{
		this.ctrl = ctrl;
		this.setBackground(new Color(200, 200, 200));
		this.setFocusable(false);
		
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
		this.btnCouleur.setBackground(this.couleurSelectionnee); 
		this.btnCouleur.setOpaque(true);
		this.btnCouleur.setBorderPainted(true);

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
            private void onTextChanged() {
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

		this.btnAutreFrame = new ToolBarBouton(new ImageIcon(ImageUtils.openImg("/open.png", true) ));
		this.btnAutreFrame.setToolTipText("charger une autre image");
		this.btnAutreFrame.setActionCommand("changeAutreImage");

		
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


		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/
		this.add( this.btnOuvrirImage );
		this.add( this.btnSauvegarder );
		this.add( this.btnUndo );
		this.add( this.btnRedo );
		this.add( this.btnCurseur );
		this.add( this.btnCouleur );
		this.add( this.btnPipette );
		this.add( this.btnPotPeinture );
		this.add( this.btnFondTransp );
		this.add( this.btnCreerRectangle );
		this.add( this.btnCreerRond );
		this.add( this.btnAjouterTexte );
		this.add( this.comboTailleTexte );
		this.add( this.textFieldTexte );
		this.add( this.btnAutreFrame );
		
		/*-------------------------------*/
		/* Activation des composants     */
		/*-------------------------------*/
		this.btnOuvrirImage    .addActionListener(this);
		this.btnSauvegarder     .addActionListener(this);
		this.btnUndo            .addActionListener(this);
		this.btnRedo            .addActionListener(this);
		this.btnCurseur         .addActionListener(this);
		this.btnCouleur         .addActionListener(this);
		this.btnPipette         .addActionListener(this);
		this.btnPotPeinture     .addActionListener(this);
		this.btnFondTransp      .addActionListener(this);
		this.btnAjouterTexte    .addActionListener(this);
		this.btnCreerRectangle  .addActionListener(this);
		this.btnCreerRond       .addActionListener(this);
		this.btnAutreFrame      .addActionListener(this);
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

	public void actionPerformed ( ActionEvent e)
	{
		final PanelImage panelIm = this.ctrl.getFramePrinc().getPanelImage();
		if (panelIm.getImage() == null)
			return;
		switch (e.getActionCommand()) {
			case "changeImage" -> { this.ctrl.getFramePrinc().mnuOpenFileListener(e); }
			case "Sauvegarder"   -> this.ctrl.getFramePrinc().save(e);
			case "Pipette"       -> panelIm.enablePipetteMode( ! panelIm.isPipetteMode() );
			case "PotDePeinture" -> panelIm.enablePotPeintureMode( !panelIm.isPotPeintureMode() );
			case "FondTransparent" 	-> panelIm.paintTransp( );
			case "Couleur"       -> {
				// Ouvrir le sélecteur de couleur
				Color nouvelleCouleur = JColorChooser.showDialog(this, "Choisir une couleur", couleurSelectionnee);

				if (nouvelleCouleur != null) {
					this.ctrl.getFramePrinc().setSelectedColor(nouvelleCouleur);
					couleurSelectionnee = nouvelleCouleur;
					this.btnCouleur.setBackground(couleurSelectionnee); // Mettre à jour la couleur du bouton
				}
			}
			case "CopierRectangle" 	-> { panelIm.enableSelectionRect( ! panelIm.isSelectionRectMode() ); }
			case "CopierRond" 		-> { panelIm.enableSelectionRond( ! panelIm.isSelectionRondMode() ); }
			case "AjouterDuTexte" 	-> { panelIm.enableStylo( ! panelIm.isStyloMode() ); }
			case "Undo" -> {
				panelIm.transform.undo();
				panelIm.updateUI();
			}
			case "Redo" -> {
				panelIm.transform.redo();
				panelIm.updateUI();
			}
			case "SourisNormal" 	-> { panelIm.curseurMode( ); }
			case "changeAutreImage" 	-> { /* TODO seb */ }
		}
	}

	private void uniformiserBouton(JButton bouton) {
		bouton.setPreferredSize ( new Dimension( 40, 40 ) );
		bouton.setMaximumSize   ( new Dimension( 40, 40 ) );
		bouton.setMinimumSize   ( new Dimension( 40, 40 ) );
	}
}