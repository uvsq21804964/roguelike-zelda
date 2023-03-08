package fr.uvsq.pglp.roguelike.ihm;

/**
 * Interface pour les commandes des {@link Console}s 
 * implémentants {@link CommandesConsole}.
 *
 * @see CommandFactory
 *
 * @author Tom Abbouz
 * @version Mars 2023
 */
@FunctionalInterface
public interface Command {
  public void apply(String s);
}
