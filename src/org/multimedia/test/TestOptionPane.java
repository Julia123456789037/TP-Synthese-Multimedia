package org.multimedia.test;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * @author  Gabriel Roche
 * @since   
 */
public class TestOptionPane {
	
	/**
	 * @param args
	 * @since 
	 */
	public static void main(String[] args) {
		JOptionPane.showOptionDialog(null, 
			new Object[] {
				"Test"
			}, 
			"Test", 
			JOptionPane.DEFAULT_OPTION, 
			JOptionPane.PLAIN_MESSAGE, 
			null, null, null
		);
		JDialog dia = new JDialog();
		dia.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dia.add(new JLabel("Hello"));
		dia.pack();
		dia.setVisible(true);
	}
	
}