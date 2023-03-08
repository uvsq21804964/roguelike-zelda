package fr.uvsq.pglp.roguelike.donjon.elements;

import fr.uvsq.pglp.roguelike.echangeable.Echangeable;

/**
 * Encapsulation de tous les éléments ouvrables.
 *
 * <p>Autrement dit, on encapsule tous les éléments d'un {@link UniversDeJeu}
 * pouvant être ouvert, forcer, crocheter, ... par le joueur.</p>
 *
 * @author Tom Abbouz
 * @version Mars 2023
 */
public interface Ouvrable {

  public Tile getType();

  public boolean isOuverte();

  public Echangeable ouvrir();
  
  public boolean tirage(int mod);
 
  public String getDifficulte();

  public int getDiffValeur();
}
