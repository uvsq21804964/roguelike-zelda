package fr.uvsq.pglp.roguelike.echangeable;


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

}
