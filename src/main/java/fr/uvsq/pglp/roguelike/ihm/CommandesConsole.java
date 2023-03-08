package fr.uvsq.pglp.roguelike.ihm;

/**
 * Encapsulation des interfaces utilisateur fonctionnant avec des commandes.
 *
 * <p>Le but ici est de respecter le principe ISP pour {@link Console}.</p>
 *
 * @see Command
 * @see Ecran Analogue de cette classe pour l'écran de l'interface
 *
 * @author Tom Abbouz
 * @version Mars 2023
 */
public interface CommandesConsole {

  void actionInexistante(String[] commands);

  void commandePartiellementInexistante(String[] commands);

  void mauvaisNombreMots(String[] commands);

  void personnageInexistant(String[] commands);

  void message(String s);

  public void doCommand(String s);
}
