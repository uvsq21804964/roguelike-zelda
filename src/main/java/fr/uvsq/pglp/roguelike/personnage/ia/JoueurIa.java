package fr.uvsq.pglp.roguelike.personnage.ia;

import fr.uvsq.pglp.roguelike.personnage.Personnage;
import java.util.List;

/**
 * Intelligence artificielle pour le personnage joueur.
 *
 * <p>La particulirité du personnage joueur est qu'il peut recevoir
 * des messages/notifications et possède dans sa mémoire les lieux
 * déjà visités.</p>
 *
 * @author Tom Abbouz
 * @version Février 2023
 * @see PersonnageIa
 */
public class JoueurIa extends PersonnageIa {

  /**
   * Constructeur de {@link JoueurIa}.
   *
   * @param personnage Le personnage joueur
   */
  public JoueurIa(Personnage personnage) {
    super(personnage, "JOUEUR", false);
  }
  
  @Override
  public void errer() {}

  @Override
  boolean doitChasser() {
    return false;
  }
  
  @Override
  boolean doitAider() {
    return false;
  }

}
