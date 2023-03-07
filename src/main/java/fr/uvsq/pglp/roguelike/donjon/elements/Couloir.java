package fr.uvsq.pglp.roguelike.donjon.elements;

import fr.uvsq.pglp.roguelike.donjon.MorceauEtage;

public abstract class Couloir extends MorceauEtage {

	protected Salle salle1;
	protected Salle salle2;
	
	public Couloir(Salle salle1, Salle salle2, int nbEquipement, int nbEnnemis, int largeur, int longueur) {
		super(largeur, longueur, nbEquipement, nbEnnemis);
		this.salle1 = salle1;
		this.salle2 = salle2;
	}

	@Override 
	public void ajouterPorte(Porte porte) {

		if(porte.getSalle().numero == Math.max(salle1.numero, salle2.numero)) {
			if (largeur > longueur) {
				int a = (int) (Math.random()*(tiles[0].length - 2)) + 1;
				tiles[tiles.length - 1][a] = porte.getType();
				ouvrables[tiles.length - 1][a] = porte;
			} else {
				int a = (int) (Math.random()*(tiles.length - 2)) + 1;
				tiles[a][tiles[0].length - 1] = porte.getType();
				ouvrables[a][tiles[0].length - 1] = porte;
			}
		} else {
			if (largeur > longueur) {
				int a = (int) (Math.random()*(tiles[0].length - 2)) + 1;
				tiles[0][a] = porte.getType();
				ouvrables[0][a] = porte;
			} else {
				int a = (int) (Math.random()*(tiles.length - 2)) + 1;
				tiles[a][0] = porte.getType();
				ouvrables[a][0] = porte;
			}
		}
	}

	int numeroSalle1() {
		return salle1.numero;
	}

	int numeroSalle2() {
		return salle2.numero;
	}
}
