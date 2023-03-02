package fr.uvsq.pglp.roguelike.donjon.genererdonjon;

/**
 * Cette classe représente un {@link Couloir} avec un certain nombre d'équipements, 
 * et un nombre de PNJ toujours nulle.
 * Elle permet de relier deux {@link Salle}s.
 *
 * @see Couloir
 *
 * @author Tom Abbouz
 *
 */
public class CouloirAvecEquipements extends Couloir {

  /**
   * Constructeur de {@link CouloirAvecEquipements}.
   * Utilise le constructeur de {@link Couloir} avec nbPnj=0.
   *
   * @param salle1 La première salle que le couloir relie.
   * @param salle2 La deuxième salle que le couloir relie.
   * @param nbEquipement Le nombre maximum d'équipement 
   *        que l'on peut trouver au maximum dans le couloir.
   *
   * @see Couloir#Couloir(Salle, Salle, int, int)
   * 
   */
  public CouloirAvecEquipements(Salle salle1, Salle salle2, int nbEquipement) {
    super(salle1, salle2, nbEquipement, 0);
  }
}
