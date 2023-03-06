package fr.uvsq.pglp.roguelike.donjon;

import java.util.ArrayList;

import fr.uvsq.pglp.roguelike.donjon.elements.Couloir;
import fr.uvsq.pglp.roguelike.donjon.elements.Salle;

public class Donjon implements UniversDeJeu{

	private Salle salles[] = new Salle[9];
	private ArrayList<Couloir> couloirs =  new ArrayList<Couloir>();

	public Donjon() {
		
		GenerationDonjon donjon = new SallesFixesItemsAleatoires();
    donjon.genererDonjon();
		salles = donjon.getSalles();
		couloirs = donjon.getCouloirs();
		ajouterJoueur();
	}

	public void ajouterJoueur() {
    salles[0].ajouterJoueur();
  }

  public Salle[] getSalles() {
		return salles;
	}

	public ArrayList<Couloir> getCouloirs() {
		return couloirs;
	}
}
