package fr.uvsq.pglp.roguelike.echangeable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Enumération de toute les {@link Arme}s de contact.
 *
 * <p>Elles ont toutes pour glyph ':'.</p>
 *
 * @author Tom Abbouz
 * @version Février 2023
 * @see Arme
 * @see Equipement
 */
public enum ArmeContact implements Arme {

  BATON("un baton", new De(4), true, 0),
  BATONFERRE("un baton ferre", new De(4), true, 2),
  DAGUE("une dague", new De(4), false, 3),
  EPEE2MAINS("une épée a deux mains", new De(2, 6), true, 10),
  EPEECOURTE("une épée courte", new De(6), false, 5),
  EPEELONGUE("une épée longue", new De(8), false, 6),
  GOURDIN("un gourdin", new De(4), false, 1),
  HACHE("une hache", new De(8), false, 6),
  HACHE2MAINS("une hache à deux mains", new De(2, 6), true, 10),
  KATANA("un katana", new De(10), true, 12),
  MARTEAUDEGUERRE("un marteau de guerre", new De(4), false, 12),
  MASSEDARMES("une masse d'armes", new De(6), false, 4),
  RAPIERE("une rapière", new De(6), false, 6),
  VIVELAME("un vivelame", new De(10), true, 12);

  private static final List<ArmeContact> VALUES 
      = Collections.unmodifiableList(Arrays.asList(values()));
  private static final int SIZE = VALUES.size();
  private static final Random RANDOM = new Random();
  private final String nom;
  private final De de;
  private final boolean deuxMains;
  private final int prix;
  private final char glyph = ':';

  ArmeContact(String nom, De de, boolean deuxMains, int prix) {
    this.nom = nom;
    this.de = de;
    this.deuxMains = deuxMains;
    this.prix = prix;
  }

  /**
   * Renvoie une valeur d'{@link ArmeContact} dont le prix est 
   * inférieur au cout indiqué choisi aléatoirement.
   *
   * @return {@link ArmeContact} dont le prix est inférieur au cout indiqué.
   */
  public static ArmeContact random(int cout) {

    ArrayList<ArmeContact> possibles = new ArrayList<ArmeContact>();

    for (int i = 0; i < SIZE; i++) {
      if (VALUES.get(i).prix < cout) {
        possibles.add(VALUES.get(i));
      }
    }

    if (possibles.size() == 0) {
      ArmeContact b = VALUES.get(0);
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
  public int getPrix() {
    return prix;
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