package fr.uvsq.pglp.roguelike.donjon.elements;

public class CouloirAvecEnnemis extends Couloir {

  public CouloirAvecEnnemis(Salle salle1, Salle salle2, int nbEnnemis, int largeur, int longueur) {
    super(salle1, salle2, 0, nbEnnemis, largeur, longueur);
  }
}