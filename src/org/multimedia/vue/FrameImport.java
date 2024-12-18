package org.multimedia.vue;

import java.awt.BorderLayout;
import java.io.Serial;

import org.multimedia.main.Controleur;

public class FrameImport extends FramePrinc {
	
	@Serial
	private static final long serialVersionUID = 8413036037505934111L;

	public FrameImport(Controleur ctrl) {
		super(ctrl);
		this.getContentPane().remove(this.panelImage); // Remove old panel
		this.panelImage = new PanelImport(ctrl); // Replace with PanelImport
		this.add(this.panelImage, BorderLayout.CENTER); // Add new panel
		this.revalidate(); // Refresh layout
		this.repaint(); // Redraw frame
	}
	
	
}
