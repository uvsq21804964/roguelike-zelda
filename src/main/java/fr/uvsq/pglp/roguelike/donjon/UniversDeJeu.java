package fr.uvsq.pglp.roguelike.donjon;

import fr.uvsq.pglp.roguelike.personnage.Personnage;

/**
 * Encapsulation des univers de jeu possibles.
 *
 * <p>Un UniversDeJeu possède toujours un personnage joueur.</p>
 *
 * @see Donjon
 *
 * @author Tom Abbouz
 * @version Mars 2023
 */
public interface UniversDeJeu {

  Personnage getJoueur();
}
