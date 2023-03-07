package fr.uvsq.pglp.roguelike.donjon;

import java.util.ArrayList;

import fr.uvsq.pglp.roguelike.donjon.elements.Salle;
import fr.uvsq.pglp.roguelike.donjon.elements.Couloir;

public abstract class GenerationDonjon {

	private GenerateurDonjon generateurDonjon;
	
	public GenerationDonjon(GenerateurDonjon generationDonjon) {
		this.generateurDonjon = generationDonjon;
	}
	
	public void genererDonjon() {
		generateurDonjon.genererDonjon();
		generateurDonjon.ajouterJoueur();
	}

	public Salle[] getSalles() {
		return generateurDonjon.getSalles();
	}

	public ArrayList<Couloir> getCouloirs() {
		return generateurDonjon.getCouloirs();
	}
	
//	public void SetGenerateurDonjon(GenerateurDonjon typeGenerateur) {
//		this.generateurDonjon = typeGenerateur;
//	}
}