package fr.uvsq.pglp.roguelike.personnage.ia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Cette classe représente les points dans l'espace.
 *
 * <p>Elle facilite grandement la création des méthodes
 * des classes {@link Path}, {@link PathFinder}
 * et {@link Line}</p>.
 *
 * @author Tom Abbouz
 * @version janvier 2023
 */
public class Point {
  public int axeX;
  public int axeY;

  public Point(int axeX, int axeY) {
    this.axeX = axeX;
    this.axeY = axeY;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + axeX;
    result = prime * result + axeY;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof Point)) {
      return false;
    }
    Point other = (Point) obj;
    if (axeX != other.axeX) {
      return false;
    }
    return axeY == other.axeY;
  }

  /**
   * Renvoie les 8 {@link Point}s voisins d'un {@link Point}.
   *
   * @return une <b>List</b> de 8 {@link Point}s dans un ordre aléatoire.
   */
  public List<Point> neighbors8() {
    List<Point> points = new ArrayList<Point>();

    for (int ox = -1; ox < 2; ox++) {
      for (int oy = -1; oy < 2; oy++) {
        if (ox == 0 && oy == 0) {
          continue;
        }

        int nx = axeX + ox;
        int ny = axeY + oy;

        points.add(new Point(nx, ny));
      }
    }

    Collections.shuffle(points);
    return points;
  }
}
