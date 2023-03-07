package fr.uvsq.pglp.roguelike.ihm;

import fr.uvsq.pglp.roguelike.ihm.screen.PlayScreen;
import fr.uvsq.pglp.roguelike.personnage.PersonnageDonjon;

public class Facade {

  private PersonnageDonjon joueur;

  public Facade(Console console) {
    PlayScreen playscreen = (PlayScreen) console.getScreen();
    this.joueur = playscreen.getJoueur();
  }

  public void deplacer(String s) {
    switch (s) {
      case "droite":
        joueur.moveBy(1, 0);
        break;
      case "gauche":
        joueur.moveBy(-1, 0);
        break;
      case "haut":
        joueur.moveBy(0, -1);
        break;
      case "bas":
        joueur.moveBy(0, 1);
        break;
      default:
        break;
    }
  }

  public void sauter(String s) {
    // TODO Auto-generated method stub

  }

  public void franchir(String s) {
    // TODO Auto-generated method stub

  }

  public void ramasser(String s) {
    // TODO Auto-generated method stub

  }

  public void parler(String s) {
    // TODO Auto-generated method stub

  }

  public void soutirer(String s) {
    // TODO Auto-generated method stub

  }

  public void convaincre(String s) {
    // TODO Auto-generated method stub

  }

}
