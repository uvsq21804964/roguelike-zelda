package fr.uvsq.pglp.roguelike.personnage.ia;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Cette classe représente une ligne sur le plan de l'étage du donjon.
 *
 * <p>Afin de déterminer si quelquechose est dans le {@link FieldOfView} d'un 
 * {@link PersonnageDonjon},
 * il faut obtenir tous les {@link Point}s entre cette {@link PersonnageDonjon} et ce
 * qu'elle veut regarder.</p>
 * <p>Ainsi, on peut déterminer si l'un d'eux bloque son {@link FieldOfView} et 
 * l'empeche donc de voire l'objet en question.</p>
 * <p>Cette classe utilise l'algorithme de Bresenham pour trouver tous les <code>Point</code>s
 * le long d'une ligne de vue.</p>
 *
 * @author Tom Abbouz
 * @version janvier 2023
 */
public class Line implements Iterable<Point> {
  private final List<Point> points;

  /**
   * Constructeur de la classe Line.
   *
   * @param x0 Coordonnée X du premier point
   * @param y0 Coordonnée Y du premier point
   * @param x1 Coordonnée X du second point
   * @param y1 Coordonnée Y du second point
   */
  public Line(int x0, int y0, int x1, int y1) {
    points = new ArrayList<Point>();

    int dx = Math.abs(x1 - x0);
    int dy = Math.abs(y1 - y0);

    int sx = x0 < x1 ? 1 : -1;
    int sy = y0 < y1 ? 1 : -1;
    int err = dx - dy;

    while (true) {
      points.add(new Point(x0, y0));

      if (x0 == x1 && y0 == y1) {
        break;
      }

      int e2 = err * 2;
      if (e2 > -dy) {
        err -= dy;
        x0 += sx;
      }
      if (e2 < dx) {
        err += dx;
        y0 += sy;
      }
    }
  }

  public List<Point> getPoints() {
    return points;
  }

  @Override
  public Iterator<Point> iterator() {
    return points.iterator();
  }
}
