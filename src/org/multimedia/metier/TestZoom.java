package org.multimedia.metier;

import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import org.multimedia.util.DisplayImage;
import org.multimedia.util.ImageUtils;

/**
 * @author  Gabriel Roche
 * @since   
 */
public class TestZoom {
	
	/**
	 * @param args
	 * @since 
	 */
	public static void main(String[] args) {
		BufferedImage img = ImageUtils.openImg("/blake_decode.png", true);
		
		
		DisplayImage.show(img, JFrame.DISPOSE_ON_CLOSE);
		
		img = ImageUtils.applyZoom(img, 2D);
		
		DisplayImage.show(img, JFrame.DISPOSE_ON_CLOSE);
		
	}
	
}