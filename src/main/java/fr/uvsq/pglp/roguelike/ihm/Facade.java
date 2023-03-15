package fr.uvsq.pglp.roguelike.ihm;

import fr.uvsq.pglp.roguelike.ihm.screen.PlayScreen;
import fr.uvsq.pglp.roguelike.personnage.PersonnageDonjon;

/**
 * Classe façade pour les {@link CommandFactory}.
 *
 * <p>Fais le lien entre la {@link ConsoleTexte} et la couche métier.</p>
 *
 * <p>Facade fournit une interface simplifiée pour un ensemble de classes. 
 * Simplifie l’usage et la compréhension de l’interface dans son contexte.
 * Réduit le couplage entre les clients et les classes.</p>
 *
 * <p>Fonctionne en effectuant des actions sur le joueur de l'{@link UniversDeJeu}.</p>
 *
 * @author Tom Abbouz
 * @version Mars 2023
 */
public class Facade {

  private PersonnageDonjon joueur;

  public Facade(Console console) {
    PlayScreen playscreen = (PlayScreen) console.getScreen();
    this.joueur = playscreen.getJoueur();
  }

  /**
   * Ordonne au joueur de se déplacer s'il le peut.
   *
   * <p>Appelle à la méthode {@link PersonnageDonjon#moveBy(int, int)}.
   *
   * @param s L'objet de la commande 'déplacer'.
   */
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

  public void ouvrir(String s) {
    switch (s) {
    case "porte":
      joueur.ouvrirPorte();
      break;
    case "coffre":
      joueur.ouvrirCoffre();
      break;
    default:
      break;
    }
  }

  public void crocheter(String s) {
    switch (s) {
    case "porte":
      joueur.crocheterPorte();
      break;
    case "coffre":
      joueur.notifier("Aucun coffre n'est crochetable dans ce jeu!");
      break;
    default:
      break;
    }
  }

  public void forcer(String s) {
    switch (s) {
    case "porte":
      joueur.notifier("Aucune porte n'est forçable dans ce jeu!");
      break;
    case "coffre":
      joueur.forcerCoffre();
      break;
    default:
      break;
    }
  }

  public void ramasser(String s) {
    joueur.commandeRamasser(s);
  }

  public boolean convaincre(String s) {
    return joueur.convaincre(s);
  }

  public boolean parler(String s) {
    return joueur.parler(s);
  }

  public boolean soutirer(String s) {
    return joueur.soutirer(s);
  }

  public boolean acheter(String s) {
    return joueur.acheter(s);
  }

  public void sauter(String s) {
    switch (s) {
    case "droite":
      joueur.sauter(2, 0);
      break;
    case "gauche":
      joueur.sauter(-2, 0);
      break;
    case "haut":
      joueur.sauter(0, -2);
      break;
    case "bas":
      joueur.sauter(0, 2);
      break;
    default:
      break;
    }
  }

  public void franchir(String s) {
    switch (s) {
    case "droite":
      joueur.franchir(1, 0);
      break;
    case "gauche":
      joueur.franchir(-1, 0);
      break;
    case "haut":
      joueur.franchir(0, -1);
      break;
    case "bas":
      joueur.franchir(0, 1);
      break;
    default:
      break;
    }
  }

  public void etudier(String s) {
    switch (s) {
    case "porte":
    case "salle":
      joueur.etudierPorte(s);
      break;
    case "coffre":
      joueur.etudierCoffre(s);
      break;
    default:
      break;
    }
  }

  public void equiper(int i) {
    joueur.equiper(i);
  }
  
  public void desequiper(int i) {
    joueur.desequiper(i);
  }

  public boolean attaquer(String s) {
    return joueur.attaquer(s);
  }

}
