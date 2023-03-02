package fr.uvsq.pglp.roguelike.personnage;

abstract class ScoreAttaque {

  protected Personnage personnage;
  protected int attaque;

  public ScoreAttaque(Personnage personnage, int attaque) {
    this.personnage = personnage;
    this.attaque = attaque;
  }

  protected abstract int attaque();

  protected int attaqueSansEquipements() {
    return attaque;
  }

  protected void setPersonnage(Personnage pers) {
    this.personnage = pers;
  }
}
