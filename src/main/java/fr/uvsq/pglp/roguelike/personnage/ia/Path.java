package fr.uvsq.pglp.roguelike.personnage.ia;

import java.util.List;

import fr.uvsq.pglp.roguelike.personnage.Personnage;

/**
 * Cette classe à une cause esthétique:
 * elle permet de cacher les détails de construction
 * du chemin que réalise la classe <code>PathFinder</code>.
 *
 * @author Tom Abbouz
 * @version janvier 2023
 */
public class Path {

    private static final PathFinder pf = new PathFinder();

    private final List<Point> points;

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
