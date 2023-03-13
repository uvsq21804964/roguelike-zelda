package fr.uvsq.pglp.roguelike.personnage.attributs;

import fr.uvsq.pglp.roguelike.personnage.Personnage;

class ScoreAttaqueContact extends ScoreAttaque {

  public ScoreAttaqueContact(Personnage personnage, int attaque) {
    super(personnage, attaque);
  }

  @Override
  protected int valeur() {
    return attaque;
  }

}
