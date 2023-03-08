package fr.uvsq.pglp.roguelike.donjon.elements;

/**
 * Classe représentant un couloir ne contenant que des ennemis.
 *
 * <p>Un type particulier de {@link Couloir} qui ne contient que des ennemis.</p>
 *
 * @see Couloir
 * @author Tom Abbouz
 * @version Mars 2023
 */
public class CouloirAvecEnnemis extends Couloir {

  public CouloirAvecEnnemis(Salle salle1, Salle salle2, int nbEnnemis, int largeur, int longueur) {
    super(salle1, salle2, 0, nbEnnemis, largeur, longueur);
  }
}