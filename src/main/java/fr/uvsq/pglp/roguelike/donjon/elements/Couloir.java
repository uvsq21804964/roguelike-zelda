package fr.uvsq.pglp.roguelike.donjon.elements;

import fr.uvsq.pglp.roguelike.donjon.MorceauEtage;

/**
 * Classe abstraite représentant un couloir.
 *
 * <p>Un couloir peut contenir des ennemis et des {@link Equipement}s
 * au sol, mais pas de {@link Tresor}s. De plus, il possède toujours deux
 * {@link Porte}s permettant de faire la liaison entre deux {@link Salle}s.</p>
 *
 * @see MorceauEtage
 * @see CouloirAvecEnnemis
 * @see CouloirAvecEquipements
 * @author Tom Abbouz
 * @version Mars 2023
 */
public abstract class Couloir extends MorceauEtage {

  protected Salle salle1;
  protected Salle salle2;

  /**
   * Constructeur de couloir.
   *
   * @param salle1 La première {@link Salle} qu'il relie.
   * @param salle2 La seconde {@link Salle} qu'il relie.
   * @param nbEquipement Le nombre d'{@link Equipement}s qui s'y trouve.
   * @param nbEnnemis Le nombre d'ennemis qui s'y trouve.
   * @param largeur La largeur du couloir (murs inclus)
   * @param longueur La longueur du couloir (murs inclus)
   */
  public Couloir(Salle salle1, Salle salle2, int nbEquipement, int nbEnnemis,
      int largeur, int longueur) {
    super(largeur, longueur, nbEquipement, nbEnnemis);
    this.salle1 = salle1;
    this.salle2 = salle2;
  }

  @Override
  public void ajouterPorte(Porte porte) {

    if (porte.getSalle().numero == Math.max(salle1.numero, salle2.numero)) {
      if (largeur > longueur) {
        int a = (int) (Math.random() * (tiles[0].length - 2)) + 1;
        tiles[tiles.length - 1][a] = porte.getType();
        ouvrables[tiles.length - 1][a] = porte;
      } else {
        int a = (int) (Math.random() * (tiles.length - 2)) + 1;
        tiles[a][tiles[0].length - 1] = porte.getType();
        ouvrables[a][tiles[0].length - 1] = porte;
      }
    } else {
      if (largeur > longueur) {
        int a = (int) (Math.random() * (tiles[0].length - 2)) + 1;
        tiles[0][a] = porte.getType();
        ouvrables[0][a] = porte;
      } else {
        int a = (int) (Math.random() * (tiles.length - 2)) + 1;
        tiles[a][0] = porte.getType();
        ouvrables[a][0] = porte;
      }
    }
  }

  int numeroSalle1() {
    return salle1.numero;
  }

  int numeroSalle2() {
    return salle2.numero;
  }
}
