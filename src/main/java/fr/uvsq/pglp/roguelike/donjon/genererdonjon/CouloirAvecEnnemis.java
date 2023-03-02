package fr.uvsq.pglp.roguelike.donjon.genererdonjon;

/**
 * Cette classe représente un {@link Couloir} avec un certain nombre de PNJ, 
 * et un nombre d'équipements toujours nulle.
 * Elle permet de relier deux {@link Salle}s.
 *
 * @see Couloir
 * 
 * @author Tom Abbouz
 *
 */
public class CouloirAvecEnnemis extends Couloir {

  /**
   * Constructeur de {@link CouloirAvecEnnemis}.
   * Utilise le constructeur de {@link Couloir} avec nbEquipements=0.
   *
   * @param salle1 La première salle que le couloir relie.
   * @param salle2 La deuxième salle que le couloir relie.
   * @param nbPnj Le nombre maximum de PNJ (ennemis ou non ennemis) 
   *        que l'on peut trouver au maximum dans le couloir.
   *
   * @see Couloir#Couloir(Salle, Salle, int, int)
   * 
   */
  public CouloirAvecEnnemis(Salle salle1, Salle salle2, int nbPnj) {
    super(salle1, salle2, 0, nbPnj);
  }
}
