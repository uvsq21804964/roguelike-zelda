package fr.uvsq.pglp.roguelike.personnage.ia;

import fr.uvsq.pglp.roguelike.personnage.Personnage;

/**
 * Intelligence artificielle pour les PNJs défensifs.
 *
 * <p>Chaque PNJ défensif possède un point/objet à défendre. Dès ors que le
 * personnage joueur s'approche de trop près de ce point, alors lle
 * PNJ défensif ira attaquer le personnage joueur, jusqu'à ce qu'il s'éloigne assez.</p>
 *
 * @author Tom Abbouz
 * @version Février 2023
 * @see PersonnageIa
 */
public class DefensifIa extends PersonnageIa {

  private int defendreX;
  private int defendreY;

  public DefensifIa(Personnage personnage) {
    super(personnage);
  }

//
//  @Override
//  boolean doitChasser() {
//    List<fr.uvsq.pglp.roguelike.personnage.ia.Point> points = 
//        new Path(player, defendreX, defendreY).points();
//
//    if (points != null && points.size() <= 6) {
//      return true;
//    }
//    return false;
//  }
//
//  @Override
//  boolean doitAider() {
//    return false;
//  }
//
//  @Override
//  public void errer() {
//    int mx = (int) (Math.random() * 3) - 1;
//    int my = (int) (Math.random() * 3) - 1;
//
//    Personnage other = personnage.personnage(personnage.x + mx, personnage.y + my);
//
//    List<fr.uvsq.pglp.roguelike.personnage.ia.Point> points = 
//        new Path(personnage, defendreX, defendreY).points();
//
//    if (points != null && points.size() <= 6) {
//      if (other == null || !other.getNom().equals(personnage.getNom())) {
//        personnage.moveBy(mx, my);
//      }
//    }
//  }

}
