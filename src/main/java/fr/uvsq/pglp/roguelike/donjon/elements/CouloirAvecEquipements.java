package fr.uvsq.pglp.roguelike.donjon.elements;

/**
 * Classe représentant un couloir ne contenant que des {@link Equipement}s.
 *
 * <p>Un type particulier de {@link Couloir} qui ne contient que des {@link Equipement}s.</p>
 *
 * @see Couloir
 * @author Tom Abbouz
 * @version Mars 2023
 */
public class CouloirAvecEquipements extends Couloir {

  public CouloirAvecEquipements(Salle salle1, Salle salle2, int nbEquipement, 
      int largeur, int longueur) {
    super(salle1, salle2, nbEquipement, 0, largeur, longueur);
  }
}