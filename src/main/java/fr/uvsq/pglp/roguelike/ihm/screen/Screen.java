package fr.uvsq.pglp.roguelike.ihm.screen;

import fr.uvsq.pglp.roguelike.ihm.Console;

/**
 * Cette interface permet d'afficher la sortie sur l'AsciiPanel
 * et de répondre aux entrées de l'utilisateur.
 *
 * @author Tom Abbouz
 * @version janvier 2023
 */
public interface Screen {
    void displayOutput(Console console);

    boolean commande(String s);

    Screen autreScreen(String s);
}

