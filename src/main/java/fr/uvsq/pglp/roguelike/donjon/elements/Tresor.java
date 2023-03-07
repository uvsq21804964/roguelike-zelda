package fr.uvsq.pglp.roguelike.donjon.elements;

public class Tresor implements Ouvrable {

  private Tile type;
  private boolean ouverte = false;

  public Tresor(Tile type) {
    this.type = type;
  }

  public Tile getType() {
    return type;
  }

  public boolean isOuverte() {
    return ouverte;
  }

  public void ouvrir() {
    this.ouverte = true;
  }
}
