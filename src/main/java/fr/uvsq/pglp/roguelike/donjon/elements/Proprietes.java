package fr.uvsq.pglp.roguelike.donjon.elements;

import java.awt.Color;

public class Proprietes {
  
  private String glyph;
  private Color couleur;
  
  protected Proprietes(String glyph, Color couleur) {
    this.glyph = glyph;
    this.couleur = couleur;
  }
  
  protected void setGlyph(String glyph) {
    this.glyph = glyph;
  }
  
  protected void setCouleur(Color couleur) {
    this.couleur = couleur;
  }
  
  protected String  getGlyph() {
    return glyph;
  }
  
  protected Color getCouleur() {
    return couleur;
  }

}
