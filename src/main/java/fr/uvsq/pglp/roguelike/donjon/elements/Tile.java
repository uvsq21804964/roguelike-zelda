package fr.uvsq.pglp.roguelike.donjon.elements;

/**
 * Cette classe représente les différents types de tuiles
 * étant affichées : un glyphe et une couleur pour l'afficher.
 * Les tuiles sont représentées sous la forme d'une énumération
 * car leur variété est en nombre très limité.
 *
 * @author Tom Abbouz
 * @version Janvier 2023
 */
public enum Tile {
  FLOOR("un magnifique carrelage"),
  WALL("une paroi"),
  UNKNOWN("un lieu inconnu"),
  CHEST("un trésor à ouvrir"),
  BOX("un trésor à forcer"),
  PORTE("une porte à ouvrir"),
  PORTEACROCHETER("une porte à crocheter");

  private final String nom;

  Tile(String nom) {
    this.nom = nom;
  }

  public boolean isGround() {
    return this != WALL && this != PORTE && this != PORTEACROCHETER;
  }

  public boolean isSautable() {
    return this != WALL && this != PORTE && this != PORTEACROCHETER;
  }

  public boolean ouvrable() {
    return (this.equals(PORTE) || this.equals(CHEST));
  }

  public boolean crochetable() {
    return this.equals(PORTEACROCHETER);
  }

  public boolean forcable() {
    return this.equals(BOX);
  }

  public boolean isTresor() {
    return (this.equals(BOX) || this.equals(CHEST));
  }

  public String nom() {
    return nom;
  }

  public boolean isPorte() {
    return (this.equals(PORTE) || this.equals(PORTEACROCHETER));
  }
}