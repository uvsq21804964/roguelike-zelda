package fr.uvsq.pglp.roguelike.personnage;

public class Jauge {
	
	private int valeur;
	private int valeurMax;
	
	public Jauge(int depart, int max) {
		this.valeur = depart;
		this.valeurMax = max;
	}
	
	public int valeur() {
		return valeur;
	}
	
	public int valeurMax() {
		return valeurMax;
	}
	
	public float taux() {
		float a = (float) (valeur);
		float b = (float) (valeurMax);
		
		return (float) (a/b);
	}
	
	public boolean pleine() {
		return valeur == valeurMax;
	}
	
	public boolean vide() {
		return valeur == 0;
	}
	
	public int modifier(int gain) {
		
		int ajout = gain; 
		
		if(ajout + valeur > valeurMax) {
			ajout = valeurMax - valeur;
			valeur = valeurMax;
		} else if ( ajout + valeur < 0 ) {
			ajout = valeur;
			valeur = 0;
		} else {
			valeur = valeur + ajout;
		}
		
		return ajout;
	}
	
	public boolean ajoutable(int gain) {
		if(gain + valeur <= valeurMax && gain + valeur >= 0) {
			return true;
		}
		
		return false;
	}

	public int marge () {
		return (valeurMax - valeur);
	}
	
	public int fixerTaux(float tauxVise) {
		float difference = (float) (tauxVise - taux());
		return (int) (difference*valeurMax);
	}
}
