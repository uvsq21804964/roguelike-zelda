package fr.uvsq.pglp.roguelike.echangeable;

/**
 * Enumération de toute les {@link Armure}s de type bouclier.
 *
 * <p> Tous les boucliers ont pour glyph ','.</p>
 * <p> Le bonus de défense procuré par un bouclier s'ajoute à l'
 * {@link Armure}.</p>
 * <p> Un bouclier nécessite d'avoir une main de disponible pour être porté.</p>
 *
 * @see Armure
 * @see Equipement
 *
 * @author Tom Abbouz
 * @version Février 2023
 */
public enum Bouclier implements Armure {

  PETITBOUCLIER("petit bouclier", 1, 2),
  GRANDBOUCLIER("grand bouclier", 2, 4);

  private final String nom;
  private final int bonusDef;
  private final int prix;
  private final char glyph = ',';

  Bouclier(String nom, int bonusDef, int prix) {

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
