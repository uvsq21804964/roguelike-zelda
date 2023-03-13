package fr.uvsq.pglp.roguelike.donjon.elements;

public class Bombe {

  private int degats;
  private boolean exploser;
  
  protected Bombe(int degats) {
   this.exploser = false;
   this.degats = degats;
  }
  
  protected void exploser() {
    exploser = true;
  }
  
  protected int degat() {
    return degats;
  }
}
