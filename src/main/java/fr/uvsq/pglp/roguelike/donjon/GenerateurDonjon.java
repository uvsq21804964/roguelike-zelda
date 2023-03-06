package fr.uvsq.pglp.roguelike.donjon;

import java.util.ArrayList;

import fr.uvsq.pglp.roguelike.donjon.elements.Couloir;
import fr.uvsq.pglp.roguelike.donjon.elements.Salle;

public interface GenerateurDonjon {
	public void genererDonjon();

	public Salle[] getSalles();

	public ArrayList<Couloir> getCouloirs();
	
	void ajouterJoueur();
}
