package org.multimedia.vue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

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

	public BarreOutils (Controleur ctrl ) 
	{
		this.ctrl = ctrl;
		
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

		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/


		
		this.add(this.btnSauvegarder);
		this.add(this.btnPipette);
		this.add(this.btnPotPeinture);
		this.add(this.btnAjouterTexte);
		
		/*-------------------------------*/
		/* Activation des composants     */
		/*-------------------------------*/
		this.btnSauvegarder .addActionListener(this);
		this.btnPipette     .addActionListener(this);
		this.btnPotPeinture .addActionListener(this);
		this.btnAjouterTexte.addActionListener(this);
		
	}

	public void actionPerformed ( ActionEvent e)
	{
		switch (e.getActionCommand()) {
			case "Sauvegarder" -> {
//				this.ctrl.sauvegarder ();
			}
			case "Pipette" -> {}
			case "PotDePeinture" -> {}
			case "AjouterDuTexte" -> {
				System.out.println("-----------------");
			}
		}
	}
}