package fr.uvsq.pglp.roguelike.echangeable;


/**
 * Enumération de toute les {@link ArmeDistance}s dont le rechargement 
 * est limité.
 *
 * <p>Elles ont toutes pour glyph ';'.</p>
 *
 * @see Arme
 * @see ArmeDistance
 * @see Equipement
 *
 * @author Tom Abbouz
 * @version Février 2023
 */
public enum ArmeRechargementLimite implements ArmeDistance {

  ARBALETELOURDE("arbalete lourde", new De(3, 4), true, 60.0, 12),
  MOUSQUET("mousquet", new De(2, 6), true, 50.0, 20),
  PETOIRE("petoire", new De(10), false, 20.0, 15);

  private final String nom;
  private final De de;
  private final boolean deuxMains;
  private final double portee;
  private final int prix;
  private final char glyph = ';';

  ArmeRechargementLimite(String nom, De de, boolean deuxMains, double portee, int prix) {
    this.nom = nom;
    this.de = de;
    this.deuxMains = deuxMains;
    this.portee = portee;
    this.prix = prix;
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
  public double getPortee() {
    return portee;
  }

  @Override
  public int getPrix() {
    return prix;
  }

  @Override
  public int recharger() {
    return 0;
  }

  @Override
  public char getGlyph() {
    return glyph;
  }
}

