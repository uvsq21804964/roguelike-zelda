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
 * @see Armure
 * @see Equipement
 *
 * @author Tom Abbouz
 * @version Février 2023
 */
public enum ArmureDeCorps implements Armure {

  TISSUMATELASSE("tissu matellasse", 1, 2),
  CUIR("cuir", 2, 4),
  CUIRRENFORCE("cuir renforce", 3, 8),
  CHEMISE("chemise de mailles", 4, 15),
  COTTE("cotte de mailles", 5, 20),
  DEMIPLAQUE("demi-plaque", 6, 50),
  PLAQUE("plaque complete", 8, 200);

  private final String nom;
  private final int bonusDef;
  private final int prix;
  private final char glyph = '/';

  ArmureDeCorps(String nom, int bonusDef, int prix) {

    this.nom = nom;
    this.bonusDef = bonusDef;
    this.prix = prix;
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
  
  private static final List<ArmureDeCorps> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
  private static final int SIZE = VALUES.size();
  private static final Random RANDOM = new Random();

  public static ArmureDeCorps random()  {
    
    ArrayList<ArmureDeCorps> possibles = new ArrayList<ArmureDeCorps>();
 
    for(int i = 0 ; i < SIZE ; i++) {
      if(VALUES.get(i).prix < 6) {
        possibles.add(VALUES.get(i));
      }
    }
    
    if(possibles.size() == 0) {
      ArmureDeCorps b = VALUES.get(0);
      for(int i = 1 ; i < SIZE ; i++) {
        if(b.prix > VALUES.get(i).prix) {
          b = VALUES.get(i);
        }
      }
      return b;
    }
    
    int r = RANDOM.nextInt(possibles.size());
    return VALUES.get(r);
  }
}