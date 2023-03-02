package fr.uvsq.pglp.roguelike.personnage;

import java.util.Arrays;

class PersonnageBuilder {

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

  public PersonnageBuilder(String nom, int pointsDeVieDepart, int pointsDeVieMax) {
    this.nom = nom;
    this.pointsDeVie = new Jauge(pointsDeVieDepart, pointsDeVieMax);
    this.caracteristiques = calculCaracteristiques(65, 80, priorite);
    scoreActualiser();
  }

  public PersonnageBuilder caracteristiques(ScoreDeCaracteristique[] car) {
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

  public PersonnageDonjon build() {
    return new PersonnageDonjon(this);
  }

  private ScoreDeCaracteristique[] calculCaracteristiques(
      int limInf, int limSup, Caracteristique[] priorite) {

    int nbCar = Caracteristique.values().length;
    ScoreDeCaracteristique[] calculer = new ScoreDeCaracteristique[nbCar];
    int somme = 0;
    Integer[] arr = new Integer[nbCar];


    do {
      Integer[][] resultatsDes = new Integer[nbCar][4];

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

  public Jauge getPointsDeVie() {
    return pointsDeVie;
  }

  public Jauge getBourse() {
    return bourse;
  }

  public ScoreDeCaracteristique[] getCaracteristiques() {
    return caracteristiques;
  }

  public int getInitiativeBase() {
    return initiativeBase;
  }

  public int getDefenseBase() {
    return defenseBase;
  }

  public ScoreAttaqueContact getContact() {
    return contact;
  }

  public ScoreAttaqueDistance getDistance() {
    return distance;
  }

  public ScoreAttaqueMagique getMagique() {
    return magique;
  }

  public Sac getSac() {
    return sac;
  }

  public Caracteristique[] getPriorite() {
    return priorite;
  }

  public String getNom() {
    return nom;
  }


}
