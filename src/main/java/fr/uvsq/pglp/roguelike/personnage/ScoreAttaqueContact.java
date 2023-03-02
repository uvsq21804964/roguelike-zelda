package fr.uvsq.pglp.roguelike.personnage;

class ScoreAttaqueContact extends ScoreAttaque {

  public ScoreAttaqueContact(Personnage personnage, int attaque) {
    super(personnage, attaque);
  }

  @Override
  protected int attaque() {
    return attaque;
  }

}
