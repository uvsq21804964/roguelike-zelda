package fr.uvsq.pglp.roguelike.donjon.elements;

import java.awt.Color;

/**
 * Classe pour afficher un élément du jeu ({@link Personnage}, 
 * {@link Echangeable} ou {@link Tuile}).
 *
 * <p>L'affichage d'un élément nécessite un glyph et une couleur.</p>
 *
 * @see AfficherElementEtage#afficher(fr.uvsq.pglp.roguelike.ihm.EcranConsole)
 * @author Tom Abbouz
 * @version Mars 2023
 */
public class Proprietes {

  private String glyph;
  private Color couleur;

  protected Proprietes(String glyph, Color couleur) {
    this.glyph = glyph;
    this.couleur = couleur;
  }

  protected String getGlyph() {
    return glyph;
  }

  protected void setGlyph(String glyph) {
    this.glyph = glyph;
  }

  protected Color getCouleur() {
    return couleur;
  }

  protected void setCouleur(Color couleur) {
    this.couleur = couleur;
  }

}
