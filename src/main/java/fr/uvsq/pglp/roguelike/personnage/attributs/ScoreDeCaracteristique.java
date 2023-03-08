package fr.uvsq.pglp.roguelike.personnage.attributs;

/**
 * Représente une caractéristique avec sa valeur et son mod.
 *
 * @author Tom Abbouz
 * @version Février 2023
 */
public final class ScoreDeCaracteristique {

  private final Caracteristique caracteristique;
  private final int valeur;
  private final int modificateurDeCaracteristique;

  /**
   * Constructeur de {@link ScoreDeCaracteristique}.
   *
   * @param caracteristique La caracteristique en question
   * @param valeur La valeur que l'on veut attribuer (devant appartenir à [1,21]).
   */
  public ScoreDeCaracteristique(Caracteristique caracteristique, int valeur) {

    if (valeur < 1 || valeur > 21) {
      throw new IllegalArgumentException("La valeur d'un score de caractéristique "
          + "doit appartenir à l'intervalle [1,21]. Valeur reçue : " + valeur);
    }

    this.caracteristique = caracteristique;
    this.valeur = valeur;
    this.modificateurDeCaracteristique = calculerModifDeCar(valeur);
  }

  private int calculerModifDeCar(int valeurCar) {

    switch (valeurCar) {
      case 1:
      case 2:
      case 3:
        return -4;
      case 4:
      case 5:
        return -3;
      case 6:
      case 7:
        return -2;
      case 8:
      case 9:
        return -1;
      case 10:
      case 11:
        return 0;
      case 12:
      case 13:
        return 1;
      case 14:
      case 15:
        return 2;
      case 16:
      case 17:
        return 3;
      case 18:
      case 19:
        return 4;
      default:
        return 5;
    }
  }

  public Caracteristique type() {
    return caracteristique;
  }

  public String nom() {
    return caracteristique.getNom();
  }

  public int val() {
    return valeur;
  }

  public int mod() {
    return modificateurDeCaracteristique;
  }
}
