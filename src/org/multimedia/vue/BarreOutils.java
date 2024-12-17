package org.multimedia.vue;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import org.multimedia.composants.ToolBarBouton;
import org.multimedia.main.Controleur;
import org.multimedia.util.ImageUtils;

public class BarreOutils extends JToolBar implements ActionListener
{
	@Serial
	private static final long serialVersionUID = 6326511901738014336L;
	
	Controleur ctrl;
	JButton    btnSauvegarder;
	JButton    btnImporteImage;
	JButton    btnPipette;
	JButton    btnPotPeinture;
	JButton    btnCouleur;

	JButton    btnSelectionRect;
	JButton    btnSelectionRond;
	JButton    btnAutreFrame;
	JButton    btnFondTransp;
	JButton    btnRotationGauche;
	JButton    btnRotationDroite;

	JButton    btnMiroirGaucheDroite;
	JButton    btnMiroirHautBas;
	JButton    btnNoirBlanc;
	JButton    btnZoneTexte;


	JButton btnAjouterTexte;

	private Color couleurSelectionnee = Color.BLACK;

	public BarreOutils(Controleur ctrl ) 
	{
		this.ctrl = ctrl;
		
		/*-------------------------------*/
		/* Création des composants       */
		/*-------------------------------*/

        // Bouton 1 avec une image
        this.btnSauvegarder = new ToolBarBouton(new ImageIcon(ImageUtils.openImg("/save.png", true) ));
        this.btnSauvegarder.setToolTipText("Sauvegarder");
        this.btnSauvegarder.setActionCommand("Sauvegarder");

		this.btnPipette = new ToolBarBouton(new ImageIcon(ImageUtils.openImg("/pipette.png", true) ));
        this.btnPipette.setToolTipText("Pipette");
        this.btnPipette.setActionCommand("Pipette");

		this.btnPotPeinture = new ToolBarBouton(new ImageIcon(ImageUtils.openImg("/potPeinture.png", true) ));
        this.btnPotPeinture.setToolTipText("Pot de peinture");
        this.btnPotPeinture.setActionCommand("PotDePeinture");

		this.btnAjouterTexte = new ToolBarBouton(new ImageIcon(ImageUtils.openImg("/ajoutZoneTexte.png", true) ));
        this.btnAjouterTexte.setToolTipText("Ajouter du texte");
        this.btnAjouterTexte.setActionCommand("AjouterDuTexte");



		this.btnSelectionRect		= new ToolBarBouton ( "Selectionner rectangle"	);
		this.btnSelectionRond		= new ToolBarBouton ( "Selectionner rond"		);
		this.btnAutreFrame			= new ToolBarBouton ( "importer autre image"		);
		this.btnFondTransp			= new ToolBarBouton ( "Fond transparent"			);
		this.btnRotationGauche		= new ToolBarBouton ( "rotation gauche"			);
		this.btnRotationDroite		= new ToolBarBouton ( "rotation droite"			);

		this.btnMiroirGaucheDroite	= new ToolBarBouton ( "miroir gauche droite"		);
		this.btnMiroirHautBas		= new ToolBarBouton ( "miroir haut bas"			);
		this.btnNoirBlanc			= new ToolBarBouton ( "noir et blanc"			);
		this.btnZoneTexte			= new ToolBarBouton ( "Zone de texte"			);



		
		
		// Dans votre constructeur, après chaque création de bouton :
		uniformiserBouton(this.btnSauvegarder);
		uniformiserBouton(this.btnPipette);
		uniformiserBouton(this.btnPotPeinture);
		uniformiserBouton(this.btnAjouterTexte);
		uniformiserBouton(this.btnCouleur);
		

		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/


		
		this.add(this.btnSauvegarder);
		this.add(this.btnPipette);
		this.add(this.btnPotPeinture);
		this.add(this.btnAjouterTexte);
		this.add(this.btnCouleur);
		
		/*-------------------------------*/
		/* Activation des composants     */
		/*-------------------------------*/
		this.btnSauvegarder .addActionListener(this);
		this.btnPipette     .addActionListener(this);
		this.btnPotPeinture .addActionListener(this);
		this.btnAjouterTexte.addActionListener(this);
		this.btnCouleur		.addActionListener(this);
		
	}
	
	@Override
	public int getHeight() {
		return 40;
	}

	public void setCouleurSelectionnee(Color couleur) {
		this.couleurSelectionnee = couleur;
		this.btnCouleur.setBackground(couleur);
	}

	public void actionPerformed ( ActionEvent e)
	{
		switch (e.getActionCommand()) {
			case "Sauvegarder" -> {
//				this.ctrl.sauvegarder ();
			}
			case "Pipette" -> {
				// Activer le mode pipette dans PanelImage
				FramePrinc frame = (FramePrinc) SwingUtilities.getWindowAncestor(this);
				if (frame != null) {
					frame.getPanelImage().enablePipetteMode(true);
				}
			}
			case "PotDePeinture" -> {}
			case "AjouterDuTexte" -> {
				System.out.println("-----------------");
			}
			case "Couleur" -> {
				// Ouvrir le sélecteur de couleur
				Color nouvelleCouleur = JColorChooser.showDialog(this, "Choisir une couleur", couleurSelectionnee);

				if (nouvelleCouleur != null) {
					couleurSelectionnee = nouvelleCouleur;
					this.btnCouleur.setBackground(couleurSelectionnee); // Mettre à jour la couleur du bouton
				}
			}
		}
	}

	private void uniformiserBouton(JButton bouton) {
		bouton.setPreferredSize(new java.awt.Dimension(40, 40));
		bouton.setMaximumSize(new java.awt.Dimension(40, 40));
		bouton.setMinimumSize(new java.awt.Dimension(40, 40));
	}
}