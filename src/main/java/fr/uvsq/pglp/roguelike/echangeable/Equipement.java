package fr.uvsq.pglp.roguelike.echangeable;

/**
 * Encapsulation de tous les équipements du jeu.
 *
 * <p>Un équipement possède toujours un nom, un glyph pour le reconnaître et
 * un prix fixé.</p>
 *
 * @see Arme
 * @see Armure
 *
 * @author Tom Abbouz
 * @version Février 2023
 */
public interface Equipement extends Echangeable {

  public int getPrix();
}
