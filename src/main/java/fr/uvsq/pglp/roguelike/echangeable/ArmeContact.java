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

  BATON("baton", new De(4), true, 0),
  BATONFERRE("baton ferre", new De(4), true, 2),
  DAGUE("dague", new De(4), false, 3),
  EPEE2MAINS("epee a deux mains", new De(2, 6), true, 10),
  EPEECOURTE("epee courte", new De(6), false, 5),
  EPEELONGUE("epee longue", new De(8), false, 6),
  GOURDIN("gourdin", new De(4), false, 1),
  HACHE("hache", new De(8), false, 6),
  HACHE2MAINS("hache a deux mains", new De(2, 6), true, 10),
  KATANA("katana", new De(10), true, 12),
  MARTEAUDEGUERRE("marteau de guerre", new De(4), false, 12),
  MASSEDARMES("masse d'armes", new De(6), false, 4),
  RAPIERE("rapiere", new De(6), false, 6),
  VIVELAME("vivelame", new De(10), true, 12);

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

  public static ArmeContact random() {

    ArrayList<ArmeContact> possibles = new ArrayList<ArmeContact>();

    for (int i = 0; i < SIZE; i++) {
      if (VALUES.get(i).prix < 6) {
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
  public int getDe() {
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
}