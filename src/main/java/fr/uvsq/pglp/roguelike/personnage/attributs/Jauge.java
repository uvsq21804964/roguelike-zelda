package fr.uvsq.pglp.roguelike.personnage.attributs;

/**
 * Représentation d'une jauge.
 *
 * <p>Utilisé notamment pour représenter les points de vie des
 * {@link PersonnageDonjon}s.</p>
 *
 * @author Tom Abbouz
 * @version Février 2023
 */
public class Jauge {

  private int valeur;
  private int valeurMax;

  public Jauge(int depart, int max) {
    this.valeur = depart;
    this.valeurMax = max;
  }

  public int valeur() {
    return valeur;
  }

  public int valeurMax() {
    return valeurMax;
  }

  /**
   * Retourne le taux de complétion de la jauge.
   *
   * @return un float indiquant le taux de complétion de la jauge.
   */
  public float taux() {
    float a = (float) (valeur);
    float b = (float) (valeurMax);

    return (float) (a / b);
  }

  public boolean pleine() {
    return valeur == valeurMax;
  }

  public boolean vide() {
    return valeur == 0;
  }

  /**
   * Modifier la valeur de la jauge.
   * 
   * <p>La jauge peut-être augmentée ou diminuée, mais sans modifier
   * sa valeur maximum. Ainsi, on ne peut avoir une jauge dont la valeur est 
   * négative ou supérieure à la valeur maximale de celle-ci.</p>
   * 
   * <p>Elle prend une valeur que l'on souhaite ajoutée à la jauge,
   * et retourne la valeur vraiment ajoutée à la jauge.</p>
   *
   * @param gain La valeur dont l'on <b>souhaite</b> modifier la jauge.
   * @return La valeur <b>réellement</b> ajoutée à la jauge.
   */
  public int modifier(int gain) {

    int ajout = gain;

    if (ajout + valeur > valeurMax) {
      ajout = valeurMax - valeur;
      valeur = valeurMax;
    } else if (ajout + valeur < 0) {
      ajout = valeur;
      valeur = 0;
    } else {
      valeur = valeur + ajout;
    }

    return ajout;
  }

  /**
   * Retourne <b>true</b> si la valeur en entrée peut être ajoutée à la jauge
   * sans la faire déborder ou la rendre négative.
   *
   * @param gain La valeur (int) que l'on souhaite ajoutée à la jauge.
   * @return <b>true</b> si la jauge peut ajoutée la valeur renseignée. 
   */
  public boolean ajoutable(int gain) {
    if (gain + valeur <= valeurMax && gain + valeur >= 0) {
      return true;
    }

    return false;
  }

  public int marge() {
    return (valeurMax - valeur);
  }

  public int fixerTaux(float tauxVise) {
    float difference = (float) (tauxVise - taux());
    return (int) (difference * valeurMax);
  }
}
