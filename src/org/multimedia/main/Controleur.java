package org.multimedia.main;

import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

import org.multimedia.composants.FormeFigure;
import org.multimedia.metier.Figure;
import org.multimedia.metier.Metier;
import org.multimedia.vue.FramePrinc;

public class Controleur {
	
	public final FramePrinc ihm;
	public final Metier  metier;

	// Constructeur
	public Controleur()
	{
		this.metier = new Metier();
		this.ihm    = new FramePrinc(this);

	}

    public FramePrinc getFramePrinc() { return this.ihm; }

	public int getNbFigure() {
		return metier.getNbFigure();
	}

	public Figure getFigure(int num) {
		return metier.getFigure(num);
	}

	public Figure getFigureAtPosition(int x,int y)
	{
		return metier.getFigure(metier.getIndiceFigure(x, y));
	}

	public Integer getIndiceFigure(int x, int y) {
		return metier.getIndiceFigure(x, y);
	}

	public Integer getIndiceFigure(Figure fig) {
		return metier.getIndiceFigure(fig);
	}

	public Figure getSelectedFigure()
	{
		return this.metier.getFigureSelected();
	}

	public void deselectFigure() {
		this.metier.deselectFigure();
	}

	public void ajouterFigure(int x, int y, int tx, int ty, FormeFigure c, BufferedImage bi) {
		this.metier.ajouterFigure(x, y, tx, ty, c, bi);
	}

	public void deplacerFigure(Integer numFigure, int x, int y) {
		this.metier.deplacerFigure(numFigure, x, y);
	}

	public void premierPlan(Integer numFigure) {
		this.metier.premierPlan(numFigure);
	}

	public void ArrierePlan(Integer numFigure) {
		this.metier.ArrierePlan(numFigure);
	}

	public void planAvant(Integer numFigure) { //avance d'un plan
		this.metier.planAvant(numFigure);
	}

	public void planArriere(Integer numFigure) { //recule d'un plan
		this.metier.planArriere(numFigure);
	}

	public static void main(String[] a) {
		SwingUtilities.invokeLater(Controleur::new);
	}

	public void delFigure(Figure selectedFigure) {
		this.metier.delFigure(selectedFigure);
	}
	
}
