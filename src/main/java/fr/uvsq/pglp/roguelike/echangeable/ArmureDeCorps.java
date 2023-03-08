package fr.uvsq.pglp.roguelike.echangeable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Enumération de toute les {@link Armure}s de corps.
 *
 * <p>Elles ont toutes pour glyph '/'.</p>
 *
 * @author Tom Abbouz
 * @version Février 2023
 * @see Armure
 * @see Equipement
 */
public enum ArmureDeCorps implements Armure {

  TISSUMATELASSE("du tissu matellassé", 1, 2),
  CUIR("du cuir", 2, 4),
  CUIRRENFORCE("du cuir renforcé", 3, 8),
  CHEMISE("une chemise de mailles", 4, 15),
  COTTE("une cotte de mailles", 5, 20),
  DEMIPLAQUE("une demi-plaque", 6, 50),
  PLAQUE("une plaque complète", 8, 200);

  private static final List<ArmureDeCorps> VALUES 
      = Collections.unmodifiableList(Arrays.asList(values()));
  private static final int SIZE = VALUES.size();
  private static final Random RANDOM = new Random();
  private final String nom;
  private final int bonusDef;
  private final int prix;
  private final char glyph = '/';


  ArmureDeCorps(String nom, int bonusDef, int prix) {

    this.nom = nom;
    this.bonusDef = bonusDef;
    this.prix = prix;
  }

  /**
   * Renvoie une valeur d'{@link ArmureDeCorps} dont le prix est 
   * inférieur au cout indiqué choisi aléatoirement.
   *
   * @return {@link ArmureDeCorps} dont le prix est inférieur au cout indiqué.
   */
  public static ArmureDeCorps random(int cout) {

    ArrayList<ArmureDeCorps> possibles = new ArrayList<ArmureDeCorps>();

    for (int i = 0; i < SIZE; i++) {
      if (VALUES.get(i).prix < cout) {
        possibles.add(VALUES.get(i));
      }
    }

    if (possibles.size() == 0) {
      ArmureDeCorps b = VALUES.get(0);
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
  public int getBonusDef() {
    return bonusDef;
  }

  @Override
  public int getPrix() {
    return prix;
  }

  @Override
  public char getGlyph() {
    return glyph;
  }
}