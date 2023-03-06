package fr.uvsq.pglp.roguelike.donjon.elements;

public class SalleAleatoire extends Salle {
	
	public SalleAleatoire(int numero, int largeur, int longueur) {
		super(numero, largeur, longueur, (int) (Math.random()*5) + 1, (int) (Math.random()*2) + 1);
	}
}
