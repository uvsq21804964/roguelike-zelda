package fr.uvsq.pglp.roguelike.personnage.attributs;

import fr.uvsq.pglp.roguelike.personnage.Personnage;

class ScoreAttaqueDistance extends ScoreAttaque {

  public ScoreAttaqueDistance(Personnage personnage, int attaque) {
    super(personnage, attaque);
  }

  @Override
  protected int valeur() {
    return attaque;
  }

}
