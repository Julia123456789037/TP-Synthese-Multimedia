package org.multimedia.vue;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

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

	public BarreOutils (Controleur ctrl ) 
	{
		this.ctrl = ctrl;
		this.setBackground(Color.RED);
		
//		UIManager.put(null, null);
		
		UIDefaults map = UIManager.getDefaults();
		/* 
		for (Entry<Object, Object> o : map.entrySet()) {
			System.out.println(o.getKey().getClass());
			if (((String) o.getKey()).contains("JToolBar"))
				System.out.println(o.getKey());
		}*/
		
		/*-------------------------------*/
		/* Création des composants       */
		/*-------------------------------*/

		// Bouton 1 avec une image
		this.btnSauvegarder = new JButton(new ImageIcon(ImageUtils.openImg("/save.png", true) ));
		this.btnSauvegarder.setToolTipText("Sauvegarder");
		this.btnSauvegarder.setActionCommand("Sauvegarder");

		this.btnPipette = new JButton(new ImageIcon(ImageUtils.openImg("/pipette.png", true) ));
		this.btnPipette.setToolTipText("Pipette");
		this.btnPipette.setActionCommand("Pipette");

		this.btnPotPeinture = new JButton(new ImageIcon(ImageUtils.openImg("/potPeinture.png", true) ));
		this.btnPotPeinture.setToolTipText("Pot de peinture");
		this.btnPotPeinture.setActionCommand("PotDePeinture");

		this.btnAjouterTexte = new JButton(new ImageIcon(ImageUtils.openImg("/ajoutZoneTexte.png", true) ));
		this.btnAjouterTexte.setToolTipText("Ajouter du texte");
		this.btnAjouterTexte.setActionCommand("AjouterDuTexte");


		// Bouton Couleur
		this.btnCouleur = new JButton();
		this.btnCouleur.setToolTipText("Couleur sélectionnée");
		this.btnCouleur.setActionCommand("Couleur");
		this.btnCouleur.setBackground(couleurSelectionnee); 
		this.btnCouleur.setOpaque(true);
		this.btnCouleur.setBorderPainted(true); 



		this.btnSelectionRect		= new JButton ( "Selectionner rectangle"	);
		this.btnSelectionRond		= new JButton ( "Selectionner rond"		);
		this.btnAutreFrame			= new JButton ( "importer autre image"		);
		this.btnFondTransp			= new JButton ( "Fond transparent"			);
		this.btnRotationGauche		= new JButton ( "rotation gauche"			);
		this.btnRotationDroite		= new JButton ( "rotation droite"			);

		this.btnMiroirGaucheDroite	= new JButton ( "miroir gauche droite"		);
		this.btnMiroirHautBas		= new JButton ( "miroir haut bas"			);
		this.btnNoirBlanc			= new JButton ( "noir et blanc"			);
		this.btnZoneTexte			= new JButton ( "Zone de texte"			);



		
		
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

	public void setCouleurSelectionnee(Color couleur) {
		this.ctrl.getFramePrinc().setSelectedColor(couleur);
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
		}
	}

	private void uniformiserBouton(JButton bouton) {
		bouton.setPreferredSize(new java.awt.Dimension(40, 40));
		bouton.setMaximumSize(new java.awt.Dimension(40, 40));
		bouton.setMinimumSize(new java.awt.Dimension(40, 40));
	}
}