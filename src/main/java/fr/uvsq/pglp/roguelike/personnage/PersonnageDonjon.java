package fr.uvsq.pglp.roguelike.personnage;

import java.util.Arrays;
import fr.uvsq.pglp.roguelike.echangeable.*;
import fr.uvsq.pglp.roguelike.personnage.ia.PersonnageIA;

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
	private Armure[] armures = {null, null};
	private Arme[] armes = {null, null};
	private PersonnageIA ia;
	public void setPersonnageIA(PersonnageIA ia) { this.ia = ia; }
	private int x;
	private int y;
	
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
				{	Caracteristique.CHA, 
					Caracteristique.CON, 
					Caracteristique.DEX,
					Caracteristique.FOR, 
					Caracteristique.INT, 
					Caracteristique.SAG		};
		private Jauge bourse = new Jauge(50, 1000);
		
		public PersonnageBuilder(String nom, int pointsDeVieDepart, int pointsDeVieMax) {
			this.nom = nom;
			this.pointsDeVie = new Jauge(pointsDeVieDepart, pointsDeVieMax);
			this.caracteristiques = CalculCaracteristiques(65, 80, priorite);
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
		
		public Personnage build() {
			return new PersonnageDonjon(this);
		}
		
		private ScoreDeCaracteristique[] CalculCaracteristiques(int limInf, int limSup, Caracteristique[] priorite) {
			
			int nbCar = Caracteristique.values().length;
			ScoreDeCaracteristique[] calculer = new ScoreDeCaracteristique[nbCar];
			int somme = 0;
			int arr[] = new int[nbCar];
			
			
			do {
				int resultatsDes[][] = new int[nbCar][4];
				
				for(int i = 0 ; i < nbCar ;  i++) {
					for(int j = 0 ; j < 4 ;  j++) {
						resultatsDes[i][j] = (int) ((Math.random()*6) + 1);
					}
				}
				
				for(int i = 0 ; i < nbCar ;  i++) {
					Arrays.sort(resultatsDes[i]);
					arr[i] = resultatsDes[i][3] + resultatsDes[i][2] + resultatsDes[i][1];
				}
				
				somme = Arrays.stream(arr).sum();
				
			} while(somme > limSup && somme < limInf);
			
			Arrays.sort(arr);
			
			for(int i = 0 ; i < nbCar ;  i++) {
				calculer[i] = new ScoreDeCaracteristique(priorite[nbCar - i - 1], arr[i]);
			}
			
			return calculer;
		}
		
		private int mod(Caracteristique carChoisie) {
			
			for(ScoreDeCaracteristique car : caracteristiques) {
				if(car.type() == carChoisie) {
					return car.mod();
				}
			}
			
			return 0;
		}
	}
	
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
	
	public void priorite(Caracteristique[] priorite) {
		
		if(priorite.length != Caracteristique.values().length) {
			throw new IllegalArgumentException(
            		"Vous avez renseigné un ordre de priorité inapproprié."
					+ " Il faut " + Caracteristique.values().length + " caractéristiques différentes."
					+ " Vous en avez renseigné " + priorite.length + "."
            		);
		}
		
		for(int i = 0 ; i < priorite.length - 1 ; i++) {
			for(int j = i + 1 ; i < priorite.length ; j++) {
				if(priorite[i] == priorite[j]) {
					throw new IllegalArgumentException(
		            		"Vous avez renseigné un ordre de priorité inapproprié."
							+ " Il faut " + Caracteristique.values().length + " caractéristiques différentes."
							+ " Vous en avez renseigné deux fois ou plus " + priorite[j].getNom() + "."
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
		for (int i = 0 ; i < defaut.length ; i++) {
			valeurs[i] = defaut[i].val();
		}
		
		Arrays.sort(valeurs);
		
		for(int i = 0 ; i < defaut.length ;  i++) {
			caracteristiques[i] = new ScoreDeCaracteristique(priorite[defaut.length - i - 1], valeurs[i]);
		}
		
		scoreActualiser();
	}

	public void valeur(Caracteristique car, int valeur) {
		
		for(ScoreDeCaracteristique selectionner : caracteristiques ) {
			if(selectionner.type().equals(car)) {
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
		
		for(ScoreDeCaracteristique car : caracteristiques) {
			if(car.type() == carChoisie) {
				return car.mod();
			}
		}
		
		return 0;
	}
	
	public Jauge bourse() {
		return bourse;
	}
	
	public boolean echangerPiece(int qtePieces) {
		
		if(bourse.ajoutable(qtePieces)) {
			bourse.modifier(qtePieces);
			return true;
		}
		
		return false;
	}
	

	public boolean setArmes(Arme arme1, Arme arme2) {
		
		if(arme1 != null && arme2 != null) {
			if(arme1.isDeuxMains() || arme2.isDeuxMains()) {
				String nbMains1 = "deux mains";
				String nbMains2 = "deux mains.";
				if(!arme1.isDeuxMains()) {
					nbMains1 = "une main";
				}
				if(!arme2.isDeuxMains()) {
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
	
	public void setArmures(Bouclier bouclier, ArmureDeCorps armure) {
				
		this.armures[0] = bouclier;
		this.armures[1] = armure;
	}
	
	public boolean setSac(Sac sac) {
		
		for(int i = 0 ; i < sac.size() ; i++) {
			if(!(sac.get(i) instanceof Equipement)) {
				throw new IllegalArgumentException("Un sac ne peut contenir que des équipements."
						+ "Le sac renseigné contient un objet " + sac.get(i).getClass().getName() + ".");
			
			}
		}
		
		
		if(sac.size() > 20) {
			throw new IllegalArgumentException("Un sac ne peut pas contenir plus de 20 équipements."
					+ "Le sac renseigné contient " + sac.size() + " équipements.");
		}
		
		Sac newSac = new Sac(20);
		for(int i = 0 ; i < sac.getItems().length ; i++) {
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
	
	public boolean retirerArmes(Arme arme) {
		if(armes[0].equals(arme)) {
			armes[0] = null;
			return true;
		} else if (armes[1].equals(arme)) {
			armes[1] = null;
			return true;
		}
			
		return false;
	}
	
	public void laisserRubisAleatoire() {
    	
//		switch((int) (Math.random()*3)) {
//		case 0 :
//			world.leavePieces((int) (bourse.valeur()*0.2));
//			break;
//		case 1 :
//			world.leavePieces((int) (bourse.valeur()*0.05));
//			break;
//		case 2:
//			world.leavePieces((int) (bourse.valeur()*0.5));
//			break;
//		}
    }
	
}

