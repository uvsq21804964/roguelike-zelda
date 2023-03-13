package fr.uvsq.pglp.roguelike.donjon.elements;

import fr.uvsq.pglp.roguelike.donjon.MorceauEtage;
import fr.uvsq.pglp.roguelike.ihm.EcranConsole;
import fr.uvsq.pglp.roguelike.personnage.Personnage;
import fr.uvsq.pglp.roguelike.personnage.ia.AgressifIa;
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
    
    for (int k = 0; k < element.longueur(); k++) {

      for (int j = 0; j < element.largeur(); j++) {

        Proprietes p = new Proprietes(".", Color.GRAY);

        afficherMur(p, j, k);
        afficherOuvrables(p, j, k);
        afficherEchangeable(p, j, k);
        afficherPnj(p, j, k);
        afficherJoueur(p, j, k);
        
        if(p.getGlyph().equals(".")) {
          verifierZoneDefense(p, j, k);
        }

        console.print(p.getGlyph(), p.getCouleur());
      }
      console.println("");
    }
  }

  private void verifierZoneDefense(Proprietes p, int j, int k) {
    
    MorceauEtage e = (MorceauEtage) element;
    
    if(e.zoneDefense(j,k)) {
      p.setGlyph("#");
      p.setCouleur(Color.ORANGE);
    }
  }

  private void afficherMur(Proprietes p, int j, int k) {
    if (element.tiles(j, k).equals(Tile.WALL)) {
      p.setGlyph("#");
      p.setCouleur(Color.WHITE);
    }
  }


  private void afficherOuvrables(Proprietes p, int j, int k) {
    
    Ouvrable ouvrable = element.ouvrables(j, k);
    if (ouvrable != null && !ouvrable.isOuverte()) {
      if (ouvrable instanceof Porte) {
        p.setGlyph("0");
      } else if (ouvrable instanceof Tresor) {
        p.setGlyph("*");
      }
      if(element.ouvrables(j, k).isOuverte()) {
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
    if (element.echangeables(j, k) != null) {
      p.setGlyph("" + element.echangeables(j, k).getGlyph());
      p.setCouleur(Color.GREEN);
    }
  }


  private void afficherPnj(Proprietes p, int j, int k) {
    if (element.personnages(j, k) != null) {
      String nom = element.personnages(j, k).getNom();
      p.setGlyph(nom.substring(0, 1));
      p.setCouleur(couleurPersonnage(element.personnages(j, k)));
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
    } else if (personnageIa instanceof AgressifIa) {
      return new Color(180, 0, 180);
    } else {
      return Color.CYAN;
    }
  }

  private void afficherJoueur(Proprietes p, int j, int k) {
    if (element.personnages(j, k) == element.getJoueur()) {
      if (element.personnages(j, k).getIa() instanceof JoueurIa) {
        p.setGlyph("@");
        p.setCouleur(Color.WHITE);
      }
    }
  }
}
