package org.multimedia.test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import org.multimedia.util.Arithmetique;
import org.multimedia.util.DisplayImage;
import org.multimedia.util.ImageUtils;

public class TestCheckerBoard {
	
	public static void main(String[] args) {
		int width = 1000, height = 600;
		
		BufferedImage res = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		int squareW = Arithmetique.commDiv(width, height) * 4;
		
//		System.out.println(squareW);
		
		Graphics2D g2d = res.createGraphics();
		
		final Color darker   = new Color(125, 125, 125, 255 / 2);
		final Color brighter = new Color(200, 200, 200, 255 / 2);
		
		Color selected = darker;
		
//		DisplayImage.show(ImageUtils.Builder.plainColor(squareW, squareW, darker),   "Darker",   JFrame.DISPOSE_ON_CLOSE);
//		DisplayImage.show(ImageUtils.Builder.plainColor(squareW, squareW, brighter), "Brighter", JFrame.DISPOSE_ON_CLOSE);
		
		for (int y = 0; y < height / squareW; y++) {
			for (int x = 0; x < width / squareW; x++) {
				selected = (x + y) % 2 == 0 ? brighter : darker;
				g2d.setColor(selected);
				g2d.fillRect(x * squareW, y * squareW, squareW, squareW);
//				System.out.println("R:"+y+", C:"+x);
//				System.out.println(x + y % 2);
			}
		}
		
		g2d.dispose();
		
		DisplayImage.show(res);
		
	}
	
}