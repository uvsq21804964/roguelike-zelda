package fr.uvsq.pglp.roguelike.personnage.attributs;

public class DeDeVie {

  private final int valeurMax;

  public DeDeVie (int valeurMax) {
    this.valeurMax = valeurMax;
  }

  public int tirage() {
    return ((int) ((Math.random()) * valeurMax) + 1);
  }
  
  public int valeurMax() {
    return valeurMax;
  }
}