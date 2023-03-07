package fr.uvsq.pglp.roguelike.donjon;

import java.util.ArrayList;

import fr.uvsq.pglp.roguelike.donjon.elements.Couloir;
import fr.uvsq.pglp.roguelike.donjon.elements.ElementEtage;
import fr.uvsq.pglp.roguelike.donjon.elements.Salle;
import fr.uvsq.pglp.roguelike.personnage.Personnage;

public class Donjon implements UniversDeJeu{

	private Salle salles[] = new Salle[9];
	private ArrayList<Couloir> couloirs =  new ArrayList<Couloir>();

	public Donjon() {
		
		GenerationDonjon donjon = new SallesFixesItemsAleatoires();
    donjon.genererDonjon();
		salles = donjon.getSalles();
		couloirs = donjon.getCouloirs();
	}

  public Salle[] getSalles() {
		return salles;
	}

	public ArrayList<Couloir> getCouloirs() {
		return couloirs;
	}

  @Override
  public Personnage getJoueur() {
      for(Salle s : salles) {
        if(s.getJoueur() != null) {
          return s.getJoueur();
        }
      }
      
      for(Couloir c : couloirs) {
        if(c.getJoueur() != null) {
          return c.getJoueur();
        }
      }
      return null;
  }
}
