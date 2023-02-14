package fr.uvsq.pglp.roguelike.personnage;

enum Bouclier implements Armure {
	
	PETITBOUCLIER("petit bouclier", 1, 2),
	GRANDBOUCLIER("grand bouclier", 2, 4);
	
	private final String nom;
	private final int bonusDEF;
	private final int prix;
	private final char glyph = ',';

	Bouclier(String nom, int bonusDEF, int prix) {
		
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