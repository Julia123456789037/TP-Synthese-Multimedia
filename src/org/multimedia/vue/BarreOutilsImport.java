package org.multimedia.vue;
import org.multimedia.main.Controleur;

import java.awt.event.ActionEvent;


public class BarreOutilsImport extends BarreOutils  {
	
	private FrameImport frameImport;

	public BarreOutilsImport(Controleur ctrl, FrameImport fp)
	{
		super(ctrl);
		this.frameImport =fp;
        this.panel.remove(btnCouleur);
        this.panel.remove(btnPipette);
        this.panel.remove(btnPotPeinture);
        this.panel.remove(btnFondTransp);
        this.panel.remove(comboTailleTexte);
        this.panel.remove(btnAjouterTexte);
        this.panel.remove(textFieldTexte);
        this.panel.remove(btnAutreFrame);
        this.panel.remove(btnColerForme);
        this.panel.remove(btnPremPlan);
        this.panel.remove(btnDeuxPlan);
        this.panel.remove(btnAvDerPlan);
        this.panel.remove(btnArrPlan);
        this.panel.remove(btnSauvegarder);
	}

	@Override
	public void actionPerformed ( ActionEvent e)
	{
		final PanelImage panelIm = BarreOutilsImport.this.frameImport.getPanelImage();
        if (e.getActionCommand().equals("changeImage")) { BarreOutilsImport.this.frameImport.mnuOpenFileListener(e); }
		else if (panelIm.getImage() == null && !e.getActionCommand().equals("Couleur")) { return; }
			
		switch (e.getActionCommand()) {
			case "CopierRectangle" 	-> { System.out.println("Rectangle"); panelIm.enableSelectionRect( ! panelIm.isSelectionRectMode() ); }
			case "CopierRond" 		-> { panelIm.enableSelectionRond( ! panelIm.isSelectionRondMode() ); }
			case "Undo" -> {
				panelIm.transform.undo();
				panelIm.updateUI();
			}
			case "Redo" -> {
				panelIm.transform.redo();
				panelIm.updateUI();
			}
			case "SourisNormal" 	-> { panelIm.curseurMode( ); }
		}
	}
}
