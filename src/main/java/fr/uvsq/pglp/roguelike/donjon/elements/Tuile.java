package fr.uvsq.pglp.roguelike.donjon.elements;

/**
 * Interface pour les tuiles d'un {@link UniversDeJeu}.
 *
 * @author Tom Abbouz
 * @version Mars 2023
 */
public interface Tuile {

  boolean isGround();

  String nom();

}
