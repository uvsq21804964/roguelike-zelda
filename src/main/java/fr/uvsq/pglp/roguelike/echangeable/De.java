package fr.uvsq.pglp.roguelike.echangeable;

/**
 * Représentation d'un (ensemble de) dé pouvant être tiré pour donner un
 * nombre aléatoire entre 1 et la valeur maximale de celui-ci/ceux_ci.
 *
 * <p>Les dés permettent de donner le nombre de dégâts infligé à l'adversaire
 * quand une {@link Arme} est utilisée.</p>
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
