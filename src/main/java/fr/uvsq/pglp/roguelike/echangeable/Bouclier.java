package fr.uvsq.pglp.roguelike.echangeable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Enumération de toute les {@link Armure}s de type bouclier.
 *
 * <p> Tous les boucliers ont pour glyph ','.</p>
 * <p> Le bonus de défense procuré par un bouclier s'ajoute à l'
 * {@link Armure}.</p>
 * <p> Un bouclier nécessite d'avoir une main de disponible pour être porté.</p>
 *
 * @author Tom Abbouz
 * @version Février 2023
 * @see Armure
 * @see Equipement
 */
public enum Bouclier implements Armure {

  PETITBOUCLIER("petit bouclier", 1, 2),
  GRANDBOUCLIER("grand bouclier", 2, 4);

  private static final List<Bouclier> VALUES 
      = Collections.unmodifiableList(Arrays.asList(values()));
  private static final int SIZE = VALUES.size();
  private static final Random RANDOM = new Random();
  private final String nom;
  private final int bonusDef;
  private final int prix;
  private final char glyph = ',';


  Bouclier(String nom, int bonusDef, int prix) {

    this.nom = nom;
    this.bonusDef = bonusDef;
    this.prix = prix;
  }

  public static Bouclier random() {

    ArrayList<Bouclier> possibles = new ArrayList<Bouclier>();

    for (int i = 0; i < SIZE; i++) {
      if (VALUES.get(i).prix < 6) {
        possibles.add(VALUES.get(i));
      }
    }

    if (possibles.size() == 0) {
      Bouclier b = VALUES.get(0);
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