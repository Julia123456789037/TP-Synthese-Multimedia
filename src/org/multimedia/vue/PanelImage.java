package org.multimedia.vue;

import org.multimedia.composants.FormeFigure;
import org.multimedia.composants.ImageTransform;
import org.multimedia.composants.ModeEdition;
import org.multimedia.main.Controleur;
import org.multimedia.metier.Figure;
import org.multimedia.util.Arithmetique;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.Serial;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class PanelImage extends JPanel {
	protected Controleur     ctrl;
	protected BufferedImage  image;
	protected boolean        pipetteMode = false; // Mode pipette activé/désactivé
	protected Color          couleurSelectionnee = Color.BLACK;
	protected Cursor         cursorPipette;
	
	protected FormeFigure creationFigure = FormeFigure.VIDE;
	
	protected int startX;
	protected int startY;
	protected int currentX;
	protected int currentY;

	protected JComboBox<String> JcbFigure;
	protected JLabel lblRect, lblOval;
	protected char typeSelection;

	@Serial
	private static final long serialVersionUID = 8341091164745892107L;

	private ModeEdition mode;
	private FrameImport sourceFrame;

	final ImageTransform transform;
	public GereSouris gereSouris;

	public PanelImage(Controleur ctrl) {
		this.ctrl = ctrl;
		this.image = null;
		this.setFocusable(true);
		this.transform = new ImageTransform(ctrl);
		this.mode = ModeEdition.NORMAL;

		// Ajouter un écouteur de souris pour récupérer la couleur
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (PanelImage.this.image != null) {
					int x = e.getX(), y = e.getY();
					switch (PanelImage.this.mode) {
						case NORMAL -> {
						}
						case PIPETTE -> pickColor(x, y);
						case POT_DE_PEINTURE -> paintColor(x, y);
						case TEXTE -> paintTexte(x, y);
						default -> { }
					}
				}
			}
		});

		JPanel panelTracer;
		this.typeSelection = 'c';

		this.setLayout(new BorderLayout());

		// crÃ©ation des composants;
		panelTracer = new JPanel();
		panelTracer.setOpaque(false);

		this.lblOval = new JLabel("Ovale");
		this.lblRect = new JLabel("Rectangle");

		// activation des composants

		GereSouris gereSouris = new GereSouris();

		this.addMouseListener(gereSouris);
		this.addMouseMotionListener(gereSouris);

		this.setFocusable(true);
		this.requestFocusInWindow();
	}

	public boolean       isPotPeintureMode()        { return this.mode == ModeEdition.POT_DE_PEINTURE; }
	public boolean       isPipetteMode()            { return this.mode == ModeEdition.PIPETTE; }
	public boolean       isStyloMode()              { return this.mode == ModeEdition.TEXTE; }
	public boolean       isSelectionRectMode()      { return this.mode == ModeEdition.SELECTION_RECT; }
	public boolean       isSelectionRondMode()      { return this.mode == ModeEdition.SELECTION_ROND; }
	public Point         getImageLocationOnScreen() { return this.getLocationOnScreen(); }
	public BufferedImage getImage()                 { return this.image; }
	public Color         getColor()                 { return this.couleurSelectionnee; }
	public void          loadImage( BufferedImage image ) { this.image = image; }
	public void setCreationFigure(FormeFigure c)    { this.creationFigure = c; }

	@SuppressWarnings("incomplete-switch")
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		// Dessiner l'image si elle existe
		if ( this.image != null ) {
			BufferedImage image = this.transform.applyTransforms( this.image );
			int x = ( getWidth()  - image.getWidth()  ) / 2;
			int y = ( getHeight() - image.getHeight() ) / 2;
			g.drawImage(this.drawCheckerBoard(image.getWidth(), image.getHeight()), x, y, this);
			// Dessiner l'image avec ses dimensions d'origine
			g.drawImage(image, x, y, this);
		}

		// 2. Draw all figures
		for (int i = 0; i < ctrl.getNbFigure(); i++) {
			Figure fig = ctrl.getFigure(i);

			if (fig != null) {
				int left = fig.getCentreX() - fig.getTailleX() / 2;
				int top = fig.getCentreY() - fig.getTailleY() / 2;

				BufferedImage figureImage = fig.getFigureImage();

				if (figureImage != null) { g2.drawImage(figureImage, left, top, null); }

				if (fig.isSelected()) {
					g2.setColor(this.couleurSelectionnee);
					switch (fig.getType()) {
						case RECTANGLE -> g2.drawRect(left, top, fig.getTailleX(), fig.getTailleY());
						case OVAL      -> g2.drawOval(left, top, fig.getTailleX(), fig.getTailleY());
					}
				}
			}
		}

		// 3. Draw the preview of the figure being created (if in create mode)
		if (!this.creationFigure.equals(FormeFigure.VIDE)) {
			int left = Math.min(startX, currentX);
			int top = Math.min(startY, currentY);
			int width = Math.abs(currentX - startX);
			int height = Math.abs(currentY - startY);

			g2.setColor(Color.GRAY);
			g2.setStroke(new BasicStroke(2));
			
			switch (this.creationFigure) {
				case RECTANGLE -> g2.drawRect(left, top, width, height);
				case OVAL      -> g2.drawOval(left, top, width, height);
			}
		}
		g.dispose();
	}

	private BufferedImage drawCheckerBoard(int width, int height) {
		BufferedImage res = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		int squareW = Arithmetique.commDiv(width, height) * 4;

		Graphics2D g2d = res.createGraphics();

		final Color darker = new Color(125, 125, 125, 255 / 2);
		final Color brighter = new Color(200, 200, 200, 255 / 2);

		for (int y = 0; y < height / squareW; y++) {
			for (int x = 0; x < width / squareW; x++) {
				g2d.setColor((x + y) % 2 == 0 ? brighter : darker);
				g2d.fillRect(x * squareW, y * squareW, squareW, squareW);
			}
		}
		g2d.dispose();

		return res;
	}

	@Override
	public void updateUI() {
		super.updateUI();
		this.repaint();
	}

	public void enablePipetteMode(boolean enable) {
		this.mode = enable ? ModeEdition.PIPETTE : ModeEdition.NORMAL;
		this.setCursor(this.mode.cursor);
	}

	public void enablePotPeintureMode(boolean enable) {
		this.mode = enable ? ModeEdition.POT_DE_PEINTURE : ModeEdition.NORMAL;
		this.setCursor(this.mode.cursor);
	}

	public void enableStylo(boolean enable) {
		this.mode = enable ? ModeEdition.TEXTE : ModeEdition.NORMAL;
		this.setCursor(this.mode.cursor);
	}

	public void enableSelectionRect(boolean enable) {
		this.mode = enable ? ModeEdition.SELECTION_RECT : ModeEdition.NORMAL;
		PanelImage.this.setCreationFigure(enable ? FormeFigure.RECTANGLE : FormeFigure.VIDE);
		this.setCursor(this.mode.cursor);
	}

	public void enableSelectionRond(boolean enable) {
		this.mode = enable ? ModeEdition.SELECTION_ROND : ModeEdition.NORMAL;
		PanelImage.this.setCreationFigure(enable ? FormeFigure.OVAL : FormeFigure.VIDE);
		this.setCursor(this.mode.cursor);
	}

	public void curseurMode() {
		this.mode = ModeEdition.NORMAL;
		this.setCursor(this.mode.cursor);
	}

	private void pickColor(int x, int y) {
		BufferedImage image = this.transform.applyTransforms(this.image);
		int imageX = x - (getWidth() - image.getWidth()) / 2;
		int imageY = y - (getHeight() - image.getHeight()) / 2;

		// Vérification que les coordonnées sont dans les limites de l'image
		if (imageX >= 0 && imageY >= 0 && imageX < image.getWidth() && imageY < image.getHeight()) {
			int rgb = image.getRGB(imageX, imageY);
			Color selectedColor = new Color(rgb);

			// Mettre à jour la couleur dans la barre d'outils
			FramePrinc frame = this.ctrl.getFramePrinc();
			if (frame != null) { frame.getBarreOutils().setCouleurSelectionnee(selectedColor); }
		} else { System.out.println("Les coordonnées sont en dehors de l'image."); }

		// Désactiver le mode pipette après avoir sélectionné la couleur
		enablePipetteMode(false);
	}

	private synchronized void paintColor(int x, int y) {
		BufferedImage image = this.transform.applyTransforms(this.image);

		// Calculer la position de l'image dans le panneau
		int imageX = x - (getWidth() - image.getWidth()) / 2; // Décalage horizontal
		int imageY = y - (getHeight() - image.getHeight()) / 2; // Décalage vertical

		// Vérification que les coordonnées sont dans les limites de l'image
		if (imageX >= 0 && imageY >= 0 && imageX < image.getWidth() && imageY < image.getHeight()) {
			FramePrinc frame = this.ctrl.getFramePrinc();
			if (frame != null) { frame.potPeint(imageX, imageY); }
		} else { System.out.println("Les coordonnées sont en dehors de l'image."); }
	}

	public synchronized void paintTransp() {
		FramePrinc frame = this.ctrl.getFramePrinc();
		if (frame != null) { frame.transp(); }
	}

	private synchronized void paintTexte(int x, int y) {
		BufferedImage image = this.transform.applyTransforms(this.image);

		// Calculer la position de l'image dans le panneau
		int imageX = x - (getWidth() - image.getWidth()) / 2; // Décalage horizontal
		int imageY = y - (getHeight() - image.getHeight()) / 2; // Décalage vertical

		// Vérification que les coordonnées sont dans les limites de l'image
		if (imageX >= 0 && imageY >= 0 && imageX < image.getWidth() && imageY < image.getHeight()) {
			FramePrinc frame = this.ctrl.getFramePrinc();
			if (frame != null) { frame.writeText(imageX, imageY); }
		} else { System.out.println("Les coordonnées sont en dehors de l'image."); }
	}

	public void openSourcePanel() {
		sourceFrame = new FrameImport(this.ctrl);
		sourceFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		sourceFrame.setVisible(true);
	}

	public void premPlan() {
		int index = this.ctrl.getIndiceFigure(this.ctrl.getSelectedFigure());
		if (index != -1) {
			this.ctrl.premierPlan(index); // Call premierPlan with the figure index
			this.repaint();
		}
	}

	public void avanverUnPlan() {
		int index = this.ctrl.getIndiceFigure(this.ctrl.getSelectedFigure());
		if (index != -1) {
			this.ctrl.planAvant(index);
			this.repaint();
		}
	}

	public void reculerUnPlan() {
		int index = this.ctrl.getIndiceFigure(this.ctrl.getSelectedFigure());
		if (index != -1) {
			this.ctrl.planArriere(index);
			this.repaint();
		}
	}

	public void dernPlan() {
		int index = this.ctrl.getIndiceFigure(this.ctrl.getSelectedFigure());
		if (index != -1) {
			this.ctrl.ArrierePlan(index);
			this.repaint();
		}
    }

	public void saveImageWithOverlap() {
		BufferedImage image = this.transform.applyTransforms(this.image);
		
		int imageX = (getWidth() - image.getWidth()) / 2;
		int imageY = (getHeight() - image.getHeight()) / 2;
		Rectangle imageBounds = new Rectangle(imageX, imageY, image.getWidth(), image.getHeight());

		for (int i = 0; i < this.ctrl.getNbFigure(); i++) {
			Figure figure = this.ctrl.getFigure(i);

			// Figure bounds
			int figureLeft = figure.getCentreX() - figure.getTailleX() / 2;
			int figureTop = figure.getCentreY() - figure.getTailleY() / 2;
			Rectangle figureBounds = new Rectangle(figureLeft, figureTop, figure.getTailleX(), figure.getTailleY());

			// Intersection between figure and image
			Rectangle intersection = figureBounds.intersection(imageBounds);

			if (!intersection.isEmpty()) {
				// Modify image pixels
				this.transform.drawCalc(intersection, imageX, imageY, figure, figureLeft, figureTop);
			}
		}
	}

	private class GereSouris extends MouseAdapter {

		private Figure figSelected;

		@Override
		public void mousePressed(MouseEvent e) {
			if (!creationFigure.equals(FormeFigure.VIDE)) {
				startX = currentX = e.getX();
				startY = currentY = e.getY();
			} else {
				boolean foundFigure = false;
				for (int i = 0; i < PanelImage.this.ctrl.getNbFigure(); i++) {
					Figure fig = PanelImage.this.ctrl.getFigure(i);
					if (fig != null && fig.possede(e.getX(), e.getY())) {
						this.figSelected = fig;
						startX = e.getX();
						startY = e.getY();
						foundFigure = true;
					} 
				}
				if (this.figSelected != null) { this.figSelected.setSelected(true); }

				for (int i = 0; i < PanelImage.this.ctrl.getNbFigure(); i++) {
					if (!PanelImage.this.ctrl.getFigure(i).equals(this.figSelected))
					{
						PanelImage.this.ctrl.getFigure(i).setSelected(false);
					}
				}
				
				if (!foundFigure) {
					for (int i = 0; i < PanelImage.this.ctrl.getNbFigure(); i++) {
						PanelImage.this.ctrl.getFigure(i).setSelected(false);
					}
					this.figSelected = null;
				}
				PanelImage.this.repaint();
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (!creationFigure.equals(FormeFigure.VIDE)) {
				currentX = e.getX();
				currentY = e.getY();
				PanelImage.this.repaint(); // Repaint to update the preview
			} else if (this.figSelected != null) {
				int deltaX = e.getX() - startX;
				int deltaY = e.getY() - startY;
				PanelImage.this.ctrl.deplacerFigure(PanelImage.this.ctrl.getIndiceFigure(this.figSelected), deltaX,
						deltaY);
				startX = e.getX();
				startY = e.getY();
				PanelImage.this.repaint();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (!creationFigure.equals(FormeFigure.VIDE)) {
				BufferedImage image = PanelImage.this.transform.applyTransforms(PanelImage.this.image);
				
				// Calculate the width and height of the figure
				int width = Math.abs(currentX - startX);
				int height = Math.abs(currentY - startY);

				// Calculate the center coordinates of the figure relative to the image
				int x = (startX + currentX) / 2;
				int y = (startY + currentY) / 2;

				// Calculate the offset due to the centering of the image
				int offsetX = (getWidth() - image.getWidth()) / 2;
				int offsetY = (getHeight() - image.getHeight()) / 2;

				// Adjust the coordinates to account for the image offset
				x -= offsetX;
				y -= offsetY;

				// Create the figure based on the selected type

				PanelImage.this.ctrl.ajouterFigure(x, y, width, height, creationFigure, image);

				PanelImage.this.enableSelectionRect(false);
				PanelImage.this.enableSelectionRond(false);
				PanelImage.this.setCreationFigure(FormeFigure.VIDE);
				PanelImage.this.repaint();
			}
		}
	}
}
