package fr.uvsq.pglp.roguelike.ihm;

import java.awt.event.KeyEvent;
import javax.swing.JFrame;

/**
 * Encapsulation des différents écrans du jeu.
 * 
 * <p>Les différents écrans du jeu doivent afficher la sortie et 
 * répondre aux entrées de l'utilisateur.</p> 
 *
 * @see LoseScreen
 * @see PlayScreen
 * @see StartScreen
 * @see WinScreen
 *
 * @author Tom Abbouz
 * @version janvier 2023
 */
public interface Screen {
  void displayOutput(JFrame terminal);

  Screen respondToUserInput(KeyEvent key);
}
