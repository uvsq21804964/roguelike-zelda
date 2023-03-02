package fr.uvsq.pglp.roguelike.donjon.genererdonjon;

/**
 * Cette classe représente un {@link Couloir} vide permettant de relier deux 
 * {@link Salle}s.
 *
 * @see Couloir
 * 
 * @author Tom Abbouz
 *
 */
public class CouloirSimple extends Couloir {

  /**
   * Constructeur de {@link CCouloirSimple}.
   * Utilise le constructeur de {@link Couloir} avec nbEquipements=nbPnj=0.
   *
   * @param salle1 La première salle que le couloir relie.
   * @param salle2 La deuxième salle que le couloir relie.
   *
   * @see Couloir#Couloir(Salle, Salle, int, int)
   * 
   */
  public CouloirSimple(Salle salle1, Salle salle2) {
    super(salle1, salle2, 0, 0);
  }

}
