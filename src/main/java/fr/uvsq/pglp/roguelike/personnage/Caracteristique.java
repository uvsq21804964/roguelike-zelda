package fr.uvsq.pglp.roguelike.personnage;

public enum Caracteristique {
	
	FOR("Force"),
	DEX("Dexterite"),
	CON("Constitution"),
	INT("Intelligence"),
	SAG("Sagesse"),
	CHA("Charisme");
	
	private String nom;
	
	private Caracteristique(String nomCaracteristique) {
		this.nom = nomCaracteristique;
	}
	
	/**
	 * Getters
	 * @return
	 */
	public String getNom() {
		return nom;
	}
}
