package fr.uvsq.pglp.roguelike.personnage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Enumeration des différents types d'intelligence artificielle
 * pour {@link PersonnageDonjon}.
 *
 * @see PersonnageIa
 *
 * @author Tom Abbouz
 * @version Mars 2023
 */
public enum TypeIa {
  AGRESSIF,
  AMICAL,
  DEFENSIF,
  JOUEUR,
  NEUTRE;

  private static final List<TypeIa> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
  private static final int SIZE = VALUES.size();
  private static final Random RANDOM = new Random();

  /**
   * Renvoie une valeur de {@link TypeIa} aléatoirement, hormis
   * AGRESSIF et JOUEUR.
   *
   * @return une valeur de {@link TypeIa} aléatoire (hormis AGRESSIF et JOUEUR)
   */
  public static TypeIa random() {

    TypeIa a = JOUEUR;

    while (a.equals(JOUEUR) || a.equals(AGRESSIF)) {
      a = VALUES.get(RANDOM.nextInt(SIZE));
    }
    return a;
  }
}
