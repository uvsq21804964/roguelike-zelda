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

  /**
   * Affiche l'{@link ElementEtage} dans lequel se trouve le joueur.
   *
   * @param console Console sur laquelle on affiche l'{@link ElementEtage}.
   */
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
    
    Ouvrable ouvrable = element.ouvrables(k, j);
    if (ouvrable != null && !ouvrable.isOuverte()) {
      if (ouvrable instanceof Porte) {
        p.setGlyph("0");
      } else if (ouvrable instanceof Tresor) {
        p.setGlyph("*");
      }
      if(element.ouvrables(k, j).isOuverte()) {
        p.setCouleur(Color.GREEN);
      } else {
        p.setCouleur(couleurOuvrable(ouvrable.getType()));
      }
    }
  }

  private Color couleurOuvrable(Tile tiles) {
    if (tiles.crochetable() || tiles.forcable()) {
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
      if (element.personnages(k, j).getIa() instanceof JoueurIa) {
        p.setGlyph("@");
        p.setCouleur(Color.WHITE);
      }
    }
  }
}
