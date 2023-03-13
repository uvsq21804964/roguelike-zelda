package fr.uvsq.pglp.roguelike.personnage.ia;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Difficulte {
  
  FACILE("facile", 5),
  MOYEN("assez rude", 10),
  DIFFICILE("difficile", 15),
  TRESDIFF("très difficile", 20),
  EXTDIFF("extrêmement difficile", 25),
  IMPOSSIBLE("quasiment impossible", 30);

  private String difficulte;
  private int valeur;
  
  private static final List<Difficulte> VALUES 
      = Collections.unmodifiableList(Arrays.asList(values()));
  private static final int SIZE = VALUES.size();
  private static final Random RANDOM = new Random();
  
  
  Difficulte(String difficulte, int valeur) {
    this.difficulte = difficulte;
    this.valeur = valeur;
  }
  
  public String getString() {
    return difficulte;
  }
  
  public int getValeur() {
    return valeur;
  }

  public static Difficulte random() {
    int r = RANDOM.nextInt(SIZE);
    return VALUES.get(r);
  }

  boolean tirage(int mod) {
    int tirage = ((int) (Math.random()*20)+1);
    if(valeur <= (tirage + mod)) {
      return true;
    }
    return false;
  }
  
  
}