package fr.uvsq.pglp.roguelike.personnage;

public enum ArmeContact implements Arme {
	
	BATON("baton", new De(4), true, 0),
	BATONFERRE("baton ferre", new De(4), true, 2),
	DAGUE("dague", new De(4), false, 3),
	EPEE2MAINS("epee a deux mains", new De(2, 6), true, 10),
	EPEECOURTE("epee courte", new De(6), false, 5),
	EPEELONGUE("epee longue", new De(8), false, 6),
	GOURDIN("gourdin", new De(4), false, 1),
	HACHE("hache", new De(8), false, 6),
	HACHE2MAINS("hache a deux mains", new De(2, 6), true, 10),
	KATANA("katana", new De(10), true, 12),
	MARTEAUDEGUERRE("marteau de guerre", new De(4), false, 12),
	MASSEDARMES("masse d'armes", new De(6), false, 4),
	RAPIERE("rapiere", new De(6), false, 6),
	VIVELAME("vivelame", new De(10), true, 12);

	private final String nom;
	private final De de;
	private final boolean deuxMains;
	private final int prix;
	private final char glyph = ':';
	
	ArmeContact(String nom, De de, boolean deuxMains, int prix) {
		this.nom = nom;
		this.de = de;
		this.deuxMains = deuxMains;
		this.prix = prix;
	}

	@Override
	public String getNom() {
		return nom;
	}
	
	@Override
	public int getDe() {
		return de.tirage();
	}

	@Override
	public boolean isDeuxMains() {
		return deuxMains;
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
