package fr.uvsq.pglp.roguelike.echangeable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Enumération de toute les {@link ArmeDistance}s dont le rechargement
 * est simple.
 *
 * <p>Elles ont toutes pour glyph '!'.</p>
 *
 * @author Tom Abbouz
 * @version Février 2023
 * @see Arme
 * @see ArmeDistance
 * @see Equipement
 */
public enum ArmeRechargementSimple implements ArmeDistance {

  ARBALETEPOING("une arbalète de poing", new De(6), false, 10.0, 8),
  ARBALETELEGERE("une arbalète légère", new De(2, 4), true, 30.0, 10);

  private static final List<ArmeRechargementSimple> VALUES 
      = Collections.unmodifiableList(Arrays.asList(values()));
  private static final int SIZE = VALUES.size();
  private static final Random RANDOM = new Random();
  private final String nom;
  private final De de;
  private final boolean deuxMains;
  private final double portee;
  private final int prix;
  private final char glyph = '!';

  ArmeRechargementSimple(String nom, De de, boolean deuxMains, double portee, int prix) {
    this.nom = nom;
    this.de = de;
    this.deuxMains = deuxMains;
    this.portee = portee;
    this.prix = prix;
  }

  /**
   * Renvoie une valeur d'{@link ArmeRechargementSimple} dont le prix est 
   * inférieur au cout indiqué choisi aléatoirement.
   *
   * @return {@link ArmeRechargementSimple} dont le prix est inférieur au cout indiqué.
   */
  public static ArmeRechargementSimple random(int cout) {

    ArrayList<ArmeRechargementSimple> possibles = new ArrayList<ArmeRechargementSimple>();

    for (int i = 0; i < SIZE; i++) {
      if (VALUES.get(i).prix < cout) {
        possibles.add(VALUES.get(i));
      }
    }

    if (possibles.size() == 0) {
      ArmeRechargementSimple b = VALUES.get(0);
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
  public int getTirage() {
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
  
  @Override
  public int getTirageMax() {
    return de.valeurMax();
  }
}