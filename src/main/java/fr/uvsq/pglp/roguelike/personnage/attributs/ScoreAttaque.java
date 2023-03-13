package fr.uvsq.pglp.roguelike.personnage.attributs;

import fr.uvsq.pglp.roguelike.personnage.Personnage;

abstract class ScoreAttaque {

  protected Personnage personnage;
  protected int attaque;

  public ScoreAttaque(Personnage personnage, int attaque) {
    this.personnage = personnage;
    this.attaque = attaque;
  }

  protected abstract int valeur();

  protected int valeurSansEquipements() {
    return attaque;
  }

  protected void setPersonnage(Personnage pers) {
    this.personnage = pers;
  }
}
