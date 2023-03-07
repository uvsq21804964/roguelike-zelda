package fr.uvsq.pglp.roguelike.personnage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum TypeIa {
  AGRESSIF,
  AMICAL,
  DEFENSIF,
  JOUEUR,
  NEUTRE;

  private static final List<TypeIa> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
  private static final int SIZE = VALUES.size();
  private static final Random RANDOM = new Random();

  public static TypeIa random() {

    TypeIa a = JOUEUR;

    while (a.equals(JOUEUR) || a.equals(AGRESSIF)) {
      a = VALUES.get(RANDOM.nextInt(SIZE));
    }
    return a;
  }
}
