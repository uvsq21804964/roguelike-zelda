package fr.uvsq.pglp.roguelike.echangeable;

public enum ArmureDeCorps implements Armure {
	
	TISSUMATELASSE("tissu matellasse", 1, 2),
	CUIR("cuir", 2, 4),
	CUIRRENFORCE("cuir renforce", 3, 8),
	CHEMISE("chemise de mailles", 4, 15),
	COTTE("cotte de mailles", 5, 20),
	DEMIPLAQUE("demi-plaque", 6, 50),
	PLAQUE("plaque complete", 8, 200);
	
	private final String nom;
	private final int bonusDEF;
	private final int prix;
	private final char glyph = ';';

	ArmureDeCorps(String nom, int bonusDEF, int prix) {
		
		this.nom = nom;
		this.bonusDEF = bonusDEF;
		this.prix = prix;
	}

	@Override
	public String getNom() {
		return nom;
	}

	@Override
	public int getBonusDEF() {
		return bonusDEF;
	}


	@Override
	public int getPrix() {
		return prix;
	}

	@Override
	public char getGlyph() {
		return glyph;
	}

}