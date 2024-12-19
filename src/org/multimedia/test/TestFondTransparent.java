package org.multimedia.test;

import java.awt.image.BufferedImage;

import org.multimedia.util.DisplayImage;
import org.multimedia.util.ImageUtils;
import org.multimedia.util.ImageUtils.RGB;

public class TestFondTransparent {
	
	public static void main(String[] args) {
		BufferedImage img = ImageUtils.openImg("/blake_decode.png", true);
		
		img = ImageUtils.fill(img, 0, 0, RGB.parse(0, 0, 0, 0), 50);
		
		DisplayImage.show(img);
	}
	
}