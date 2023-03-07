package fr.uvsq.pglp.roguelike.echangeable;

/**
 * Encapsulation de tous les équipements du jeu.
 *
 * <p>Un équipement possède toujours un nom, un glyph pour le reconnaître et
 * un prix fixé.</p>
 *
 * @author Tom Abbouz
 * @version Février 2023
 * @see Arme
 * @see Armure
 */
public interface Equipement extends Echangeable {

  public int getPrix();
}
