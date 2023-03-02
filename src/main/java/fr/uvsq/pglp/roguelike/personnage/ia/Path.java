package fr.uvsq.pglp.roguelike.personnage.ia;

import fr.uvsq.pglp.roguelike.personnage.Personnage;
import java.util.List;

/**
 * Classe cachant les détails de construction
 * du chemin que réalise la classe {@link PathFinder}.
 * <p>Cette classe à une cause esthétique.</p>
 *
 * @author Tom Abbouz
 * @version janvier 2023
 */
public class Path {

  private static final PathFinder pf = new PathFinder();

  private final List<Point> points;

  /**
   * Constructeur de la classe Path.
   *
   * @param creature Le {@link PersonnageDonjon} en question
   * @param x La coordonnée X du point objectif
   * @param y La coordonnée Y du point objectif
   */
  public Path(Personnage creature, int x, int y) {
    points = pf.findPath(creature,
            new Point(creature.getX(), creature.getY()),
            new Point(x, y),
            300);
  }

  public List<Point> points() {
    return points;
  }
}
