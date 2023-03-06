package fr.uvsq.pglp.roguelike.donjon.elements;

import fr.uvsq.pglp.roguelike.donjon.MorceauEtage;

public abstract class Salle extends MorceauEtage {

	protected int numero;
	
	public Salle(int numero, int largeur, int longueur, int nbEquipement, int nbEnnemis) {
		super(largeur, longueur, nbEquipement, nbEnnemis);
		this.numero = numero;
		ajouterTresor();
		// ajouterEnnemis();
		// ajouterEquipements();
	}
	
	private void ajouterTresor()  {

		int a = (int) (Math.random()*100);
		
		int nbTresors = 0;

		if (a < 1) {
			nbTresors = 3;
		} else if (a < 5) {
			nbTresors = 2;
		} else if(a < 30) {
			nbTresors = 1;
		}

		while (nbTresors > 0) {
			int x = (int) (Math.random()*tiles.length);
			int y = (int) (Math.random()*tiles[0].length);
			if (tiles[x][y].equals(Tile.FLOOR)) {

				switch((int) (Math.random()*2)) {
					case 0 :
						tiles[x][y] = Tile.CHEST;
						break;
					case 1 :
						tiles[x][y] = Tile.BOX;
						break;
				}
			}
			nbTresors--;
		}
	}

	@Override
	public void ajouterPorte(Porte porte) {

		Couloir c = (Couloir) porte.getCouloir();
		
		if(c.numeroSalle1() == numero + 1 || c.numeroSalle2() == numero + 1) {
			int a = (int) (Math.random()*(tiles[0].length - 2)) + 1;
			tiles[tiles.length - 1][a] = porte.getType();
			portes[tiles.length - 1][a] = porte;
		} else if(c.numeroSalle1() == numero - 1 || c.numeroSalle2() == numero - 1) {
			int a = (int) (Math.random()*(tiles[0].length - 2)) + 1;
			tiles[0][a] = porte.getType();
			portes[0][a] = porte;
		} else if(c.numeroSalle1() == numero + 3 || c.numeroSalle2() == numero + 3) {
			int a = (int) (Math.random()*(tiles.length - 2)) + 1;
			tiles[a][tiles[0].length - 1] = porte.getType();
			portes[a][tiles[0].length - 1] = porte;
		} else if(c.numeroSalle1() == numero - 3 || c.numeroSalle2() == numero - 3) {
			int a = (int) (Math.random()*(tiles.length - 2)) + 1;
			tiles[a][0] = porte.getType();
			portes[a][0] = porte;
		}
	}
}
