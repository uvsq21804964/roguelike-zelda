package fr.uvsq.pglp.roguelike.personnage.ia;

import java.util.List;

import fr.uvsq.pglp.roguelike.personnage.Personnage;

/**
 * Intelligence artificielle pour le personnage joueur.
 *
 * <p>La particulirité du personnage joueur est qu'il peut recevoir
 * des messages/notifications et possède dans sa mémoire les lieux
 * déjà visités.</p>
 *
 * @see PersonnageIa
 *
 * @author Tom Abbouz
 * @version Février 2023
 */
public class JoueurIa extends PersonnageIa {

  private final List<String> messages;
  // private final FieldOfView fov;

  /**
   * Constructeur de {@link JoueurIa}.
   *
   * @param personnage Le personnage joueur
   * @param messages Une liste de messages devant lui être envoyés
   * @param fov Un champ de vision {@link FieldOfView}
   */
  public JoueurIa(Personnage personnage, List<String> messages) {
    super(personnage);
    this.messages = messages;
    // this.fov = fov;
  }

  @Override
  public void notifier(String message) {
    messages.add(message);
  }
  
  @Override
  public List<String> getMessages() {
    return messages;
  }
//
//  @Override
//  public Tile souvenirTuile(int wx, int wy) {
//    return fov.tile(wx, wy);
//  }
//
//  @Override
//  public boolean peutVoir(int wx, int wy) {
//    return fov.isVisible(wx, wy);
//  }
//
//  @Override
//  boolean doitChasser() {
//    return false;
//  }
//
//  @Override
//  boolean doitAider() {
//    return false;
//  }

}
