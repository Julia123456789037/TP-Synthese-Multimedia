package org.multimedia.metier;

import java.util.List;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class Metier {
	private List<Figure> lstFigure;

	public Metier() {
		this.lstFigure = new ArrayList<Figure>();
	}

	
	public void ajouterFigure(int x, int y, int tx, int ty, char c, BufferedImage bi) {
		System.out.println("Adding Figure: ");
		if (c == 'r') {
			this.lstFigure.add(new Rectangle(x, y, tx, ty, c, bi));
		}
		if (c == 'o') {
			this.lstFigure.add(new Ovale(x, y, tx, ty, c, bi));
		}
	}

	public int getNbFigure() {
		return this.lstFigure.size();
	}

	public Figure getFigure(int num) {
		
		return this.lstFigure.get(num);
	}

	public void premierPlan(int n) {
		if (n < 0 || n >= lstFigure.size()) {
			return;
		}

		Figure figure = this.lstFigure.remove(n);
		this.lstFigure.add(figure);
	}

	public void ArrierePlan(int n) {
		if (n < 0 || n >= lstFigure.size()) {
			return;
		}

		Figure figure = this.lstFigure.remove(n);
		this.lstFigure.add(0, figure);

	}

	public void planArriere(int n) {
		if (n < 0 || n >= lstFigure.size()) {
			return;
		}

		Collections.swap(lstFigure, n, n - 1);

	}

	public void planAvant(int n) {
		if (n < 0 || n >= lstFigure.size()) {
			return;
		}
		Collections.swap(lstFigure, n, n + 1);

	}

	
	public Integer getIndiceFigure(int x, int y) {
		for (int cpt = 0; cpt < this.lstFigure.size(); cpt++)
			if (this.lstFigure.get(cpt).possede(x, y))
				return cpt;

		return null;
	}
	
	public Integer getIndiceFigure(Figure fig) {
		return this.lstFigure.indexOf(fig);
	}

	public Figure getFigureSelected() {
		for (Figure fig : lstFigure) {
			if (fig.isSelected())
				return fig;
		}
		return null;
	}
	
	public void deselectFigure(){
		for (Figure fig : lstFigure) {
			fig.setSelected(false);
		}
	}

	

	public void deplacerFigure(Integer numFigure, int x, int y) {
		if (numFigure != null && numFigure >= 0 && numFigure < this.lstFigure.size()) {
			this.lstFigure.get(numFigure).deplacerX(x);
			this.lstFigure.get(numFigure).deplacerY(y);
		}
	}

	public void delFigure(Figure selectedFigure) {
		this.lstFigure.remove(selectedFigure);
	}

}