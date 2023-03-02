package fr.uvsq.pglp.roguelike.personnage.ia;

import fr.uvsq.pglp.roguelike.personnage.Personnage;

/**
 * Intelligence artificielle pour les PNJs agressifs.
 * 
 * <p>Dès qu'un PNJ agressif voit le personnage joueur, il l'attaque et/ou
 * s'approche de lui.</p>
 *
 * @see PersonnageIa
 * 
 * @author Tom Abbouz
 * @version Février 2023
 */
public class AgressifIa extends PersonnageIa {

  public AgressifIa(Personnage personnage) {
    super(personnage);
  }

  @Override
  boolean doitChasser() {
    return true;
  }

  @Override
  boolean doitAider() {
    return false;
  }
}
