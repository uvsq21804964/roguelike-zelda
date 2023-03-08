package fr.uvsq.pglp.roguelike.donjon.elements;

/**
 * Représentation d'une salle simple de dimension 8x8.
 *
 * @see Salle
 * @author Tom Abbouz
 * @version Mars 2023
 */
public class SalleFixe extends Salle {

  public SalleFixe(int numero) {
    super(numero, 8, 8, 1, 1);
  }
}
