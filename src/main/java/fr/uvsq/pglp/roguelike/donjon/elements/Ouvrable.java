package fr.uvsq.pglp.roguelike.donjon.elements;

public interface Ouvrable {

  public Tile getType();
  
  public boolean isOuverte();
  
  public void ouvrir();
}
