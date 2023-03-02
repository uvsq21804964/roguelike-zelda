package fr.uvsq.pglp.roguelike.donjon.genererdonjon;

/**
 * Cette classe abstraite définit un couloir.
 * Un couloir contient soit des PNJ, soit des équipements,
 * et relie toujours deux salles.
 *
 * @see CouloirAvecEquipements
 * @see CouloirAvecEnnemis
 * 
 * @author Tom Abbouz
 *
 */
public abstract class Couloir {

  protected Salle salle1;
  protected Salle salle2;
  protected int nbEquipement;
  protected int nbPnj;
  
  /**
   * Constructeur minimal d'un couloir.
   *
   * @exception IllegalArgumentException Une salle ne peut être relié par un 
   *            Couloir avec elle-même.
   * 
   * @param salle1 La première salle que le couloir relie.
   * @param salle2 La deuxième salle que le couloir relie.
   * @param nbEquipement Le nombre d'equipements que l'on peut trouver au 
   *        maximum dans le couloir.
   * @param nbPnj Le nombre maximum de PNJ (ennemis ou non-ennemis) 
   *        que l'on peut trouver au maximum dans le couloir.
   * 
   */
  public Couloir(Salle salle1, Salle salle2, int nbEquipement, int nbPnj) {
    
    if (salle1.equals(salle2)) {
      throw new IllegalArgumentException("Un couloir ne doit pas relié une salle à elle-même.");
    }
    
    this.salle1 = salle1;
    this.salle2 = salle2;
    this.nbEquipement = nbEquipement;
    this.nbPnj = nbPnj;
  }
}
