package fr.uvsq.pglp.roguelike.personnage.ia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Cette classe représente les points dans l'espace.
 * Elle facilite grandement la création des méthode
 * des classes <code>Path</code>, <code>PathFinder</code>
 * et <code>Line</code>.
 *
 * @author Tom Abbouz
 * @version janvier 2023
 */
public class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Point))
            return false;
        Point other = (Point) obj;
        if (x != other.x)
            return false;
		return y == other.y;
	}

    public List<Point> neighbors8() {
        List<Point> points = new ArrayList<Point>();

        for (int ox = -1; ox < 2; ox++) {
            for (int oy = -1; oy < 2; oy++) {
                if (ox == 0 && oy == 0)
                    continue;

                int nx = x + ox;
                int ny = y + oy;

                points.add(new Point(nx, ny));
            }
        }

        Collections.shuffle(points);
        return points;
    }
}
