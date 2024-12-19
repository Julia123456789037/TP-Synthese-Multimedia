package org.multimedia.main;

import javax.swing.SwingUtilities;
import java.awt.image.BufferedImage;
import org.multimedia.metier.*;
import org.multimedia.vue.FramePrinc;

public class Controleur {
	private FramePrinc ihm;
	private Metier  metier;

	// Constructeur
	public Controleur ()
	{
		this.metier = new Metier ();
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

	public void deselectFigure()
	{
		this.metier.deselectFigure();
	}

	public void ajouterFigure(int x, int y, int tx, int ty, char c, BufferedImage bi) {
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

	public void premierPlan() {
		this.metier.premierPlan(this.getIndiceFigure(this.getSelectedFigure()));
	}

	public void ArrierePlan() {
		this.metier.ArrierePlan(this.getIndiceFigure(this.getSelectedFigure()));
	}

	public void PlanAvant() {
		this.metier.planAvant(this.getIndiceFigure(this.getSelectedFigure()));
	}

	public void PlanArriere() {
		this.metier.planArriere(this.getIndiceFigure(this.getSelectedFigure()));
	}
	
}
