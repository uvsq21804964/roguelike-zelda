package fr.uvsq.pglp.roguelike.echangeable;


/**
 * Enumération de toute les {@link ArmeDistance}s n'ayant pas besoin
 * d'être rechargé.
 *
 * <p>Elles ont toutes pour glyph '?'.</p>
 *
 * @see Arme
 * @see ArmeDistance
 * @see Equipement
 *
 * @author Tom Abbouz
 * @version Février 2023
 */
public enum ArmeSansRechargement implements ArmeDistance {

  ARCCOURT("arc court", new De(6), true, 30.0, 4),
  ARCLONG("arc long", new De(8), true, 50.0, 10),
  FRONDE("fronde", new De(4), false, 5.0, 0),
  HACHETTE("hachette", new De(6), false, 5.0, 2),
  JAVELOT("javelot", new De(6), false, 20.0, 6);

  private final String nom;
  private final De de;
  private final boolean deuxMains;
  private final double portee;
  private final int prix;
  private final char glyph = '?';

  ArmeSansRechargement(String nom, De de, boolean deuxMains, double portee, int prix) {
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

