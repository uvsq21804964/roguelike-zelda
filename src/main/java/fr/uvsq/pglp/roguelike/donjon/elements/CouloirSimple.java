package fr.uvsq.pglp.roguelike.donjon.elements;

/**
 * Classe représentant un couloir vide.
 *
 * <p>Le type le plus simple de {@link Couloir}, complètement vide.</p>
 *
 * @see Couloir
 * @author Tom Abbouz
 * @version Mars 2023
 */
public class CouloirSimple extends Couloir {
  public CouloirSimple(Salle salle1, Salle salle2, int largeur, int longueur) {
    super(salle1, salle2, 0, 0, largeur, longueur);
  }
}
