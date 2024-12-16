package org.multimedia.vue;
import org.multimedia.main.Controleur;
import org.multimedia.util.ImageUtils;

import java.awt.event.*;
import javax.swing.*;
import java.awt.FlowLayout;

public class PanelBouton extends JPanel implements ActionListener
{
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

	public PanelBouton (Controleur ctrl ) 
	{
		this.ctrl = ctrl;

		/*-------------------------------*/
		/* Création des composants       */
		/*-------------------------------*/

		this.setLayout(new FlowLayout()); // Disposition simple pour placer les boutons

        // Bouton 1 avec une image
        this.btnSauvegarder = new JButton(new ImageIcon(ImageUtils.openImg("/save.png", true) ));
        this.btnSauvegarder.setToolTipText("Sauvegarder"); 

		this.btnPipette = new JButton(new ImageIcon(ImageUtils.openImg("/pipette.png", true) ));
        this.btnPipette.setToolTipText("Pipette"); 

		this.btnPotPeinture = new JButton(new ImageIcon(ImageUtils.openImg("/potPeinture.png", true) ));
        this.btnPotPeinture.setToolTipText("Pot de peinture"); 

		this.btnAjouterTexte = new JButton(new ImageIcon(ImageUtils.openImg("/ajoutZoneTexte.png", true) ));
        this.btnAjouterTexte.setToolTipText("Ajouter un texte"); 



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



		this.add ( this.btnSauvegarder			);
		this.add ( this.btnPipette				);
		this.add ( this.btnPotPeinture			);


		this.add ( this.btnAjouterTexte			);

		/*-------------------------------*/
		/* Activation des composants     */
		/*-------------------------------*/
		this.btnSauvegarder			.addActionListener ( this );
		this.btnPipette			.addActionListener ( this );
		this.btnPotPeinture				.addActionListener ( this );
		this.btnAjouterTexte				.addActionListener ( this );
	}



	public void actionPerformed ( ActionEvent e)
	{
		// Sauvegarde
		if ( e.getSource() == this.btnSauvegarder ) { // this.ctrl.sauvegarder (); 
			}

		// Pipette
		if ( e.getSource() == this.btnPipette ) {
			//
		}

		// Pot de peinture
		if ( e.getSource() == this.btnPotPeinture ){
			//
		}

		// Pot de peinture
		if ( e.getSource() == this.btnAjouterTexte ){ System.out.println("-----------------");
			//
		}

	}
}