package org.multimedia.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.imageio.ImageIO;

/**
 * Classe utilitaire, indispensable pour la manipulation d'images.
 * <br><br>
 * Chacunes des méthodes de la classe {@code ImageUtils} renvoie une nouvelle image après le traitement.
 * Aucun résultat n'est renvoyé par référence.
 * <br><br>
 * <b>Date :</b> 14/12/2024
 * @author Gabriel Roche
 */
public class ImageUtils
{
	
	/**
	 * Constructeur à ne pas instancier.
	 */
	private ImageUtils() {}
	
	/**
	 * Ouvre une image et retourne un objet modifiable ({@link BufferedImage}).
	 * 
	 * @param name
	 * @return
	 */
	public static BufferedImage openImg(String name)
	{
		return ImageUtils.openImg(name, false);
	}
	
	/**
	 * Ouvre une image et retourne un objet modifiable ({@link BufferedImage}).
	 * 
	 * @param name
	 * @param isResource
	 * @return
	 */
	public static BufferedImage openImg(String name, boolean isResource)
	{
		if (name == null)
			return null;
		if (!isResource)
			return ImageUtils.openImg(new File(name));
		else
			return ImageUtils.openImg(ImageUtils.class.getResourceAsStream(name));
	}
	
	/**
	 * Ouvre une image et retourne un objet modifiable ({@link BufferedImage}).
	 * 
	 * @param file
	 * @return
	 */
	public static BufferedImage openImg(File file)
	{
		if (file == null)
			return null;
		try
		{
			return ImageIO.read(file);
		} catch (IOException e)
		{
			return null;
		}
	}
	
	/**
	 * Ouvre une image et retourne un objet modifiable ({@link BufferedImage}).
	 * 
	 * @param stream
	 * @return
	 */
	public static BufferedImage openImg(InputStream stream)
	{
		if (stream == null)
			return null;
		try
		{
			return ImageIO.read(stream);
		} catch (IOException e)
		{
			return null;
		}
	}
	
	/**
	 * Convertir les couleurs de l'image en noir et blanc.
	 * 
	 * @param img
	 * @return
	 */
	public static BufferedImage toGreyScale(BufferedImage img)
	{
		try
		{
			return ImageUtils.toGreyScale(img, 2);
		} catch (NoSuchAlgorithmException e)
		{
			return null;
		}
	}
	
	/**
	 * Convertir les couleurs de l'image en noir et blanc.
	 * 
	 * @param img
	 * @param algorithm
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static BufferedImage toGreyScale(BufferedImage img, int algorithm) throws NoSuchAlgorithmException
	{
		if (img == null)
			return null;
		BufferedImage res = Builder.deepClone(img);
		for (int x = 0; x < img.getWidth(); x++)
		{
			for (int y = 0; y < img.getHeight(); y++)
			{
				int rgb = img.getRGB(x, y);
				int r = RGB.getRed(rgb);
				int g = RGB.getGreen(rgb);
				int b = RGB.getBlue(rgb);
				int greyscale = switch (algorithm)
				{
					case 1  -> (Math.min(r, Math.min(g, b)) + Math.max(r, Math.max(g, b))) / 2;
					case 2  -> (r + g + b) / 3;
					case 3  -> (int) (r * 0.299 + g * 0.587 + b * 0.114) / 3;
					default -> throw new NoSuchAlgorithmException();
				};
				res.setRGB(x, y, RGB.parse(greyscale, greyscale, greyscale));
			}
		}
		return res;
	}
	
	/**
	 * Augmente ou diminue la luminosité d'une image.
	 * 
	 * @param img
	 * @param brightness
	 * @return
	 */
	public static BufferedImage applyBrightness(BufferedImage img, int brightness)
	{
		if (brightness > 255 || brightness < -255)
			throw new IndexOutOfBoundsException("Brightness offset must be contained between -255 and 255");
		if (img == null)
			return null;
		BufferedImage res = Builder.deepClone(img);
		for (int y = 0; y < img.getHeight(); y++)
		{
			for (int x = 0; x < img.getWidth(); x++)
			{
				int rgb = img.getRGB(x, y);
				int r = RGB.getRed(rgb);
				int g = RGB.getGreen(rgb);
				int b = RGB.getBlue(rgb);
				
				r = RGB.limit(r + brightness);
				g = RGB.limit(g + brightness);
				b = RGB.limit(b + brightness);
				
				res.setRGB(x, y, RGB.parse(r, g, b));
			}
		}
		
		return res;
	}
	
	/**
	 * Augmente ou diminue le contraste d'une image.
	 * 
	 * @param img
	 * @param contrast
	 * @return
	 */
	public static BufferedImage applyContrast(BufferedImage img, int contrast)
	{
		if (contrast < -100 || contrast > 100)
			throw new IllegalArgumentException("Constrast must be between -100 and 100");
		if (img == null)
			return null;
		float tmp = contrast;
		if (contrast == 0)
			tmp = 1F;
		if (contrast < 0)
			tmp = 1 - Math.abs(contrast) / 100F;
		BufferedImage res = Builder.deepClone(img);
		for (int x = 0; x < img.getWidth(); x++)
		{
			for (int y = 0; y < img.getHeight(); y++)
			{
				int rgb = img.getRGB(x, y);
				int r = RGB.getRed(rgb);
				int g = RGB.getGreen(rgb);
				int b = RGB.getBlue(rgb);
				r = RGB.limit((int) ((r - 127) * tmp + 127));
				g = RGB.limit((int) ((g - 127) * tmp + 127));
				b = RGB.limit((int) ((b - 127) * tmp + 127));
				res.setRGB(x, y, RGB.parse(r, g, b));
			}
		}
		return res;
	}
	
	/**
	 * Remplace la couleur au point {@code X:Y} et tous les points adjacents de couleur similaire avec un écart de 80,
	 * par la couleur de votre choix.
	 * 
	 * @param img
	 * @param x
	 * @param y
	 * @param color
	 * @return
	 */
	public static BufferedImage fill(BufferedImage img, int x, int y, Color color)
	{
		if (color == null)
			return null;
		return fill(img, x, y, color.getRGB());
	}
	
	/**
	 * Remplace la couleur au point {@code X:Y} et tous les points adjacents de couleur similaire avec un écart de 80,
	 * par la couleur de votre choix.
	 * 
	 * @param img
	 * @param x
	 * @param y
	 * @param rgb
	 * @return
	 */
	public static BufferedImage fill(BufferedImage img, int x, int y, int rgb)
	{
		if (x < 0 || x > img.getWidth() || y < 0 || y > img.getHeight())
			throw new IndexOutOfBoundsException("Coordinates out of bounds: (" + x + ", " + y + ")");
		BufferedImage res = Builder.deepClone(img);
		Queue<Point> file = new LinkedList<Point>();
		int origC = res.getRGB(x, y) & 0xFFFFFF;
		
		if (origC == rgb)
			return res;

		file.add(new Point(x, y));
		
		int width  = res.getWidth();
		int height = res.getHeight();

		while (!file.isEmpty()) {
			Point p = file.remove();
			if (p.x >= 0 && p.x < width && p.y >= 0 && p.y < height)
			{
				int curC = res.getRGB(p.x, p.y) & 0xFFFFFF;
				double diff = RGB.difference(origC, curC);
				
				if (diff >= 0.0 && diff <= 80.0)
				{
					res.setRGB(p.x, p.y, rgb);

					file.add(new Point(p.x + 1, p.y));
					file.add(new Point(p.x - 1, p.y));
					file.add(new Point(p.x,	 p.y - 1));
					file.add(new Point(p.x,	 p.y + 1));
				}
			}
		}
		return res;
	}
	
	/**
	 * Inclusion naïve d'une image dans une autre.
	 * 
	 * @param source
	 * @param img
	 * @param x
	 * @param y
	 * @return
	 */
	public static BufferedImage naiveIncludeIn(BufferedImage source, BufferedImage img, int x, int y)
	{
		if (img.getHeight() > source.getHeight() || img.getWidth() > source.getWidth())
			throw new IllegalArgumentException("image cannot be greater than source");
		if (x < 0 || y < 0 || x + img.getWidth() > source.getWidth() || y + img.getHeight() > source.getHeight())
			throw new IndexOutOfBoundsException("Include coordinates out of bounds on source image");
		BufferedImage res = Builder.deepClone0(source);
		pixelLoop(img, (x0, y0) -> res.setRGB(x + x0, y + y0, img.getRGB(x0, y0)));
		return res;
	}
	
	/**
	 * Inclusion d'une image dans une autre, en filtrant une couleur particulière.
	 * 
	 * @param source
	 * @param img
	 * @param x
	 * @param y
	 * @param color
	 * @return
	 */
	public static BufferedImage includeIn(BufferedImage source, BufferedImage img, int x, int y, Color color)
	{
		return includeIn(source, img, x, y, color.getRGB());
	}
	
	/**
	 * Inclusion d'une image dans une autre, en filtrant une couleur particulière.
	 * 
	 * @param source
	 * @param img
	 * @param x
	 * @param y
	 * @param rgb
	 * @return
	 */
	public static BufferedImage includeIn(BufferedImage source, BufferedImage img, int x, int y, int rgb)
	{
		if (img.getHeight() > source.getHeight() || img.getWidth() > source.getWidth())
			throw new IllegalArgumentException("image cannot be greater than source");
		if (x < 0 || y < 0 || x + img.getWidth() > source.getWidth() || y + img.getHeight() > source.getHeight())
			throw new IndexOutOfBoundsException("Include coordinates out of bounds on source image");
		BufferedImage res = Builder.deepClone0(source);
		pixelLoop(img, (x0, y0) ->
		{
			int rgb0 = img.getRGB(x0, y0);
			if (rgb0 != rgb)
				res.setRGB(x + x0, y + y0, rgb0);
		});
		return res;
	}
	
	/**
	 * Découpe un segment d'une image à partir des coordonnées données.
	 * 
	 * @param img
	 * @param posA
	 * @param posB
	 * @return
	 */
	public static BufferedImage subImage(BufferedImage img, Point posA, Point posB) {
		if (posA == null || posB == null)
			return null;
		return ImageUtils.subImage(img, posA.x, posA.y, posB.x, posB.y);
	}
	
	/**
	 * Découpe un segment d'une image à partir des coordonnées données.
	 * 
	 * @param img
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static BufferedImage subImage(BufferedImage img, int x1, int y1, int x2, int y2) {
		if (img == null)
			return null;
		BufferedImage tmp = new BufferedImage(x2 - x1, y2 - y1, img.getType());
		for (int x = 0; x < tmp.getWidth(); x++) {
			for (int y = 0; y < tmp.getHeight(); y++) {
				tmp.setRGB(x, y, img.getRGB(x + x1, y + y1));
			}
		}
		return tmp;
	}
	
	/**
	 * Effectue une rotation de l'image.
	 * 
	 * @param image
	 * @param angle
	 * @return
	 */
	public static BufferedImage rotate(BufferedImage image, double angle)
	{
		int largeur = image.getWidth();
	    int hauteur = image.getHeight();
	    
	    // Calculer la taille de la nouvelle image
	    double radians = Math.toRadians(angle);
	    double sin = Math.abs(Math.sin(radians));
	    double cos = Math.abs(Math.cos(radians));
	    int nouvelleLargeur = (int) Math.ceil(largeur * cos + hauteur * sin);
	    int nouvelleHauteur = (int) Math.ceil(hauteur * cos + largeur * sin);
	    
	    BufferedImage resultat = new BufferedImage(nouvelleLargeur, nouvelleHauteur, BufferedImage.TYPE_INT_ARGB);
	    
	    // Calculer le centre de l'image originale et de la nouvelle image
	    double x0 = 0.5 * (largeur - 1);
	    double y0 = 0.5 * (hauteur - 1);
	    double xc = 0.5 * (nouvelleLargeur - 1);
	    double yc = 0.5 * (nouvelleHauteur - 1);
	    
	    for (int y = 0; y < nouvelleHauteur; y++)
	    {
	        for (int x = 0; x < nouvelleLargeur; x++)
	        {
	            // Calculer la position relative au centre de la nouvelle image
	            double a = x - xc;
	            double b = y - yc;
	            
	            // Appliquer la rotation inverse et translater vers le centre de l'image originale
	            int xx = (int) (a * Math.cos(radians) + b * Math.sin(radians) + x0);
	            int yy = (int) (-a * Math.sin(radians) + b * Math.cos(radians) + y0);
	            
	            if (xx >= 0 && xx < largeur && yy >= 0 && yy < hauteur)
	            {
	                resultat.setRGB(x, y, image.getRGB(xx, yy));
	            }
	        }
	    }
	    
	    return resultat;
	}
	
	public static BufferedImage invertHorizontal(BufferedImage img) {
		BufferedImage res = Builder.clone(img);
		int width = res.getWidth();
		pixelLoop(img, (x, y) -> res.setRGB(width - x - 1, y, img.getRGB(x, y)));
		return res;
	}
	
	public static BufferedImage invertVertical(BufferedImage img) {
		BufferedImage res = Builder.clone(img);
		int height = res.getHeight();
		pixelLoop(img, (x, y) -> res.setRGB(x, height - y - 1, img.getRGB(x, y)));
		return res;
	}
	
	public static BufferedImage writeText(BufferedImage img, Text text) {
		BufferedImage res = Builder.deepClone(img);
		Graphics2D g2d = res.createGraphics();
		g2d.setColor(text.getColor());
		g2d.setFont(text.getFont());
		g2d.drawString(text.getText(), text.x(), text.y());
		g2d.dispose();
		return res;
	}
	
	/**
	 * TODO: Méthode pas indispensable, à faire plus tard.
	 * 
	 * @param img
	 * @param text
	 * @param mask
	 * @return
	 */
	public static BufferedImage writeTextMask(BufferedImage img, Text text, BufferedImage mask) {
		return null;
	}
	
	/*----------------*/
	/*    À tester    */
	/*----------------*/
	
	/**
	 * Inverse les couleurs d'une image.
	 * 
	 * @param img
	 * @return
	 */
	public static BufferedImage invertFilter(BufferedImage img)
	{
		BufferedImage tmp = Builder.clone(img);
		pixelLoop(img, (x, y) -> tmp.setRGB(x, y, img.getRGB(x, y) & 0xFFFFFF));
		return tmp;
	}
	
	/**
	 * Méthode utilitaire permettant d'appliquer un traitement ({@link PixelIterator}), sur chaque pixel d'une image.
	 * 
	 * @param img
	 * @param pi
	 */
	static void pixelLoop(BufferedImage img, PixelIterator pi)
	{
		for (int x = 0; x < img.getWidth(); x++)
		{
			for (int y = 0; y < img.getHeight(); y++)
			{
				pi.applyFilter(x, y);
			}
		}
	}
	
	public static List<BufferedImage> cutOut(BufferedImage img, int width, int height) {
		List<BufferedImage> lst = new ArrayList<>();
		BufferedImage tmp;
		for (int y = 0; y < img.getHeight() / height; y++) {
			for (int x = 0; x < img.getWidth() / width; x++) {
				tmp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				ImageUtils.copyRGB(img, new Point(x * width, y * height), tmp);
				lst.add(tmp);
			}
		}
		return lst;
	}
	
	private static void copyRGB(BufferedImage origin, Point pos, BufferedImage destination)
	{
		for (int y = 0; y < destination.getHeight(); y++)
		{
			for (int x = 0; x < destination.getWidth(); x++)
			{
				destination.setRGB(x, y, origin.getRGB(x + pos.x, y + pos.y));
			}
		}
	}
	
	public static BufferedImage mergeH(BufferedImage img1, BufferedImage img2)
	{
		if (img1 == null || img2 == null)
			return null;
		BufferedImage tmp = new BufferedImage(img1.getWidth() + img2.getWidth(), Math.max(img1.getHeight(), img2.getHeight()), img1.getType());
		
		for (int y = 0; y < img1.getHeight(); y++)
		{
			for (int x = 0; x < img1.getWidth(); x++)
			{
				tmp.setRGB(x, y, img1.getRGB(x, y));
			}
		}
		
		for (int y = 0; y < img2.getHeight(); y++)
		{
			for (int x = 0; x < img2.getWidth(); x++)
			{
				tmp.setRGB(x + img1.getWidth(), y, img2.getRGB(x, y));
			}
		}
		return tmp;
	}
	
	public static BufferedImage mergeV(BufferedImage img1, BufferedImage img2)
	{
		if (img1 == null || img2 == null)
			return null;
		BufferedImage tmp = new BufferedImage(Math.max(img1.getWidth(), img2.getWidth()), img1.getHeight() + img2.getHeight(), img1.getType());
		
		for (int y = 0; y < img1.getHeight(); y++)
		{
			for (int x = 0; x < img1.getWidth(); x++)
			{
				tmp.setRGB(x, y, img1.getRGB(x, y));
			}
		}
		
		for (int y = 0; y < img2.getHeight(); y++)
		{
			for (int x = 0; x < img2.getWidth(); x++)
			{
				tmp.setRGB(x, y + Math.max(img1.getHeight(), img2.getHeight()), img2.getRGB(x, y));
			}
		}
		return tmp;
	}
	
	public static BufferedImage meltedMergeH(BufferedImage img1, BufferedImage img2, int length)
	{
		if (img1 == null || img2 == null || length > Math.min(img1.getWidth(), img2.getWidth()))
			return null;
		BufferedImage tmp = ImageUtils.mergeH(img1, img2);
		
		int rgb1  = tmp.getRGB(img1.getWidth() - length, 0), rgb2 = tmp.getRGB(img2.getWidth() + length, 0);
		int diffR = RGB.getRed(rgb1)   - RGB.getRed(rgb2);
		int diffG = RGB.getGreen(rgb1) - RGB.getGreen(rgb2);
		int diffB = RGB.getBlue(rgb1)  - RGB.getBlue(rgb2);
		float rFactor = (float) (Math.abs(diffR) / (length * 2.0));
		float gFactor = (float) (Math.abs(diffG) / (length * 2.0));
		float bFactor = (float) (Math.abs(diffB) / (length * 2.0));
		
		for (int x = img1.getWidth() - length, i = 0; x <= img2.getWidth() + length; x++, i++)
		{
			for (int y = 0; y < tmp.getHeight(); y++) {
				float r = (RGB.getRed(tmp.getRGB(x, y)) + (i * rFactor) * (diffR < 0 ? 1.0F : -1.0F));
				float g = (RGB.getGreen(tmp.getRGB(x, y)) + (i * gFactor) * (diffG < 0 ? 1.0F : -1.0F));
				float b = (RGB.getBlue(tmp.getRGB(x, y)) + (i * bFactor) * (diffB < 0 ? 1.0F : -1.0F));
				tmp.setRGB(x, y, RGB.parse(r, g, b));
			}
		}
		
		return tmp;
	}
	
	public static BufferedImage merge(BufferedImage img1, BufferedImage img2)
	{
		if (img1 == null || img2 == null)
			return null;
		int width  = Math.max(img1.getWidth(),  img2.getWidth());
		int height = Math.max(img1.getHeight(), img2.getHeight());
		BufferedImage tmp = new BufferedImage(width, height, img1.getType());
		
		for (int y = 0; y < tmp.getHeight(); y++)
		{
			for (int x = 0; x < tmp.getWidth(); x++)
			{
				// Calculez le facteur de fondu (de 0 à 1)
				float alpha = (float) x / tmp.getWidth();
				
				// Obtenez les couleurs des pixels correspondants dans les deux images
				int rgb1 = (x < img1.getWidth() && y < img1.getHeight()) ? img1.getRGB(x, y) : 0;
				int rgb2 = (x < img2.getWidth() && y < img2.getHeight()) ? img2.getRGB(x, y) : 0;
				
				// Effectuez le mélange des couleurs
				int r = (int) ((1 - alpha) * ((rgb1 >> 16) & 0xFF) + alpha * ((rgb2 >> 16) & 0xFF));
				int g = (int) ((1 - alpha) * ((rgb1 >> 8) & 0xFF) + alpha * ((rgb2 >> 8) & 0xFF));
				int b = (int) ((1 - alpha) * (rgb1 & 0xFF) + alpha * (rgb2 & 0xFF));
				
				// Définissez la couleur du pixel dans l'image de sortie
				tmp.setRGB(x, y, RGB.parse(r, g, b));
			}
		}
		
		return tmp;
	}
	
	/**
	 * Appliquer un zoom sur l'image.
	 * 
	 * @param img
	 * @param zoom
	 * @return
	 */
	public static BufferedImage applyZoom(BufferedImage img, float zoom)
	{
		BufferedImage tmp = new BufferedImage((int) (img.getWidth() * zoom), (int) (img.getHeight() * zoom), img.getType());
		
		for (int y = 0; y < tmp.getHeight(); y++)
		{
			for (int x = 0; x < tmp.getWidth(); x++)
			{
				tmp.setRGB(x, y, img.getRGB((int) Math.floor(x / zoom), (int) Math.floor(y / zoom)));
			}
		}
		
		return ImageUtils.applyMedianFilter(tmp, 2);
	}
	
	/**
	 * Appliquer un filtre mégian sur l'image.
	 * 
	 * @param img
	 * @param windowSize
	 * @return
	 */
	public static BufferedImage applyMedianFilter(BufferedImage img, int windowSize)
	{
		int width = img.getWidth();
		int height = img.getHeight();
		BufferedImage outputImage = new BufferedImage(width, height, img.getType());
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				List<Integer> neighbors = new ArrayList<>();
				for (int dy = -windowSize/2; dy <= windowSize/2; dy++)
				{
					for (int dx = -windowSize/2; dx <= windowSize/2; dx++)
					{
						int nx = Math.min(Math.max(x + dx, 0), width - 1);
						int ny = Math.min(Math.max(y + dy, 0), height - 1);
						neighbors.add(img.getRGB(nx, ny));
					}
				}
				Collections.sort(neighbors);
				outputImage.setRGB(x, y, neighbors.get(neighbors.size() / 2));
			}
		}
		return outputImage;
	}
	
	/**
	 * Appliquer un filtre gaussien sur l'image.
	 * 
	 * @param image
	 * @param radius
	 * @param sigma
	 * @return
	 */
	public static BufferedImage applyGaussianFilter(BufferedImage image, int radius, double sigma)
	{
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage outputImage = new BufferedImage(width, height, image.getType());
		double[][] kernel = createGaussianKernel(radius, sigma);
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				double red = 0, green = 0, blue = 0;
				double weight = 0;
				for (int ky = -radius; ky <= radius; ky++)
				{
					for (int kx = -radius; kx <= radius; kx++)
					{
						int nx = Math.min(Math.max(x + kx, 0), width - 1);
						int ny = Math.min(Math.max(y + ky, 0), height - 1);
						int rgb = image.getRGB(nx, ny);
						double w = kernel[ky + radius][kx + radius];
						
						red += ((rgb >> 16) & 0xFF) * w;
						green += ((rgb >> 8) & 0xFF) * w;
						blue += (rgb & 0xFF) * w;
						weight += w;
					}
				}
				
				int r = (int) (red / weight);
				int g = (int) (green / weight);
				int b = (int) (blue / weight);
				
				outputImage.setRGB(x, y, (r << 16) | (g << 8) | b);
			}
		}
		return outputImage;
	}
	
	private static double[][] createGaussianKernel(int radius, double sigma)
	{
		int size = 2 * radius + 1;
		double[][] kernel = new double[size][size];
		double sum = 0;
		
		for (int y = -radius; y <= radius; y++)
		{
			for (int x = -radius; x <= radius; x++)
			{
				kernel[y + radius][x + radius] = Math.exp(-(x * x + y * y) / (2 * sigma * sigma));
				sum += kernel[y + radius][x + radius];
			}
		}
		
		for (int y = 0; y < size; y++)
		{
			for (int x = 0; x < size; x++)
			{
				kernel[y][x] /= sum;
			}
		}
		
		return kernel;
	}
	
	/**
	 * Classe utilitaire permettant la création et la duplication d'images.
	 * <br><br>
	 * <b>Date :</b> 14/12/2024
	 * @author Gabriel Roche
	 */
	public static class Builder
	{
		
		/**
		 * Constructeur, à ne pas instancier.
		 */
		private Builder() {}
		
		/**
		 * Créé une image de dimension {@code width / height} remplie d'une couleur.
		 * 
		 * @param width
		 * @param height
		 * @param color
		 * @return
		 */
		public static BufferedImage plainColor(int width, int height, Color color)
		{
			return Builder.plainColor(width, height, color.getRGB());
		}
		
		/**
		 * Créé une image de dimension {@code width / height} remplie d'une couleur.
		 * 
		 * @param width
		 * @param height
		 * @param rgb
		 * @return
		 */
		public static BufferedImage plainColor(int width, int height, int rgb)
		{
			BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			for (int y = 0; y < height; y++)
			{
				for (int x = 0; x < width; x++)
				{
					img.setRGB(x, y, rgb);
				}
			}
			return img;
		}
		
		/**
		 * Créé un clone d'une image avec les mêmes dimensions et type.
		 * 
		 * @param img
		 * @return
		 */
		public static BufferedImage clone(BufferedImage img)
		{
			return new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
		}
		
		/**
		 * Créé un clone d'une image en copiant également les pixels.
		 * 
		 * @param img
		 * @return
		 */
		public static BufferedImage deepClone(BufferedImage img)
		{
			return new BufferedImage(img.getColorModel(), img.copyData(null), img.getColorModel().isAlphaPremultiplied(), null);
		}
		
		/**
		 * @deprecated Duplicata
		 * @param img
		 * @return
		 */
		@Deprecated
		public static BufferedImage deepClone0(BufferedImage img)
		{
			BufferedImage res = Builder.clone(img);
			for (int x = 0; x < res.getWidth(); x++)
			{
				for (int y = 0; y < res.getHeight(); y++)
				{
					res.setRGB(x, y, img.getRGB(x, y));
				}
			}
			return res;
		}
		
	}
	
	/**
	 * Classe utilitaire pour les couleur RGB pures.
	 * <br><br>
	 * <b>Date :</b> 14/12/2024
	 * @author Gabriel Roche
	 */
	public static class RGB
	{
		
		/**
		 * Constructeur, à ne pas instancier.
		 */
		private RGB() {}
		
		/**
		 * Retourne la valeur Alpha d'une couleur.
		 * 
		 * @param rgb
		 * @return
		 */
		public static int getAlpha(int rgb)
		{
			return (rgb >> 24) & 0xFF;
		}
		
		/**
		 * Retourne la valeur Rouge d'une couleur.
		 * 
		 * @param rgb
		 * @return
		 */
		public static int getRed(int rgb)
		{
			return (rgb >> 16) & 0xFF;
		}
		
		/**
		 * Retourne la valeur Vert d'une couleur.
		 * 
		 * @param rgb
		 * @return
		 */
		public static int getGreen(int rgb)
		{
			return (rgb >> 8) & 0xFF;
		}
		
		/**
		 * Retourne la valeur Bleu d'une couleur.
		 * 
		 * @param rgb
		 * @return
		 */
		public static int getBlue(int rgb)
		{
			return rgb & 0xFF;
		}
		
		/**
		 * Converti les valeurs Rouge, Vert et Bleu en couleur.
		 * 
		 * @param r
		 * @param g
		 * @param b
		 * @return
		 */
		public static int parse(int r, int g, int b)
		{
			return RGB.parse(255, r, g, b);
		}
		
		/**
		 * Converti les valeurs Alpha, Rouge, Vert et Bleu en couleur.
		 * 
		 * @param a
		 * @param r
		 * @param g
		 * @param b
		 * @return
		 */
		public static int parse(int a, int r, int g, int b)
		{
			if (a < 0 || a > 255 || r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255)
				return -1;
			return ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | ((b & 0xFF) << 0);
		}
		
		/**
		 * Converti les valeurs Rouge, Vert et Bleu en couleur.
		 * 
		 * @param r
		 * @param g
		 * @param b
		 * @return
		 */
		public static int parse(float r, float g, float b)
		{
			return RGB.parse(255, r, g, b);
		}
		
		/**
		 * Converti les valeurs Alpha, Rouge, Vert et Bleu en couleur.
		 * 
		 * @param a
		 * @param r
		 * @param g
		 * @param b
		 * @return
		 */
		public static int parse(int a, float r, float g, float b)
		{
			if (a < 0 || a > 255 || r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255)
				return -1;
			return (int) ((a * 256 * 256 * 256) + (r * 256 * 256) + (g * 256) + b);
		}
		
		/**
		 * Assure qu'une valeur de couleur RGB respecte les bornes.
		 * 
		 * @param c
		 * @return
		 */
		public static int limit(int c)
		{
			return Math.max(0, Math.min(255, c));
		}
		
		/**
		 * Retourne la différence entre deux couleurs.
		 * 
		 * @param rgb1
		 * @param rgb2
		 * @return
		 */
		public static double difference(int rgb1, int rgb2)
		{
			int r1 = RGB.getRed(rgb1),   r2 = RGB.getRed(rgb2);
			int g1 = RGB.getGreen(rgb1), g2 = RGB.getGreen(rgb2);
			int b1 = RGB.getBlue(rgb1),  b2 = RGB.getBlue(rgb2);
			return Math.sqrt(Math.pow((r1 - r2), 2) + Math.pow((g1 - g2), 2) + Math.pow((b1 - b2), 2));
		}
		
	}
	
}