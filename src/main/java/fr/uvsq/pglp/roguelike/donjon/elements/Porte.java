package fr.uvsq.pglp.roguelike.donjon.elements;

import fr.uvsq.pglp.roguelike.echangeable.Echangeable;

/**
 * Représentation d'unz porte.
 *
 * <p>Une porte peut être ouverte ou fermée, et peut nécessiter d'être
 * crocheter selon son type. Son type est reconnu par un {@link Tile}.</p>
 *
 * @author Tom Abbouz
 * @version Mars 2023
 */
public class Porte implements Ouvrable {

  private Tile type;
  private Salle salle;
  private Couloir couloir;
  private boolean ouverte = false;
  private Difficulte difficulte = null;

  /**
   * Constructeur de Porte.
   *
   * @param type Le type de porte, crochetable ou classique.
   * @param salle La {@link Salle} que la porte relie.
   * @param couloir La {@link Couloir} que la porte relie.
   */
  public Porte(Tile type, Salle salle, Couloir couloir) {
    this.type = type;
    this.salle = salle;
    this.couloir = couloir;
    if(!type.equals(Tile.PORTE)) {
      this.difficulte = Difficulte.random();
    }
  }

  @Override
  public Tile getType() {
    return type;
  }

  public Salle getSalle() {
    return salle;
  }

  public Couloir getCouloir() {
    return couloir;
  }

  @Override
  public boolean isOuverte() {
    return ouverte;
  }

  @Override
  public Echangeable ouvrir() {
    this.ouverte = true;
    return null;
  }

  @Override
  public boolean tirage(int mod) {
    int tirage = ((int) (Math.random()*20)+1);
    if(difficulte.getValeur() <= (tirage + mod)) {
      return true;
    }
    return false;
  }

  @Override
  public String getDifficulte() {
    return difficulte.getString();
  }

  @Override
  public int getDiffValeur() {
    return difficulte.getValeur();
  }
}
