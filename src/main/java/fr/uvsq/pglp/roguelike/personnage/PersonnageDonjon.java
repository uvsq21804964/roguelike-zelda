package fr.uvsq.pglp.roguelike.personnage;

import fr.uvsq.pglp.roguelike.echangeable.Arme;
import fr.uvsq.pglp.roguelike.echangeable.Armure;
import fr.uvsq.pglp.roguelike.echangeable.ArmureDeCorps;
import fr.uvsq.pglp.roguelike.echangeable.Bouclier;
import fr.uvsq.pglp.roguelike.echangeable.Equipement;
import fr.uvsq.pglp.roguelike.personnage.ia.PersonnageIa;
import java.util.Arrays;

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
  private Jauge pointsDeVie;

  private ScoreDeCaracteristique[] caracteristiques;
  private Caracteristique[] priorite;
  private ScoreAttaqueContact contact;
  private ScoreAttaqueDistance distance;
  private ScoreAttaqueMagique magique;
  private int initiativeBase;
  private int defenseBase;
  private Jauge bourse;
  private Sac sac;
  private Armure[] armures = { null, null };
  private Arme[] armes = { null, null };
  private PersonnageIa ia;
  private int axeX;
  private int axeY;

  PersonnageDonjon(PersonnageBuilder builder) {

    // Paramètres requis
    this.nom = builder.nom;
    this.pointsDeVie = builder.pointsDeVie;

    // Paramètres optionnels
    this.caracteristiques = builder.caracteristiques;
    this.initiativeBase = builder.initiativeBase;
    this.defenseBase = builder.defenseBase;
    this.priorite = builder.priorite;
    this.contact = builder.contact;
    this.distance = builder.distance;
    this.magique = builder.magique;
    this.sac = builder.sac;
    this.bourse = builder.bourse;
  }

  public void setPersonnageIa(PersonnageIa ia) {
    this.ia = ia;
  }

  public Jauge getPointsDeVie() {
    return pointsDeVie;
  }

  public Sac getSac() {
    return sac;
  }

  public String getNom() {
    return nom;
  }

  public int getInitiativeBase() {
    return initiativeBase;
  }

  public int getDefenseBase() {
    return defenseBase;
  }

  public ScoreDeCaracteristique[] getCaracteristiques() {
    return caracteristiques;
  }

  public Caracteristique[] getPriorite() {
    return priorite;
  }

  public int contact() {
    return contact.attaque();
  }

  public int contactSansEquipements() {
    return contact.attaqueSansEquipements();
  }

  public int distance() {
    return distance.attaque();
  }

  public int distanceSansEquipements() {
    return distance.attaqueSansEquipements();
  }

  public int magique() {
    return magique.attaque();
  }

  public int magiqueSansEquipements() {
    return magique.attaqueSansEquipements();
  }

  /**
   * Echange les scores des {@link Caracteristique}s du {@link PersonnageDonjon}
   * selon un certain ordre.
   *
   * @param priorite Le nouvel ordre de priorité pour les {@link Caracteristique}s
   *
   * @see PersonnageDonjon#caracteristiquesActualiser()
   */
  public void priorite(Caracteristique[] priorite) {

    if (priorite.length != Caracteristique.values().length) {
      throw new IllegalArgumentException(
              "Vous avez renseigné un ordre de priorité inapproprié."
                      + " Il faut " + Caracteristique.values().length 
                      + " caractéristiques différentes."
                      + " Vous en avez renseigné " + priorite.length + "."
      );
    }

    for (int i = 0; i < priorite.length - 1; i++) {
      for (int j = i + 1; i < priorite.length; j++) {
        if (priorite[i] == priorite[j]) {
          throw new IllegalArgumentException(
                  "Vous avez renseigné un ordre de priorité inapproprié."
                          + " Il faut " + Caracteristique.values().length 
                          + " caractéristiques différentes."
                          + " Vous en avez renseigné deux fois ou plus " 
                          + priorite[j].getNom() + "."
          );
        }
      }
    }

    this.priorite = priorite;
    caracteristiquesActualiser();
  }

  private void caracteristiquesActualiser() {

    ScoreDeCaracteristique[] defaut = caracteristiques.clone();

    Integer[] valeurs = new Integer[defaut.length];
    for (int i = 0; i < defaut.length; i++) {
      valeurs[i] = defaut[i].val();
    }

    Arrays.sort(valeurs);

    for (int i = 0; i < defaut.length; i++) {
      caracteristiques[i] = new ScoreDeCaracteristique(priorite[defaut.length - i - 1], valeurs[i]);
    }

    scoreActualiser();
  }

  /**
   * Modifier la valeur d'une caracteristique.
   *
   * @param car La caractéristique dont l'on souhaite modifier la valeur.
   * @param valeur La valeur qu'on veut donner à la caractéristique.
   */
  public void valeur(Caracteristique car, int valeur) {

    if (valeur < 1 && valeur > 21) {
      throw new IllegalArgumentException("La valeur d'une caractéristique doit se trouver"
          + "dans l'intervalle [1, 21], mais la valeur renseignée est : " + valeur + ".");
    }
    
    for (ScoreDeCaracteristique selectionner : caracteristiques) {
      if (selectionner.type().equals(car)) {
        selectionner = new ScoreDeCaracteristique(car, valeur);
      }
    }

    scoreActualiser();
  }

  private void scoreActualiser() {
    this.defenseBase = mod(Caracteristique.CON) + 10;
    this.initiativeBase = mod(Caracteristique.DEX);
    this.contact = new ScoreAttaqueContact(null, mod(Caracteristique.FOR) + 1);
    this.distance = new ScoreAttaqueDistance(null, mod(Caracteristique.DEX) + 1);
    this.magique = new ScoreAttaqueMagique(null, mod(Caracteristique.INT) + 1);
  }

  private int mod(Caracteristique carChoisie) {

    for (ScoreDeCaracteristique car : caracteristiques) {
      if (car.type() == carChoisie) {
        return car.mod();
      }
    }

    return 0;
  }

  public Jauge bourse() {
    return bourse;
  }

  /**
   * La bourse reçoit ou donne de l'argent.
   * 
   * <p>Si la quantité ne peut être ajoutée à la bourse, alors la transaction
   * est annulée.</p>
   *
   * @see Jauge#ajoutable()
   *
   * @param qtePieces La quantité de pièces que l'on veut ajouter ou retirer
   * @return <b>true</b> si la bourse contient assez de pièces ou d'espace.
   */
  public boolean echangerPiece(int qtePieces) {

    if (bourse.ajoutable(qtePieces)) {
      bourse.modifier(qtePieces);
      return true;
    }

    return false;
  }

  /**
   * Fixe les armes portées par le {@link PersonnageDonjon}.
   * 
   * <p>Certaines armes nécessitent deux mains, il faut donc faire attention
   * à ce que le personnage ne porte pas une arme à deux mains et une autre
   * arme/bouclier.</p>
   *
   * @see Arme
   *
   * @param arme1 Première arme
   * @param arme2 Deuxième arme
   * @return <b>true</b> si les deux armes renseignées peuvent être portées en même temps
   */
  public boolean setArmes(Arme arme1, Arme arme2) {

    if (arme1 != null && arme2 != null) {
      if (arme1.isDeuxMains() || arme2.isDeuxMains()) {
        String nbMains1 = "deux mains";
        String nbMains2 = "deux mains.";
        if (!arme1.isDeuxMains()) {
          nbMains1 = "une main";
        }
        if (!arme2.isDeuxMains()) {
          nbMains2 = "une main.";
        }
        throw new IllegalArgumentException("Les personnages n'ont que deux mains."
                + arme1.getNom() + " requiert " + nbMains1
                + " et " + arme2.getNom() + " requiert " + nbMains2);
      }
    }

    this.armes[0] = arme1;
    this.armes[1] = arme2;

    return true;
  }

  /**
   * Fixe les armes portées par le {@link PersonnageDonjon}.
   * 
   * <p>Certaines armes nécessitent deux mains, il faut donc faire attention
   * à ce que le personnage ne porte pas une arme à deux mains et une autre
   * arme/bouclier.</p>
   *
   * @see Arme
   *
   * @param bouclier {@link Bouclier} que le personnage doit porter.
   * @param armure {@link ArmureDeCorps} que le personnage doit porter.
   */
  public void setArmures(Bouclier bouclier, ArmureDeCorps armure) {

    this.armures[0] = bouclier;
    this.armures[1] = armure;
  }

  /**
   * Fixe le {@link Sac} d'un {@link PersonnageDonjon}.
   * 
   * <p>Un {@link Sac} ne doit contenir que des {@link Equipement}s, 
   * et doit en possèder au maximum 20.</p>
   *
   * @see Sac
   *
   * @param sac Le sac avec les equipements que l'on veut attribuer au personnage
   * @return <b>true</b> le sac contient au plus 20 équipements et rien d'autres.
   */
  public boolean setSac(Sac sac) {

    for (int i = 0; i < sac.size(); i++) {
      if (!(sac.get(i) instanceof Equipement)) {
        throw new IllegalArgumentException("Un sac ne peut contenir que des équipements."
                + "Le sac renseigné contient un objet " + sac.get(i).getClass().getName() + ".");

      }
    }


    if (sac.size() > 20) {
      throw new IllegalArgumentException("Un sac ne peut pas contenir plus de 20 équipements."
              + "Le sac renseigné contient " + sac.size() + " équipements.");
    }

    Sac newSac = new Sac(20);
    for (int i = 0; i < sac.getItems().length; i++) {
      newSac.add(sac.get(i));
    }

    this.sac = newSac;
    return true;
  }

  public void retirerBouclier() {
    this.armures[0] = null;
  }

  public void retirerArmureDeCorps() {
    this.armures[1] = null;
  }

  /**
   * Arreter le port d'une {@link Arme}, tout en le conservant dans le {@link Sac}.
   *
   * @param arme L'arme que le personnage doit arrêté d'utiliser.
   *
   * @return <b>true</b> si l'arme renseignée est portée par le personnage.
   */
  public boolean retirerArmes(Arme arme) {
    if (armes[0].equals(arme)) {
      armes[0] = null;
      return true;
    } else if (armes[1].equals(arme)) {
      armes[1] = null;
      return true;
    }

    return false;
  }

  public void laisserRubisAleatoire() {

    /*
    switch((int) (Math.random()*3)) {
    case 0 :
      world.leavePieces((int) (bourse.valeur()*0.2));
      break;
    case 1 :
      world.leavePieces((int) (bourse.valeur()*0.05));
      break;
    case 2:
      world.leavePieces((int) (bourse.valeur()*0.5));
      break;
    }*/
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
    private Jauge pointsDeVie;

    // Paramètres optionnels
    private ScoreDeCaracteristique[] caracteristiques;
    private int initiativeBase;
    private int defenseBase;
    private ScoreAttaqueContact contact;
    private ScoreAttaqueDistance distance;
    private ScoreAttaqueMagique magique;
    private Sac sac = new Sac(20);
    // En premier, les éléments de plus grande priorité
    private Caracteristique[] priorite =
          { Caracteristique.CHA,
            Caracteristique.CON,
            Caracteristique.DEX,
            Caracteristique.FOR,
            Caracteristique.INT,
            Caracteristique.SAG };
    private Jauge bourse = new Jauge(50, 1000);

    /**
     * Constructeur de PersonnageBuilder.
     *
     * @param nom Le nom du personnage.
     * @param pointsDeVieDepart Points de vie de départ du personnage
     * @param pointsDeVieMax Nombre maximum de points de vie du personnage
     */
    public PersonnageBuilder(String nom, int pointsDeVieDepart, int pointsDeVieMax) {
      this.nom = nom;
      this.pointsDeVie = new Jauge(pointsDeVieDepart, pointsDeVieMax);
      this.caracteristiques = calculCaracteristiques(65, 80, priorite);
      scoreActualiser();
    }

    /**
     * Méthode pour personnaliser les {@link ScoreDeCaracteristique}s d'un 
     * personnage.
     *
     * @param car Les 6 scores de caractéristiques que l'on veut attribuer.
     * @return une instance de cette classe ({@link PersonnageBuilder}.
     */
    public PersonnageBuilder caracteristiques(ScoreDeCaracteristique[] car) {
      
      for (int i = 0; i < car.length - 1; i++) {
        for (int j = i + 1; j < car.length; j++) {
          if (car[i].nom().equals(car[j].nom())) {
            throw new IllegalArgumentException("Vous avez renseigné deux caractéristiques"
                + " identiques : " + car[i].nom() + " aux positions : " + i 
                + " et " + j + ".");
          }
        }
      }
      
      this.caracteristiques = car;
      scoreActualiser();
      return this;
    }

    public PersonnageBuilder priorite(Caracteristique[] priorite) {
      this.priorite = priorite;
      return this;
    }

    public PersonnageBuilder sac(Sac sac) {
      this.sac = sac;
      return this;
    }

    private void scoreActualiser() {
      this.defenseBase = mod(Caracteristique.CON) + 10;
      this.initiativeBase = mod(Caracteristique.DEX);
      this.contact = new ScoreAttaqueContact(null, mod(Caracteristique.FOR) + 1);
      this.distance = new ScoreAttaqueDistance(null, mod(Caracteristique.DEX) + 1);
      this.magique = new ScoreAttaqueMagique(null, mod(Caracteristique.INT) + 1);
    }

    public Personnage build() {
      return new PersonnageDonjon(this);
    }

    private ScoreDeCaracteristique[] calculCaracteristiques(
        int limInf, int limSup, Caracteristique[] priorite) {

      int nbCar = Caracteristique.values().length;
      ScoreDeCaracteristique[] calculer = new ScoreDeCaracteristique[nbCar];
      int somme = 0;
      int[] arr = new int[nbCar];


      do {
        int[][] resultatsDes = new int[nbCar][4];

        for (int i = 0; i < nbCar; i++) {
          for (int j = 0; j < 4; j++) {
            resultatsDes[i][j] = (int) ((Math.random() * 6) + 1);
          }
        }

        for (int i = 0; i < nbCar; i++) {
          Arrays.sort(resultatsDes[i]);
          arr[i] = resultatsDes[i][3] + resultatsDes[i][2] + resultatsDes[i][1];
        }

        somme = Arrays.stream(arr).sum();

      } while (somme > limSup && somme < limInf);

      Arrays.sort(arr);

      for (int i = 0; i < nbCar; i++) {
        calculer[i] = new ScoreDeCaracteristique(priorite[nbCar - i - 1], arr[i]);
      }

      return calculer;
    }

    private int mod(Caracteristique carChoisie) {

      for (ScoreDeCaracteristique car : caracteristiques) {
        if (car.type() == carChoisie) {
          return car.mod();
        }
      }

      return 0;
    }
  }

}

