package fr.uvsq.pglp.roguelike.donjon.genererdonjon;

/**
 * Encapsulation des univers de jeu, pouvant être un étage de {@link Donjon}
 * ou d'une autre sorte, si nécessaire.
 *
 * <p>Seulement l'implémentation {@link Donjon} existe pour l'instant, mais on pourra
 * très bien en produire d'autre par la suite grâce à cette interface.</p>
 *
 * @see Donjon
 * 
 * @author Tom Abbouz
 * @version Février 2023
 */
public interface UniversDeJeu {

  void generer(GenerationDonjon donjon);
}
