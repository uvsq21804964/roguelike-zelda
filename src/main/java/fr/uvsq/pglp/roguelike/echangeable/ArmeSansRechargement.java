package fr.uvsq.pglp.roguelike.echangeable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Enumération de toute les {@link ArmeDistance}s n'ayant pas besoin
 * d'être rechargé.
 *
 * <p>Elles ont toutes pour glyph '?'.</p>
 *
 * @author Tom Abbouz
 * @version Février 2023
 * @see Arme
 * @see ArmeDistance
 * @see Equipement
 */
public enum ArmeSansRechargement implements ArmeDistance {

  ARCCOURT("un arc court", new De(6), true, 30.0, 4),
  ARCLONG("un arc long", new De(8), true, 50.0, 10),
  FRONDE("une fronde", new De(4), false, 5.0, 0),
  HACHETTE("une hachette", new De(6), false, 5.0, 2),
  JAVELOT("un javelot", new De(6), false, 20.0, 6);

  private static final List<ArmeSansRechargement> VALUES 
      = Collections.unmodifiableList(Arrays.asList(values()));
  private static final int SIZE = VALUES.size();
  private static final Random RANDOM = new Random();
  private final String nom;
  private final De de;
  private final boolean deuxMains;
  private final double portee;
  private final int prix;
  private final char glyph = '?';

  ArmeSansRechargement(String nom, De de, boolean deuxMains, double portee, int prix) {
    this.nom = nom;
    this.de = de;
    this.deuxMains = deuxMains;
    this.portee = portee;
    this.prix = prix;
  }

  /**
   * Renvoie une valeur d'{@link ArmeSansRechargement} dont le prix est 
   * inférieur au cout indiqué choisi aléatoirement.
   *
   * @return {@link ArmeSansRechargement} dont le prix est inférieur au cout indiqué.
   */
  public static ArmeSansRechargement random(int cout) {

    ArrayList<ArmeSansRechargement> possibles = new ArrayList<ArmeSansRechargement>();

    for (int i = 0; i < SIZE; i++) {
      if (VALUES.get(i).prix < cout) {
        possibles.add(VALUES.get(i));
      }
    }

    if (possibles.size() == 0) {
      ArmeSansRechargement b = VALUES.get(0);
      for (int i = 1; i < SIZE; i++) {
        if (b.prix > VALUES.get(i).prix) {
          b = VALUES.get(i);
        }
      }
      return b;
    }

    int r = RANDOM.nextInt(possibles.size());
    return VALUES.get(r);
  }

  @Override
  public String getNom() {
    return nom;
  }

  @Override
  public int getDe() {
    return de.tirage();
  }

  @Override
  public boolean isDeuxMains() {
    return deuxMains;
  }

  @Override
  public double getPortee() {
    return portee;
  }

  @Override
  public int getPrix() {
    return prix;
  }

  @Override
  public int recharger() {
    return 0;
  }

  @Override
  public char getGlyph() {
    return glyph;
  }
}