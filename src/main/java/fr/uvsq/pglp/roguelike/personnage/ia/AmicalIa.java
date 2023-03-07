package fr.uvsq.pglp.roguelike.personnage.ia;

import fr.uvsq.pglp.roguelike.personnage.Personnage;

/**
 * Intelligence artificielle pour les PNJs amicaux.
 *
 * <p>Dès qu'un PNJ amical voit le personnage joueur, il l'aide au plus 5 fois.
 * Une aide est soit un don de pièces d'argent ou un don d'équipement.</p>
 *
 * @author Tom Abbouz
 * @version Février 2023
 * @see PersonnageIa
 */
public class AmicalIa extends PersonnageIa {

  private final int nbMaxAides = (int) (Math.random() * 5) + 1;
  private int nbAides = 0;

  public AmicalIa(Personnage personnage) {
    super(personnage);
  }
//
//  @Override
//  boolean doitChasser() {
//    return false;
//  }
//
//  @Override
//  boolean doitAider() {
//
//    if (nbMaxAides > nbAides) {
//      nbAides++;
//      return true;
//    }
//
//    return false;
//  }
}
