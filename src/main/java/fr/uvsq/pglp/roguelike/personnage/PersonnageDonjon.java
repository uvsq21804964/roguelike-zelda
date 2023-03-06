package fr.uvsq.pglp.roguelike.personnage;

import fr.uvsq.pglp.roguelike.echangeable.ArmureDeCorps;
import fr.uvsq.pglp.roguelike.echangeable.Equipement;
import fr.uvsq.pglp.roguelike.personnage.ia.AgressifIa;
import fr.uvsq.pglp.roguelike.personnage.ia.AmicalIa;
import fr.uvsq.pglp.roguelike.personnage.ia.DefensifIa;
import fr.uvsq.pglp.roguelike.personnage.ia.JoueurIa;
import fr.uvsq.pglp.roguelike.personnage.ia.NeutreIa;
import fr.uvsq.pglp.roguelike.personnage.ia.PersonnageIa;

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
 * @see Personnage
 *
 * @author Tom Abbouz
 * @version Février 2023
 */
public class PersonnageDonjon implements Personnage {

  private final String nom;
  private final TypeIa typeIa;
  private PersonnageIa Ia;

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
     * @param pointsDeVieDepart Points de vie de départ du personnage
     * @param pointsDeVieMax Nombre maximum de points de vie du personnage
     */
    public PersonnageBuilder(String nom, int axeX, int axeY) {
      this.nom = nom;
      this.axeX = axeX;
      this.axeY = axeY;
      this.typeIa = TypeIa.AGRESSIF;
    }
    
    public PersonnageBuilder setIa(TypeIa typeIA) {
      this.typeIa = typeIA;
      return this;
    }

    public PersonnageDonjon build() {
      return new PersonnageDonjon(this);
    }
  }
  
 
  public void genererPersonnageIa() {
    switch(typeIa) {
    case AGRESSIF:
      Ia = new AgressifIa(this);
      break;
    case AMICAL:
      Ia = new AmicalIa(this);
      break;
    case DEFENSIF:
      Ia = new DefensifIa(this);
      break;
    case JOUEUR:
      Ia = new JoueurIa(this);
      break;
    case NEUTRE:
      Ia = new NeutreIa(this);
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
  public int getY() {
    return axeY;
  }

  @Override
  public void setPosition(int x, int y) {
    this.axeX = x;
    this.axeY = y;
  }

  @Override
  public PersonnageIa getIa() {
    return Ia;
  }
}

