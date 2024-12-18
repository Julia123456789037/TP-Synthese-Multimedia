package org.multimedia.util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serial;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Classe utilitaire pour afficher des images dans des fenêtres popup.
 * 
 * <br><br>
 * <b>Date :</b> 14/12/2024
 * @author Gabriel Roche
 */
public class DisplayImage
{
	
	public static final int MAX_WIDTH;
	public static final int MAX_HEIGHT;
	
	/**
	 * Initialisation des constantes avec les dimensions réelles de l'écran.
	 */
	static
	{
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		MAX_WIDTH  = (int) d.getWidth();
		MAX_HEIGHT = (int) d.getHeight();
	}
	
	/**
	 * Constructeur, ne pas instancier.
	 */
	private DisplayImage() {}
	
	/**
	 * Affiche une image.
	 * 
	 * @param stream
	 * @throws IOException
	 */
	public static void show(InputStream stream) throws IOException
	{
		DisplayImage.show(ImageIO.read(stream), JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Affiche une image.
	 * 
	 * @param stream
	 * @param exitMode
	 * @throws IOException
	 */
	public static void show(InputStream stream, int exitMode) throws IOException
	{
		DisplayImage.show(ImageIO.read(stream), null, exitMode);
	}
	
	/**
	 * Affiche une image.
	 * 
	 * @param stream
	 * @param title
	 * @throws IOException
	 */
	public static void show(InputStream stream, String title) throws IOException
	{
		DisplayImage.show(ImageIO.read(stream), title, JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Affiche une image.
	 * 
	 * @param stream
	 * @param title
	 * @param exitMode
	 * @throws IOException
	 */
	public static void show(InputStream stream, String title, int exitMode) throws IOException
	{
		DisplayImage.show(ImageIO.read(stream), title, exitMode);
	}
	
	/**
	 * Affiche une image.
	 * 
	 * @param image
	 * @throws IOException
	 */
	public static void show(File image) throws IOException
	{
		DisplayImage.show(ImageIO.read(image), JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Affiche une image.
	 * 
	 * @param image
	 * @param exitMode
	 * @throws IOException
	 */
	public static void show(File image, int exitMode) throws IOException
	{
		DisplayImage.show(ImageIO.read(image), null, exitMode);
	}
	
	/**
	 * Affiche une image.
	 * 
	 * @param image
	 * @param title
	 * @throws IOException
	 */
	public static void show(File image, String title) throws IOException
	{
		DisplayImage.show(ImageIO.read(image), title, JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Affiche une image.
	 * 
	 * @param image
	 * @param title
	 * @param exitMode
	 * @throws IOException
	 */
	public static void show(File image, String title, int exitMode) throws IOException
	{
		DisplayImage.show(ImageIO.read(image), title, exitMode);
	}
	
	/**
	 * Affiche une image.
	 * 
	 * @param img
	 */
	public static void show(BufferedImage img)
	{
		DisplayImage.show(new ImageIcon(img), JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Affiche une image.
	 * 
	 * @param img
	 * @param exitMode
	 */
	public static void show(BufferedImage img, int exitMode)
	{
		DisplayImage.show(new ImageIcon(img), null, exitMode);
	}
	
	/**
	 * Affiche une image et définit le titre de la page.
	 * 
	 * @param img
	 * @param title
	 */
	public static void show(BufferedImage img, String title)
	{
		DisplayImage.show(new ImageIcon(img), title);
	}
	
	/**
	 * Affiche une image et définit le titre de la page.
	 * 
	 * @param img
	 * @param title
	 * @param exitMode
	 */
	public static void show(BufferedImage img, String title, int exitMode)
	{
		DisplayImage.show(new ImageIcon(img), title, exitMode);
	}
	
	/**
	 * Affiche une image.
	 * 
	 * @param icon
	 */
	public static void show(ImageIcon icon)
	{
		DisplayImage.show(icon, JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Affiche une image.
	 * 
	 * @param icon
	 * @param exitMode
	 */
	public static void show(ImageIcon icon, int exitMode)
	{
		DisplayImage.show(icon, null, exitMode);
	}
	
	/**
	 * Affiche une image et définit le titre de la page.
	 * 
	 * @param icon
	 * @param title
	 */
	public static void show(ImageIcon icon, String title)
	{
		DisplayImage.show(icon, title, JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Affiche une image et définit le titre de la page.
	 * 
	 * @param icon
	 * @param title
	 * @param exitMode
	 */
	public static void show(ImageIcon icon, String title, int exitMode)
	{
		DisplayImage.openFrame0(icon, title, exitMode);
	}
	
	/**
	 * Méthode privée pour créer l'interface.
	 * 
	 * @param icon
	 * @param title
	 */
	private static void openFrame0(ImageIcon icon, String title, int exitMode)
	{
		SwingUtilities.invokeLater(() ->
		{
			JFrame frame = new JFrame(title);
			frame.setLayout(new BorderLayout());
			frame.setSize(Math.min(icon.getIconWidth(), MAX_WIDTH), Math.min(icon.getIconHeight(), MAX_HEIGHT));
			ImagePanel panel = new ImagePanel(icon.getImage(), frame.getWidth(), frame.getHeight(), true);
			frame.add(BorderLayout.CENTER, panel);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(exitMode);
		});
	}
	
	/**
	 * Classe qui hérite de {@link JPanel} pour contenir l'image, la centrer et ajouter des fonctionnalitées tel que le zoom.
	 * 
	 * TODO: Fix zoom function
	 * 
	 * @author  Gabriel Roche
	 */
	public static class ImagePanel extends JPanel
	{
		
		@Serial
		private static final long serialVersionUID = -5936122733113187863L;
		
		private AffineTransform zoom;
		
		private Image image;
		
		private int width;
		private int height;
		
		/**
		 * Constructeur.
		 * 
		 * @param image
		 * @param width
		 * @param height
		 */
		public ImagePanel(Image image, int width, int height)
		{
			this(image, width, height, false);
		}
		
		/**
		 * Constructeur.
		 * 
		 * @param image
		 * @param width
		 * @param height
		 * @param scrollReload
		 */
		public ImagePanel(Image image, int width, int height, boolean scrollReload)
		{
			this.image  = image;
			this.width  = width;
			this.height = height;
			this.zoom   = new AffineTransform();
			this.setSize(MAX_WIDTH, MAX_HEIGHT);
			if (scrollReload) {
				this.addMouseWheelListener(e ->
				{
					this.zoom.setToTranslation(0, 0);
					if (e.getPreciseWheelRotation() >= 0)
					{
						if (this.zoom.getScaleX() < 10.0)
						{
							if (e.isShiftDown())
								this.zoom.scale(1.5, 1.5);
							else
								this.zoom.scale(2, 2);
						}
					} else
					{
						if (this.zoom.getScaleX() > 0.125)
						{
							if (e.isShiftDown())
								this.zoom.scale(0.75, 0.75);
							else
								this.zoom.scale(0.5, 0.5);
						}
					}
					if (this.getScaledWidth() < this.getWidth() && this.getScaledHeight() < this.getHeight())
					{
						int widthGap  = this.getWidth()  - this.getScaledWidth();
						int heightGap = this.getHeight() - this.getScaledHeight();
						this.zoom.translate((widthGap / 2) + this.getScaledWidth() / 2, (heightGap / 2) + this.getScaledHeight() / 2);
						this.zoom.translate(-(widthGap / 2), -(heightGap / 2));
					}
					this.updateUI();
				});
			}
		}
		
		/**
		 * On dessine l'image dans la méthode paintComponent.
		 * <br><br>
		 * {@inheritDoc}
		 */
		@Override
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			if (this.image != null) {
				g2d.drawImage(this.image, this.zoom, this);
			}
			g2d.dispose();
		}
		
		/**
		 * Retourne la largeur de l'image avec le zoom.
		 * 
		 * @return
		 */
		public int getScaledWidth()
		{
			return (int) (this.zoom.getScaleX() * this.image.getWidth(null));
		}
		
		/**
		 * Retourne la hauteur de l'image avec le zoom.
		 * 
		 * @return
		 */
		public int getScaledHeight()
		{
			return (int) (this.zoom.getScaleY() * this.image.getHeight(null));
		}
		
		/**
		 * Retourne la largeur du composant.
		 */
		@Override
		public int getWidth()
		{
			return Math.max(this.width, getScaledWidth());
		}
		
		/**
		 * Retourne la hauteur du composant.
		 */
		@Override
		public int getHeight()
		{
			return Math.max(this.height, getScaledHeight());
		}
		
	}
    
}
