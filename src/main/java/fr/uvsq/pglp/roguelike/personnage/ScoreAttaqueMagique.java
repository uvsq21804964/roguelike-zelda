package fr.uvsq.pglp.roguelike.personnage;

class ScoreAttaqueMagique extends ScoreAttaque {

  public ScoreAttaqueMagique(Personnage personnage, int attaque) {
    super(personnage, attaque);
  }

  @Override
  protected int attaque() {
    return attaque;
  }

}
