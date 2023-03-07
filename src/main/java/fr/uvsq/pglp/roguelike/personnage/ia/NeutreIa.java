package fr.uvsq.pglp.roguelike.personnage.ia;

import fr.uvsq.pglp.roguelike.personnage.Personnage;

/**
 * Intelligence artificielle pour les PNJs neutres.
 *
 * <p>Dès que le personnage joueur attaque un PNJ neutre, celui-ci l'attaque et/ou
 * s'approche du personnage joueur.</p>
 *
 * @author Tom Abbouz
 * @version Février 2023
 * @see PersonnageIa
 */
public class NeutreIa extends PersonnageIa {

  public NeutreIa(Personnage personnage) {
    super(personnage);
  }
//
//  @Override
//  boolean doitChasser() {
//
//    //TODO
//    return false;
//
//  }
//
//  @Override
//  boolean doitAider() {
//    return false;
//  }

}
