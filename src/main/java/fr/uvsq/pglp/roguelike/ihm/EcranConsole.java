package fr.uvsq.pglp.roguelike.ihm;

import fr.uvsq.pglp.roguelike.ihm.screen.Screen;
import java.awt.Color;

public interface EcranConsole {

  public void print(String s);
  
  public void print(String a, Color c);
  
  public void println(String s);
  
  public void println(String s, Color c);

  public void sauts(int n);

  public void clear();

  public Screen getScreen();
}
