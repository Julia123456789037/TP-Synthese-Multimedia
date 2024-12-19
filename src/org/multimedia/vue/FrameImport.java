package org.multimedia.vue;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.Serial;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.multimedia.main.Controleur;

public class FrameImport extends FramePrinc {
	
	@Serial
	private static final long serialVersionUID = 8413036037505934111L;

	public FrameImport(Controleur ctrl) {
		super(ctrl);
		this.getContentPane().remove(this.panelImage); 
		this.panelImage = new PanelImport(ctrl); 
		this.add(this.panelImage, BorderLayout.CENTER); 

		this.remove(this.barreOutils);
		
		this.barreOutils = new BarreOutilsImport(ctrl, this);
		this.add(this.barreOutils,BorderLayout.NORTH);
		

		this.setTitle("Importation image");
		this.revalidate(); 
		this.repaint(); 
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
					this.setTitle("ImportationImage");
					this.panelImage.updateUI();
					
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(this, "Erreur lors du chargement de l'image.");
				}
			}
		});
	}
	
	
}
