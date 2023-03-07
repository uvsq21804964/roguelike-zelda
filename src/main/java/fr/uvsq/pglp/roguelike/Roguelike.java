package fr.uvsq.pglp.roguelike;

import fr.uvsq.pglp.roguelike.ihm.ConsoleTexte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe principale de l'application.
 *
 * @author hal
 * @version jan 2023
 */
public enum Roguelike {
  APPLICATION;

  private static final Logger logger = LoggerFactory.getLogger(Roguelike.class);

  /**
   * Méthode appelée par la VM Java au démarrage de l'application.
   *
   * @param args Les entrées de la ligne de commande
   */
  public static void main(String[] args) {
    APPLICATION.run(args);
  }

  /**
   * Méthode principale du programme.
   *
   * @param args Les entrées de la ligne de commande
   */
  private void run(String[] args) {
    logger.info("Début de l'exécution");
    new ConsoleTexte();
    logger.info("Fin de l'exécution");
  }
}
