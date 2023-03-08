package fr.uvsq.pglp.roguelike.personnage.attributs;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Enumération des 6 caractéristiques pour chaque 
 * {@link PersonnageDonjon}.
 *
 * @see PersonnageDonjon
 *
 * @author Tom Abbouz
 * @version Février 2023
 */
public enum Caracteristique {

  FOR("Force"),
  DEX("Dexterite"),
  CON("Constitution"),
  INT("Intelligence"),
  SAG("Sagesse"),
  CHA("Charisme");

  private String nom;

  private Caracteristique(String nomCaracteristique) {
    this.nom = nomCaracteristique;
  }

  public String getNom() {
    return nom;
  }
  
  /**
   * Renvoie un tableau avec toutes les valeurs de {@link Caractéristique}s
   * dans un ordre aléatoire.
   *
   * @return une tableau de {@link Caracteristique}.
   */
  public static Caracteristique[] randomOrder() {

    Caracteristique[] c = Caracteristique.values();
    List<Caracteristique> list = Arrays.asList(c);
    
    Collections.shuffle(list);
    list.toArray(c);
    
    return c;
  }
}
