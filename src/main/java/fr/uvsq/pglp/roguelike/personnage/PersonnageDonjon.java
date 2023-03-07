package fr.uvsq.pglp.roguelike.personnage;

import fr.uvsq.pglp.roguelike.donjon.MorceauEtage;
import fr.uvsq.pglp.roguelike.donjon.elements.ElementEtage;
import fr.uvsq.pglp.roguelike.donjon.elements.Ouvrable;
import fr.uvsq.pglp.roguelike.donjon.elements.Porte;
import fr.uvsq.pglp.roguelike.donjon.elements.Tile;
import fr.uvsq.pglp.roguelike.echangeable.ArmureDeCorps;
import fr.uvsq.pglp.roguelike.echangeable.Equipement;
import fr.uvsq.pglp.roguelike.personnage.ia.AgressifIa;
import fr.uvsq.pglp.roguelike.personnage.ia.AmicalIa;
import fr.uvsq.pglp.roguelike.personnage.ia.DefensifIa;
import fr.uvsq.pglp.roguelike.personnage.ia.JoueurIa;
import fr.uvsq.pglp.roguelike.personnage.ia.NeutreIa;
import fr.uvsq.pglp.roguelike.personnage.ia.PersonnageIa;
import java.util.ArrayList;

/**
 * Cette classe représente un personnage (joueur ou PNJ) de donjon.
 *
 * <p>Un personnage de donjon possède un nom, des points de vie,
 * 6 {@link Caracteristique}s, trois scores d'attaque (contact, distance, magique),
 * des points d'initiative et de défense fixes, une bourse pouvant contenir des
 * pièces d'argent, un {@link Sac} pouvant contenir 20 {@link Equipement}s, une position
 * et une certaine IA ({@link PersonnageIa}).</p>
 *
 * <p>De plus, chaque personnage de donjon peut porter une {@link ArmureDeCorps} à la fois,
 * et possède deux mains pour porter des armes et des boucliers.</p>
 *
 * @author Tom Abbouz
 * @version Février 2023
 * @see Personnage
 */
public class PersonnageDonjon implements Personnage {

  private final String nom;
  private final TypeIa typeIa;
  private PersonnageIa intArt;
  private MorceauEtage morceauEtage;

  private int axeX;
  private int axeY;

  PersonnageDonjon(PersonnageBuilder builder) {

    // Paramètres requis
    this.nom = builder.nom;
    this.axeX = builder.axeX;
    this.axeY = builder.axeY;
    this.typeIa = builder.typeIa;
    genererPersonnageIa();
  }

  public void genererPersonnageIa() {
    switch (typeIa) {
      case AGRESSIF:
        intArt = new AgressifIa(this);
        break;
      case AMICAL:
        intArt = new AmicalIa(this);
        break;
      case DEFENSIF:
        intArt = new DefensifIa(this);
        break;
      case JOUEUR:
        intArt = new JoueurIa(this, new ArrayList<String>());
        break;
      case NEUTRE:
        intArt = new NeutreIa(this);
        break;
      default:
        break;
    }
  }

  @Override
  public String getNom() {
    return nom;
  }

  @Override
  public int getX() {
    return axeX;
  }

  @Override
  public void setX(int x) {
    axeX = x;
  }

  @Override
  public int getY() {
    return axeY;
  }

  @Override
  public void setY(int y) {
    axeY = y;
  }

  @Override
  public void setPosition(int x, int y) {
    this.axeX = x;
    this.axeY = y;
  }

  @Override
  public PersonnageIa getIa() {
    return intArt;
  }

  public void moveBy(int mx, int my) {
    if (mx == 0 && my == 0) {
      return;
    }

    Tile tile = morceauEtage.tiles(axeX + mx, axeY + my);

    Ouvrable porte = morceauEtage.ouvrables(axeX + mx, axeY + my);
    if (tile.equals(Tile.WALL) || (porte != null && !porte.isOuverte() && porte instanceof Porte)) {
      intArt.notifier("C'est " + tile.nom() + " !");
    }
    Personnage other = morceauEtage.personnages(axeX + mx, axeY + my);

    if (other == null) {
      intArt.onEnter(axeX + mx, axeY + my, tile);
    } else {
      intArt.notifier("Il y a déjà " + other.getNom() + " ici.");
    }
  }

  @Override
  public void morceauEtage(ElementEtage elementEtage) {
    this.morceauEtage = (MorceauEtage) elementEtage;
  }

  public MorceauEtage getMorceauEtage() {
    return morceauEtage;
  }

  @Override
  public void notifier(String message, Object... params) {
    intArt.notifier(String.format(message, params));
  }

  @Override
  public ElementEtage getElementEtage() {
    return morceauEtage;
  }

  /**
   * <b>Classe static Builder</b> de {@link PersonnageDonjon}.
   *
   * <p>On utilise le patron <b>Builder</b> afin d'avoir de pouvoir créer
   * des {@link PersonnageDonjon}s par défaut facilement tout en pouvant les
   * personnaliser facilement.</p>
   *
   * @author Tom Abbouz
   * @version Février 2023
   */
  public static class PersonnageBuilder {

    // Paramètres requis
    private final String nom;
    private int axeX;
    private int axeY;
    private TypeIa typeIa;

    /**
     * Constructeur de PersonnageBuilder.
     *
     * @param nom Le nom du personnage.
     */
    public PersonnageBuilder(String nom, int axeX, int axeY) {
      this.nom = nom;
      this.axeX = axeX;
      this.axeY = axeY;
      this.typeIa = TypeIa.AGRESSIF;
    }

    public PersonnageBuilder setIa(TypeIa typeIa) {
      this.typeIa = typeIa;
      return this;
    }

    public PersonnageDonjon build() {
      return new PersonnageDonjon(this);
    }
  }
}

