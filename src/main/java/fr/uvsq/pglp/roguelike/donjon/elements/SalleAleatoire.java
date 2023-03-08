package fr.uvsq.pglp.roguelike.donjon.elements;

/**
 * Représentation d'une salle aux dimensions aléatoires.
 *
 * @see Salle
 * @author Tom Abbouz
 * @version Mars 2023
 */
public class SalleAleatoire extends Salle {

  public SalleAleatoire(int numero, int largeur, int longueur) {
    super(numero, largeur, longueur, (int) (Math.random() * 5) + 1, (int) (Math.random() * 2) + 1);
  }
}
