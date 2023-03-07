package fr.uvsq.pglp.roguelike.personnage;

import fr.uvsq.pglp.roguelike.donjon.elements.ElementEtage;
import fr.uvsq.pglp.roguelike.personnage.ia.PersonnageIa;

/**
 * Encapsulation des différentes sortes de personnage.
 *
 * <p>Dans notre cas, il n'existe que la sorte {@link PersonnageDonjon}.</p>
 * <p>Chaque sorte de personnage doit posséder : un nom, une méthode d'accès
 * à une zone, une méthode de déplacement, un sac pour ranger des items, une
 * position à 2D, un rayon de vision et la possibilité de poser aléatoirement un
 * certain nombre de pièces (=monnaie du jeu).</p>
 *
 * @author Tom Abbouz
 * @version Février 2023
 */
public interface Personnage {

  String getNom();

  int getX();

  void setX(int x);

  int getY();

  void setY(int y);

  void setPosition(int x, int y);

  void genererPersonnageIa();

  PersonnageIa getIa();

  void morceauEtage(ElementEtage elementEtage);

  ElementEtage getElementEtage();

  void notifier(String message, Object... params);
}
