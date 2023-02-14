package fr.uvsq.pglp.roguelike.personnage;

class De {
	
	private final int nbTirage;
	private final int valeurMax;
	
	De(int nbTirage, int valeurMax) {
		this.nbTirage = nbTirage;
		this.valeurMax = valeurMax;
	}
	
	De(int valeurMax) {
		this.nbTirage = 1;
		this.valeurMax = valeurMax;
	}

	public int tirage() {
		
		int sommeTirage = 0;
		for (int i = 0 ; i < nbTirage ; i++) {
			sommeTirage += (int) ((Math.random())*valeurMax) + 1;
		}
		return sommeTirage;
	}
}
