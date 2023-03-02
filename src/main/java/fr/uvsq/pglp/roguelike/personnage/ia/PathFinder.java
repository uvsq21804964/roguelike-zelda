package fr.uvsq.pglp.roguelike.personnage.ia;

import fr.uvsq.pglp.roguelike.personnage.Personnage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Classe créant un chemin entre deux personnages.
 *
 * <p> Plus précisément, cette classe permet aux PNJ ennemis de trouver
 * leur chemin jusqu'au PJ s'il est dans leur champ de vision afin qu'ils l'attaquent.</p>
 *
 * @author Tom Abbouz
 * @version janvier 2023
 */
public class PathFinder {
  private final ArrayList<Point> open;
  private final ArrayList<Point> closed;
  private final HashMap<Point, Point> parents;
  private final HashMap<Point, Integer> totalCost;
  
  /**
   * Constructeur de {@link PathFinder}.
   */
  public PathFinder() {
    this.open = new ArrayList<Point>();
    this.closed = new ArrayList<Point>();
    this.parents = new HashMap<Point, Point>();
    this.totalCost = new HashMap<Point, Integer>();
  }

  private int heuristicCost(Point from, Point to) {
    return Math.max(Math.abs(from.x - to.x), Math.abs(from.y - to.y));
  }

  private int costToGetTo(Point from) {
    return parents.get(from) == null ? 0 : (1 + costToGetTo(parents.get(from)));
  }

  private int totalCost(Point from, Point to) {
    if (totalCost.containsKey(from)) {
      return totalCost.get(from);
    }

    int cost = costToGetTo(from) + heuristicCost(from, to);
    totalCost.put(from, cost);
    return cost;
  }

  private void reParent(Point child, Point parent) {
    parents.put(child, parent);
    totalCost.remove(child);
  }
  
  /**
   * Construit un chemin entre deux points pour un {@link PersonnageDonjon}.
   *
   * @param creature Le {@link PersonnageDonjon} en question
   * @param start Le {@link Point} de départ du {@link PersonnageDonjon}.
   * @param end Le {@link Point} objectif
   * @param maxTries Le nombre maximum de chemin différent calculé.
   * @return Une liste de {@link Point} étant le meilleur chemin calculé.
   */
  public ArrayList<Point> findPath(Personnage creature, Point start, Point end, int maxTries) {
    open.clear();
    closed.clear();
    parents.clear();
    totalCost.clear();

    open.add(start);

    for (int tries = 0; tries < maxTries && open.size() > 0; tries++) {
      Point closest = getClosestPoint(end);

      open.remove(closest);
      closed.add(closest);

      if (closest.equals(end)) {
        return createPath(start, closest); 
      } else {
        checkNeighbors(creature, end, closest); 
      }
    }
    return null;
  }

  private Point getClosestPoint(Point end) {
    Point closest = open.get(0);
    for (Point other : open) {
      if (totalCost(other, end) < totalCost(closest, end)) {
        closest = other;
      }
    }
    return closest;
  }

  private void checkNeighbors(Personnage creature, Point end, Point closest) {
    for (Point neighbor : closest.neighbors8()) {
      if (closed.contains(neighbor)
              || !creature.canEnter(neighbor.x, neighbor.y)
              && !neighbor.equals(end)) {
        continue;
      }

      if (open.contains(neighbor)) {
        reParentNeighborIfNecessary(closest, neighbor);
      } else {
        reParentNeighbor(closest, neighbor);
      }
    }
  }

  private void reParentNeighbor(Point closest, Point neighbor) {
    reParent(neighbor, closest);
    open.add(neighbor);
  }

  private void reParentNeighborIfNecessary(Point closest, Point neighbor) {
    Point originalParent = parents.get(neighbor);
    double currentCost = costToGetTo(neighbor);
    reParent(neighbor, closest);
    double reparentCost = costToGetTo(neighbor);

    if (reparentCost < currentCost) {
      open.remove(neighbor);
    } else {
      reParent(neighbor, originalParent);
    }
  }

  private ArrayList<Point> createPath(Point start, Point end) {
    ArrayList<Point> path = new ArrayList<Point>();

    while (!end.equals(start)) {
      path.add(end);
      end = parents.get(end);
    }

    Collections.reverse(path);
    return path;
  }
}