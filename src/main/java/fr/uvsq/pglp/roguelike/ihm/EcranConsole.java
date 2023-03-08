package fr.uvsq.pglp.roguelike.ihm;

import fr.uvsq.pglp.roguelike.ihm.screen.Screen;
import java.awt.Color;

/**
 * Encapsulation des interfaces utilisateur affichant un écran avec du texte.
 *
 * <p>Le but ici est de respecter le principe ISP pour {@link Console}.</p>
 *
 * @see CommandesConsole Analogue de cette classe pour les commandes
 *
 * @author Tom Abbouz
 * @version Mars 2023
 */
public interface EcranConsole {

  public void print(String s);
  
  public void print(String a, Color c);
  
  public void println(String s);
  
  public void println(String s, Color c);

  public void sauts(int n);

  public void clear();

  public Screen getScreen();
}
