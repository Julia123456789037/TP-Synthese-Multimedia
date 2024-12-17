package org.multimedia.vue;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.awt.event.*;

import org.multimedia.main.Controleur;
import org.multimedia.metier.Figure;

public class PanelImage extends JPanel implements ActionListener {
	private Controleur ctrl;
	private BufferedImage image;
	private boolean pipetteMode = false; // Mode pipette activé/désactivé
	private Color couleurSelectionnee = Color.BLACK;
	private Cursor cursorPipette;

	// Bordel de seb !

	private JButton btnPremierPlan, btnArrierePlan, btnAvant, btnArriere;
	private int startX, startY, currentX, currentY;

	private JComboBox JcbFigure;
	private JLabel lblRect, lblOval;
	private char typeSelection;

	private JCheckBox chkCreateMode;

	@SuppressWarnings("unchecked")
	public PanelImage(Controleur ctrl) {
		this.ctrl = ctrl;
		this.image = null;

		// Charger l'icône de pipette personnalisée pour le curseur
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image pipetteImage = toolkit.getImage(getClass().getResource("/pipette.png"));
		Point hotSpot = new Point(0, 0); // Position active de la pipette (pointe)
		this.cursorPipette = toolkit.createCustomCursor(pipetteImage, hotSpot, "Pipette");

		// Ajouter un écouteur de souris pour récupérer la couleur
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (pipetteMode && image != null) {
					pickColor(e.getX(), e.getY());
				}
			}
		});

		this.chkCreateMode = new JCheckBox("Create Figure Mode", false);

		JPanel panelTracer, panelAction;
		this.typeSelection = 'c';

		this.setLayout(new BorderLayout());

		// crÃ©ation des composants;
		panelTracer = new JPanel();
		panelAction = new JPanel(new FlowLayout());

		panelTracer.setOpaque(false);
		panelAction.setOpaque(false);

		this.lblOval = new JLabel("Ovale");
		this.lblRect = new JLabel("Rectangle");

		// initialisation de la JCOMBOBOX
		this.JcbFigure = new JComboBox<>(new JLabel[] { lblOval, lblRect });
		JcbFigure.setRenderer(new ListCellRenderer<Object>() {
			@Override
			public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index,
					boolean isSelected, boolean cellHasFocus) {
				if (value instanceof JLabel) {
					JLabel label = (JLabel) value;
					JLabel displayLabel = new JLabel(label.getText()); // Display only the text
					displayLabel.setOpaque(true);

					// Customize appearance for selection and focus
					if (isSelected) {
						displayLabel.setBackground(list.getSelectionBackground());
						displayLabel.setForeground(list.getSelectionForeground());
					} else {
						displayLabel.setBackground(list.getBackground());
						displayLabel.setForeground(list.getForeground());
					}
					return displayLabel;
				}
				return new JLabel(); // Fallback for unexpected types
			}
		});

		this.btnPremierPlan = new JButton("1er plan");
		this.btnArrierePlan = new JButton("Arrière plan");
		this.btnAvant = new JButton("1 plan avant");
		this.btnArriere = new JButton("1 plan arrière");

		// positionnement des composants
		panelAction.add(this.JcbFigure);
		panelAction.add(this.chkCreateMode);

		panelAction.add(btnPremierPlan);
		panelAction.add(btnArrierePlan);
		panelAction.add(this.btnAvant);
		panelAction.add(this.btnArriere);

		this.add(panelTracer, BorderLayout.CENTER);
		this.add(panelAction, BorderLayout.SOUTH);

		// activation des composants

		
		GereSouris gereSouris = new GereSouris();

		btnPremierPlan.addActionListener(this);
		btnArrierePlan.addActionListener(this);
		this.btnAvant.addActionListener(this);
		this.btnArriere.addActionListener(this);

		this.addMouseListener(gereSouris);
		this.addMouseMotionListener(gereSouris);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		// Dessiner l'image si elle existe
		if (this.image != null) {
			int x = (getWidth() - this.image.getWidth()) / 2; // Centrer l'image horizontalement
			int y = (getHeight() - this.image.getHeight()) / 2; // Centrer l'image verticalement

			// Dessiner l'image avec ses dimensions d'origine
			g.drawImage(this.image, x, y, this);
		}
		// 2. Draw all figures
		for (int i = 0; i < ctrl.getNbFigure(); i++) {
			Figure fig = ctrl.getFigure(i);

			if (fig != null) {
				int left = fig.getCentreX() - fig.getTailleX() / 2;
				int top = fig.getCentreY() - fig.getTailleY() / 2;

				BufferedImage figureImage = fig.getFigureImage();
				if (figureImage != null) {
					g2.drawImage(figureImage, left, top, null);
				}

				if (fig.isSelected()) {
					g2.setColor(this.couleurSelectionnee);
					switch (fig.getType()) {
						case 'r':
							g2.drawRect(left, top, fig.getTailleX(), fig.getTailleY());
							break;

						case 'o':
							g2.drawOval(left, top, fig.getTailleX(), fig.getTailleY());
							break;
					}
				}
			}
		}

		// 3. Draw the preview of the figure being created (if in create mode)
		if (chkCreateMode.isSelected()) {
			int left = Math.min(startX, currentX);
			int top = Math.min(startY, currentY);
			int width = Math.abs(currentX - startX);
			int height = Math.abs(currentY - startY);

			g2.setColor(Color.GRAY);
			g2.setStroke(new BasicStroke(2));

			if (JcbFigure.getSelectedItem() == lblRect) {
				g2.drawRect(left, top, width, height);
			} else if (JcbFigure.getSelectedItem() == lblOval) {
				g2.drawOval(left, top, width, height);
			}
		}
	}

	public void setImage(BufferedImage image) {
		this.image = image;
		repaint();
	}

	public void enablePipetteMode(boolean enable) {
		this.pipetteMode = enable;
		if (enable) {
			setCursor(cursorPipette); // Appliquer le curseur pipette
		} else {
			setCursor(Cursor.getDefaultCursor());
		}
	}

	private void pickColor(int x, int y) {
		// Calculer le décalage de l'image dans le panneau
		int imageX = x - (getWidth() - image.getWidth()) / 2; // Décalage horizontal
		int imageY = y - (getHeight() - image.getHeight()) / 2; // Décalage vertical

		// Vérification que les coordonnées sont dans les limites de l'image
		if (imageX >= 0 && imageY >= 0 && imageX < image.getWidth() && imageY < image.getHeight()) {
			int rgb = image.getRGB(imageX, imageY);
			Color selectedColor = new Color(rgb);

			// Mettre à jour la couleur dans la barre d'outils
			FramePrinc frame = this.ctrl.getFramePrinc();
			if (frame != null) {
				frame.getBarreOutils().setCouleurSelectionnee(selectedColor);
			}
		} else {
			System.out.println("Les coordonnées sont en dehors de l'image.");
		}

		// Désactiver le mode pipette après avoir sélectionné la couleur
		enablePipetteMode(false);
	}

	public Point getImageLocationOnScreen() {
		return this.getLocationOnScreen();
	}

	public void actionPerformed(ActionEvent evt) {

		int x = 0;
		int y = 0;

		if (evt.getSource() == this.btnPremierPlan) {
			// Get the index of the selected figure
			int index = this.ctrl.getIndiceFigure(this.ctrl.getSelectedFigure()); // Replace with your method to get the
																					// selected figure index
			if (index != -1) {
				this.ctrl.premierPlan(index); // Call premierPlan with the figure index
				this.repaint();
			}
		}

		// Handle send to back action
		else if (evt.getSource() == this.btnArrierePlan) {
			// Get the index of the selected figure
			int index = this.ctrl.getIndiceFigure(this.ctrl.getSelectedFigure()); // Replace with your method to get the
																					// selected figure index
			if (index != -1) {
				this.ctrl.ArrierePlan(index); // Call ArrierePlan with the figure index
				this.repaint();
			}
		} else if (evt.getSource() == this.btnArriere) {
			int index = this.ctrl.getIndiceFigure(this.ctrl.getSelectedFigure()); // Replace with your method to get the
																					// selected figure index
			if (index != -1) {
				this.ctrl.planArriere(index); // Call ArrierePlan with the figure index
				this.repaint();
			}
		} else if (evt.getSource() == this.btnAvant) {
			int index = this.ctrl.getIndiceFigure(this.ctrl.getSelectedFigure()); // Replace with your method to get the
																					// selected figure index
			if (index != -1) {
				this.ctrl.planAvant(index); // Call ArrierePlan with the figure index
				this.repaint();
			}
		}
	}

	private class GereSouris extends MouseAdapter {

		private Figure figSelected;

		@Override
		public void mousePressed(MouseEvent e) {
			if (chkCreateMode.isSelected()) {
				startX = e.getX();
				startY = e.getY();
				currentX = startX;
				currentY = startY;
			} else {
				boolean foundFigure = false;
				for (int i = 0; i < PanelImage.this.ctrl.getNbFigure(); i++) {
					Figure fig = PanelImage.this.ctrl.getFigure(i);
					if (fig != null && fig.possede(e.getX(), e.getY())) {
						this.figSelected = fig;
						startX = e.getX();
						startY = e.getY();
						foundFigure = true;
					} else {
						fig.setSelected(false);
					}
				}
				this.figSelected.setSelected(true);

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
			if (chkCreateMode.isSelected()) {
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
			if (chkCreateMode.isSelected()) {
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
				if (JcbFigure.getSelectedItem() == lblRect) {
					PanelImage.this.ctrl.ajouterFigure(x, y, width, height, 'r', image);
				} else if (JcbFigure.getSelectedItem() == lblOval) {
					PanelImage.this.ctrl.ajouterFigure(x, y, width, height, 'o', image);
				}

				chkCreateMode.setSelected(false); // Disable create mode after figure creation
				PanelImage.this.repaint();
			}
		}
	}
}
