package fr.uvsq.pglp.roguelike.echangeable;

/**
 * Encapsulation de tous les éléments échangeables entre
 * {@link Personnage} d'un {@link UniversDeJeu}.
 *
 * <p>Chaque échangeable doit posséder au moins un nom ({@link Echangeable#getNom()}) 
 * un glyph ({@link Echangeable#getGlyph()}).</p>
 *
 * @see Equipement
 * @see Pieces
 *
 * @author Tom Abbouz
 * @version Mars 2023
 */
public interface Echangeable {

  public char getGlyph();

  public String getNom();

}
