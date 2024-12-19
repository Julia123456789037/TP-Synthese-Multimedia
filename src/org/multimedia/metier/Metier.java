package org.multimedia.metier;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.multimedia.composants.FormeFigure;

public class Metier {
	
	private List<Figure> lstFigure;

	public Metier() {
		this.lstFigure = new ArrayList<>();
	}

	
	public void ajouterFigure(int x, int y, int tx, int ty, FormeFigure c, BufferedImage bi) {
		System.out.println("Adding Figure: ");
		this.lstFigure.add(switch (c) {
			case RECTANGLE -> new Rectangle(x, y, tx, ty, c, bi);
			case OVAL      -> new Ovale(x, y, tx, ty, c, bi);
			default        -> null;
		});
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
		if (n < 1 || n >= lstFigure.size()) {
			return;
		}

		Collections.swap(lstFigure, n, n - 1);

	}

	public void planAvant(int n) {
		if (n < 0 || n > lstFigure.size() - 1)
			return;
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