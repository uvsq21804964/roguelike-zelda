package fr.uvsq.pglp.roguelike.personnage;

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
}
