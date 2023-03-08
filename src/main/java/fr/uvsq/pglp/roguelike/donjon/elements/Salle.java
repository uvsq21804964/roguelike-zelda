package fr.uvsq.pglp.roguelike.donjon.elements;

import fr.uvsq.pglp.roguelike.donjon.MorceauEtage;

/**
 * Représentation d'une salle.
 *
 * <p>Une salle est relié à d'autres salles grâce à des {@link Couloir}s
 * et des {@link Porte}s.</p>
 *
 * @see MorceauEtage
 * @see SalleAleatoire
 * @see SalleFixe
 *
 * @author Tom Abbouz
 * @version Mars 2023
 */
public abstract class Salle extends MorceauEtage {

  protected int numero;

  /**
   * Constructeur de Salle.
   *
   * @param numero Le numero de la salle.
   * @param largeur La largeur de la salle.
   * @param longueur La longueur de la salle.
   * @param nbEquipement Le nombre d'équipements dans la salle.
   * @param nbEnnemis Le nombre d'ennemis dans la salle.
   */
  public Salle(int numero, int largeur, int longueur, int nbEquipement, int nbEnnemis) {
    super(largeur, longueur, nbEquipement, nbEnnemis);
    this.numero = numero;
    ajouterTresor();
  }

  private void ajouterTresor() {

    int a = (int) (Math.random() * 10);

    int nbTresors = 0;

    if (a < 3) {
      nbTresors = 3;
    } else if (a < 7) {
      nbTresors = 2;
    } else if (a < 9) {
      nbTresors = 1;
    }

    while (nbTresors > 0) {
      int x = (int) (Math.random() * tiles.length);
      int y = (int) (Math.random() * tiles[0].length);
      if (tiles[x][y].equals(Tile.FLOOR) && ouvrables[x][y] == null) {

        switch ((int) (Math.random() * 3)) {
          case 0:
          case 1:
            ouvrables[x][y] = new Tresor(Tile.BOX);
            break;
          case 2:
            ouvrables[x][y] = new Tresor(Tile.CHEST);
            break;
          default:
            break;
        }
        nbTresors--;
      }
    }
  }

  @Override
  public void ajouterPorte(Porte porte) {

    Couloir c = (Couloir) porte.getCouloir();

    if (c.numeroSalle1() == numero + 1 || c.numeroSalle2() == numero + 1) {
      int a = (int) (Math.random() * (tiles[0].length - 2)) + 1;
      tiles[tiles.length - 1][a] = porte.getType();
      ouvrables[tiles.length - 1][a] = porte;
    } else if (c.numeroSalle1() == numero - 1 || c.numeroSalle2() == numero - 1) {
      int a = (int) (Math.random() * (tiles[0].length - 2)) + 1;
      tiles[0][a] = porte.getType();
      ouvrables[0][a] = porte;
    } else if (c.numeroSalle1() == numero + 3 || c.numeroSalle2() == numero + 3) {
      int a = (int) (Math.random() * (tiles.length - 2)) + 1;
      tiles[a][tiles[0].length - 1] = porte.getType();
      ouvrables[a][tiles[0].length - 1] = porte;
    } else if (c.numeroSalle1() == numero - 3 || c.numeroSalle2() == numero - 3) {
      int a = (int) (Math.random() * (tiles.length - 2)) + 1;
      tiles[a][0] = porte.getType();
      ouvrables[a][0] = porte;
    }
  }
  
  public int numero() {
    return numero;
  }
}
