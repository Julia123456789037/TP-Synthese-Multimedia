package org.multimedia.vue;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JColorChooser;

import org.multimedia.main.Controleur;

public class BarreOutilsImport extends BarreOutils  {
	
	private FrameImport frameImport;

	public BarreOutilsImport(Controleur ctrl, FrameImport fp)
	{
		super(ctrl);
		this.frameImport =fp;
	}

	@Override
	public void actionPerformed ( ActionEvent e)
	{
		final PanelImage panelIm = BarreOutilsImport.this.frameImport.getPanelImage();
		if (panelIm.getImage() == null && !e.getActionCommand().equals("Couleur"))
			return;
		switch (e.getActionCommand()) {
			case "changeImage" -> { BarreOutilsImport.this.frameImport.mnuOpenFileListener(e); }
			case "Sauvegarder"   -> BarreOutilsImport.this.frameImport.save(e);
			case "Pipette"       -> panelIm.enablePipetteMode( ! panelIm.isPipetteMode() );
			case "PotDePeinture" -> panelIm.enablePotPeintureMode( !panelIm.isPotPeintureMode() );
			case "FondTransparent" 	-> panelIm.paintTransp( );
			case "Couleur"       -> {
				// Ouvrir le sélecteur de couleur
				Color nouvelleCouleur = JColorChooser.showDialog(this, "Choisir une couleur", couleurSelectionnee);

				if (nouvelleCouleur != null) {
					BarreOutilsImport.this.frameImport.setSelectedColor(nouvelleCouleur);
					couleurSelectionnee = nouvelleCouleur;
					this.btnCouleur.setBackground(couleurSelectionnee); // Mettre à jour la couleur du bouton
				}
			}
			case "CopierRectangle" 	-> { System.out.println("Rectangle"); panelIm.enableSelectionRect( ! panelIm.isSelectionRectMode() ); }
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
			case "changeAutreImage" -> { panelIm.openSourcePanel(); }
			case "collerForme"      -> { panelIm.saveImageWithOverlap(new File("rendu.png"));}
			case "1erPlan"          -> { panelIm.premPlan(); }
			case "passe1niv"        -> { panelIm.avanverUnPlan(); }
			case "reculer1niv"      -> { panelIm.reculerUnPlan(); }
			case "dernierPlan"      -> { panelIm.dernPlan(); }
		}
	}
}
