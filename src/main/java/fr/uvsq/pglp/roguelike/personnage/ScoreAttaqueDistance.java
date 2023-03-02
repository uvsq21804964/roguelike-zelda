package fr.uvsq.pglp.roguelike.personnage;

class ScoreAttaqueDistance extends ScoreAttaque {

  public ScoreAttaqueDistance(Personnage personnage, int attaque) {
    super(personnage, attaque);
  }

  @Override
  protected int attaque() {
    return attaque;
  }

}
