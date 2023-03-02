package fr.uvsq.pglp.roguelike.personnage.ia;

import fr.uvsq.pglp.roguelike.donjon.Tile;
import java.io.Serializable;

/**
 * Cette classe représente le champ de vision des {@link PersonnageDonjon}s.
 * Plus spécifiquement pour le PJ,elle détermine ce qui est vue en l'instant,
 * et rappelle ce qui a déjà été vu.
 *
 * @author Tom Abbouz
 * @version janvier 2023
 */
public class FieldOfView implements Serializable {
  private static final long serialVersionUID = 3075371565393106880L;

  private final World world;
  private final Tile[][] tiles;
  private boolean[][] visible;

  /**
   * Constructeur de la class FieldOfView.
   * <p>Elle permet d'indiquer quelle tuile est visible ou non pour les
   * personnages.</p>
   *
   * @param world L'étage de donjon dans lequel évolue le personnage joueur
   */
  public FieldOfView(World world) {
    this.world = world;
    this.visible = new boolean[world.width()][world.height()];
    this.tiles = new Tile[world.width()][world.height()];

    for (int x = 0; x < world.width(); x++) {
      for (int y = 0; y < world.height(); y++) {
        tiles[x][y] = Tile.UNKNOWN;
      }
    }
  }

  public boolean isVisible(int x, int y) {
    return x >= 0 && y >= 0 && x < visible.length && y < visible[0].length && visible[x][y];
  }

  public Tile tile(int x, int y) {

    return tiles[x][y];
  }

  /**
   * Mise à jour du {@link FieldOfView}, indiquant les {@link Tile}s visibles
   * par un {@link PersonnageDonjon}.
   *
   * @param wx Position selon l'axe X du personnage.
   * @param wy Position selon l'axe Y du personnage.
   * @param r Rayon de vision du personnage.
   */
  public void update(int wx, int wy, int r) {
    visible = new boolean[world.width()][world.height()];

    for (int x = -r; x < r; x++) {
      for (int y = -r; y < r; y++) {
        if (x * x + y * y > r * r) {
          continue;
        }

        if (wx + x < 0 || wx + x >= world.width() || wy + y < 0 || wy + y >= world.height()) {
          continue;
        }

        for (Point p : new Line(wx, wy, wx + x, wy + y)) {
          Tile tile = world.tile(p.x, p.y);
          visible[p.x][p.y] = true;
          tiles[p.x][p.y] = tile;

          if (!tile.isGround()) {
            break;
          }
        }
      }
    }
  }

  public void modifierTuile(int x, int y, Tile tile) {
    tiles[x][y] = tile;
  }
}
