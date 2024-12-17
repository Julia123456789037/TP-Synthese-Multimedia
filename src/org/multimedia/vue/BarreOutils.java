package org.multimedia.vue;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JToolBar;

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
	
	JButton btnUndo;
	JButton btnRedo;
	
	private Color couleurSelectionnee = Color.BLACK;
	
	public BarreOutils(Controleur ctrl) 
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
        
		this.btnCouleur = new ToolBarBouton();
		this.btnCouleur.setToolTipText("Couleur Sélectionnée");
		this.btnCouleur.setActionCommand("Couleur");
		this.btnCouleur.setBackground(this.couleurSelectionnee); 
		this.btnCouleur.setOpaque(true);
		this.btnCouleur.setBorderPainted(true);
		
		this.btnUndo = new ToolBarBouton(new ImageIcon(ImageUtils.openImg("/undo.png", true)));
		this.btnUndo.setToolTipText("Défaire");
		this.btnUndo.setActionCommand("Undo");
		
		this.btnRedo = new ToolBarBouton(new ImageIcon(ImageUtils.openImg("/redo.png", true)));
		this.btnRedo.setToolTipText("Refaire");
		this.btnRedo.setActionCommand("Redo");
		
		this.btnSelectionRect      = new ToolBarBouton("Selectionner Rectangle");
		this.btnSelectionRond      = new ToolBarBouton("Selectionner Rond");
		this.btnAutreFrame         = new ToolBarBouton("Importer une Image");
		this.btnFondTransp         = new ToolBarBouton("Fond Transparent");
		this.btnRotationGauche     = new ToolBarBouton("Rotation à Gauche");
		this.btnRotationDroite     = new ToolBarBouton("Rotation à Droite");

		this.btnMiroirGaucheDroite = new ToolBarBouton("Miroir Horizontal");
		this.btnMiroirHautBas      = new ToolBarBouton("Miroir Vertical");
		this.btnNoirBlanc          = new ToolBarBouton("Niveaux de Gris");
		this.btnZoneTexte          = new ToolBarBouton("Zone de Texte");
		
		// Dans votre constructeur, après chaque création de bouton :
		uniformiserBouton(this.btnSauvegarder);
		uniformiserBouton(this.btnPipette);
		uniformiserBouton(this.btnPotPeinture);
		uniformiserBouton(this.btnAjouterTexte);
		uniformiserBouton(this.btnCouleur);
		
		this.uniformiserBouton(this.btnUndo);
		this.uniformiserBouton(this.btnRedo);

		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/


		
		this.add(this.btnSauvegarder);
		this.add(this.btnPipette);
		this.add(this.btnPotPeinture);
		this.add(this.btnAjouterTexte);
		this.add(this.btnCouleur);
		this.add(this.btnUndo);
		this.add(this.btnRedo);
		
		/*-------------------------------*/
		/* Activation des composants     */
		/*-------------------------------*/
		this.btnSauvegarder .addActionListener(this);
		this.btnPipette     .addActionListener(this);
		this.btnPotPeinture .addActionListener(this);
		this.btnAjouterTexte.addActionListener(this);
		this.btnCouleur		.addActionListener(this);
		
		this.btnUndo.addActionListener(this);
		this.btnRedo.addActionListener(this);
		
	}
	
	@Override
	public int getHeight() {
		return 40;
	}

	public void setCouleurSelectionnee(Color couleur) {
		this.ctrl.getFramePrinc().setSelectedColor(couleur);
		this.couleurSelectionnee = couleur;
		this.btnCouleur.setBackground(couleur);
	}

	public void actionPerformed ( ActionEvent e)
	{
		final PanelImage panel = this.ctrl.getFramePrinc().getPanelImage();
		switch (e.getActionCommand()) {
			case "Sauvegarder" -> {
//				this.ctrl.sauvegarder ();
			}
			case "Pipette" -> {
				// Activer le mode pipette dans PanelImage
				panel.enablePipetteMode(true);
			}
			case "PotDePeinture" -> {
				this.ctrl.getFramePrinc().getPanelImage().enablePotPeintureMode(!this.ctrl.getFramePrinc().getPanelImage().isPotPeintureMode());
			}
			case "AjouterDuTexte" -> {
				System.out.println("-----------------");
			}
			case "Couleur" -> {
				// Ouvrir le sélecteur de couleur
				Color nouvelleCouleur = JColorChooser.showDialog(this, "Choisir une couleur", couleurSelectionnee);

				if (nouvelleCouleur != null) {
					this.ctrl.getFramePrinc().setSelectedColor(nouvelleCouleur);
					couleurSelectionnee = nouvelleCouleur;
					this.btnCouleur.setBackground(couleurSelectionnee); // Mettre à jour la couleur du bouton
				}
			}
			case "Undo" -> {
				panel.transform.undo();
				panel.updateUI();
			}
			case "Redo" -> {
				panel.transform.redo();
				panel.updateUI();
			}
		}
	}

	private void uniformiserBouton(JButton bouton) {
		bouton.setPreferredSize(new Dimension(40, 40));
		bouton.setMaximumSize(new Dimension(40, 40));
		bouton.setMinimumSize(new Dimension(40, 40));
	}
}