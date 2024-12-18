package org.multimedia.vue;

import org.multimedia.main.Controleur;
import org.multimedia.util.ImageUtils;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.awt.Color;
import java.io.Serial;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import java.awt.image.BufferedImage;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FrameImport extends FramePrinc {

	public FrameImport(Controleur ctrl) {
		super(ctrl);
		this.getContentPane().remove(this.panelImage); // Remove old panel
		this.panelImage = new PanelImport(ctrl); // Replace with PanelImport
		this.add(this.panelImage, BorderLayout.CENTER); // Add new panel
		this.revalidate(); // Refresh layout
		this.repaint(); // Redraw frame
	}
	
	
}
