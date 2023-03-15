package fr.uvsq.pglp.roguelike.personnage.attributs;

import fr.uvsq.pglp.roguelike.echangeable.ArmeDistance;
import fr.uvsq.pglp.roguelike.personnage.Personnage;
import fr.uvsq.pglp.roguelike.personnage.PersonnageDonjon;

public class ScoreAttaqueDistance extends ScoreAttaque {

  public ScoreAttaqueDistance(Personnage personnage, int attaque) {
    super(personnage, attaque);
  }

  @Override
  protected int valeur() {
    
    int s = 0;
    
//    if(((PersonnageDonjon) personnage).getArme1() != null 
//        && ((PersonnageDonjon) personnage).getArme1() instanceof ArmeDistance) {
//      (PersonnageDonjon) personnage).getArme1().getDe();
//    }
//    
    return attaque + s;
  }

}
