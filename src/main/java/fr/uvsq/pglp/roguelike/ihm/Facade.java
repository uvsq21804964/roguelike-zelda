package fr.uvsq.pglp.roguelike.ihm;

import fr.uvsq.pglp.roguelike.ihm.screen.Screen;

public class Facade {

  private Screen screen;
  
  public Facade(Screen screen) {
    this.screen = screen;
  }

}
