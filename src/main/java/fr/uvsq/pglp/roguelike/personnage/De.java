package fr.uvsq.pglp.roguelike.personnage;

/**
 * Représentation d'un (ensemble de) dé pouvant être tiré pour donner un 
 * nombre aléatoire entre 1 et la valeur maximale de celui-ci/ceux-ci.
 * 
 * <p>Après une nuit de repos, un {@link PersonnageDonjon} blessé récupère
 * un nombre de PV égal au jet de son Dé de vie auquel s’ajoute le Mod. de 
 * CON du {@link PersonnageDonjon}.</p>
 *
 * @author Tom Abbouz
 * @version Février 2023
 */
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
    for (int i = 0; i < nbTirage; i++) {
      sommeTirage += (int) ((Math.random()) * valeurMax) + 1;
    }
    return sommeTirage;
  }
}
