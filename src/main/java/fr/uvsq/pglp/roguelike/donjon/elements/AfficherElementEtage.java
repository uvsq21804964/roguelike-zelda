package fr.uvsq.pglp.roguelike.donjon.elements;

import fr.uvsq.pglp.roguelike.ihm.EcranConsole;
import fr.uvsq.pglp.roguelike.personnage.Personnage;
import fr.uvsq.pglp.roguelike.personnage.ia.AmicalIa;
import fr.uvsq.pglp.roguelike.personnage.ia.DefensifIa;
import fr.uvsq.pglp.roguelike.personnage.ia.JoueurIa;
import fr.uvsq.pglp.roguelike.personnage.ia.NeutreIa;
import fr.uvsq.pglp.roguelike.personnage.ia.PersonnageIa;
import java.awt.Color;

/**
 * Affichage du {@link MorceauEtage} dans lequel se trouve le joueur.
 *
 * @author Tom Abbouz
 * @version Mars 2023
 */
public class AfficherElementEtage {

  private ElementEtage element;

  /**
   * Constructeur de AfficherElementEtage.
   *
   * @param element {@link ElementEtage} devant être affiché.
   */
  public AfficherElementEtage(ElementEtage element) {
    this.element = element;
  }

  public void afficher(EcranConsole console) {

    for (int j = 0; j < element.longueur(); j++) {

      for (int k = 0; k < element.largeur(); k++) {

        Proprietes p = new Proprietes(".", Color.GRAY);

        afficherMur(p, j, k);
        afficherOuvrables(p, j, k);
        afficherEchangeable(p, j, k);
        afficherPnj(p, j, k);
        afficherJoueur(p, j, k);

        console.print(p.getGlyph(), p.getCouleur());
      }
      console.println("");
    }
  }

  private void afficherMur(Proprietes p, int j, int k) {
    if (element.tiles(k, j).equals(Tile.WALL)) {
      p.setGlyph("#");
      p.setCouleur(Color.WHITE);
    }
  }


  private void afficherOuvrables(Proprietes p, int j, int k) {
    if (element.ouvrables(k, j) != null && !element.ouvrables(k, j).isOuverte()) {
      if (element.ouvrables(k, j) instanceof Porte) {
        p.setGlyph("0");
      } else if (element.ouvrables(k, j) instanceof Tresor) {
        p.setGlyph("*");
      }
      p.setCouleur(couleurOuvrable(element.tiles(k, j)));
    }
  }

  private Color couleurOuvrable(Tile tiles) {
    if (tiles.crochetable()) {
      return Color.RED;
    }
    return new Color(255, 100, 0);
  }

  private void afficherEchangeable(Proprietes p, int j, int k) {
    if (element.echangeables(k, j) != null) {
      p.setGlyph("" + element.echangeables(k, j).getGlyph());
      p.setCouleur(Color.GREEN);
    }
  }


  private void afficherPnj(Proprietes p, int j, int k) {
    if (element.personnages(k, j) != null) {
      String nom = element.personnages(k, j).getNom();
      p.setGlyph(nom.substring(0, 1));
      p.setCouleur(couleurPersonnage(element.personnages(k, j)));
    }
  }

  private Color couleurPersonnage(Personnage personnage) {
    PersonnageIa personnageIa = personnage.getIa();

    if (personnageIa instanceof JoueurIa) {
      return Color.WHITE;
    } else if (personnageIa instanceof DefensifIa) {
      return Color.ORANGE;
    } else if (personnageIa instanceof AmicalIa) {
      return Color.PINK;
    } else if (personnageIa instanceof NeutreIa) {
      return Color.ORANGE;
    } else {
      return Color.CYAN;
    }
  }

  private void afficherJoueur(Proprietes p, int j, int k) {
    if (element.personnages(k, j) == element.getJoueur()) {
      p.setGlyph("@");
      p.setCouleur(Color.WHITE);
    }
  }
}
