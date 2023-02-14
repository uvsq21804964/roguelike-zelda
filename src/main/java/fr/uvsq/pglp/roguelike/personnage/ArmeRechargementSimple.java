package fr.uvsq.pglp.roguelike.personnage;

public enum ArmeRechargementSimple implements ArmeDistance {
	
	ARBALETEPOING("arbalete de poing", new De(6), false, 10.0, 8),
	ARBALETELEGERE("arbalete legere", new De(2, 4), true, 30.0, 10);

	private final String nom;
	private final De de;
	private final boolean deuxMains;
	private final double portee;
	private final int prix;
	private final char glyph = '!';
	
	ArmeRechargementSimple(String nom, De de, boolean deuxMains, double portee, int prix) {
		this.nom = nom;
		this.de = de;
		this.deuxMains = deuxMains;
		this.portee = portee;
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
	public double getPortee() {
		return portee;
	}

	@Override
	public int getPrix() {
		return prix;
	}

	@Override
	public int recharger() {
		return 0;
	}

	@Override
	public char getGlyph() {
		return glyph;
	}

}

