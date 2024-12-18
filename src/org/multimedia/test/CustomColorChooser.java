package org.multimedia.test;

import java.awt.Color;
import java.io.Serial;

import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.ColorChooserUI;
import javax.swing.plaf.basic.BasicColorChooserUI;

public class CustomColorChooser extends JColorChooser {
	
	@Serial
	private static final long serialVersionUID = 7278931886677091595L;
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Color c = CustomColorChooser.showDialog("Choisissez une couleur", Color.BLACK);
		
		System.out.println(c);
	}
	
	public static Color showDialog(String title, Color initialColor) {
		Color color = null;
		
		CustomColorChooser chooser = new CustomColorChooser(Color.BLUE);
		
		int result = JOptionPane.showOptionDialog(null, 
			new Object[] {"e", chooser}, 
			title, 
			JOptionPane.OK_CANCEL_OPTION, 
			JOptionPane.PLAIN_MESSAGE, 
			null, null, null
		);
		
		if (result == JOptionPane.OK_OPTION) {
			color = chooser.getColor();
		}
		
		return color;
	}
	
	private ColorChooserUI ui;
	
	private CustomColorChooser(Color initialColor) {
		super(initialColor);
		this.ui = new BasicColorChooserUI();
	}
	
	@Override
	public void setUI(ColorChooserUI ui) {
		super.setUI(this.getUI());
	}
	
	@Override
	public ColorChooserUI getUI() {
		return this.ui;
	}
	
}