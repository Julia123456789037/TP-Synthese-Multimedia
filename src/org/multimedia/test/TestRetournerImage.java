package org.multimedia.test;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.multimedia.util.DisplayImage;
import org.multimedia.util.ImageUtils;
import org.multimedia.util.Text;

public class TestRetournerImage {
	
	public static void main(String[] args) {
		BufferedImage img = ImageUtils.openImg("/blake_decode.png", true);
		
		img = ImageUtils.invertHorizontal(img);
		img = ImageUtils.invertVertical(img);
		img = ImageUtils.writeText(img, new Text.Builder("Image retournée !", 500, 500)
				.size(40)
				.color(Color.RED)
				.isBold()
				.build());
		
		DisplayImage.show(img);
	}
	
}