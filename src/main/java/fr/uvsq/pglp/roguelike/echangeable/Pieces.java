package fr.uvsq.pglp.roguelike.echangeable;

/**
 * Représentation d'une pièce d'argent, la monnaie du jeu.
 *
 * <p>On peut les utiliser pour acheter des équipements à certains PNJ,
 * ou en recevoir si on lui en vend. Les PNJ laissent, parfois, des pièces (quand on
 * les tue pour les PNJ ennemis et par simple générosité pour les PNJ amicaux).
 * On peut aussi trouver des pièces dans les trésors représentés par un 'X' ou un'x'.</p>
 *
 * @author Tom Abbouz
 * @version Février 2023
 */
public class Pieces implements Echangeable {

  private int valeur;

  public Pieces(int valeur) {
    this.valeur = valeur;
  }

  @Override
  public char getGlyph() {
    return '$';
  }

  @Override
  public String getNom() {
    return (valeur + " pièces d'argent");
  }

  public int getValeur() {
    return valeur;
  }

}
