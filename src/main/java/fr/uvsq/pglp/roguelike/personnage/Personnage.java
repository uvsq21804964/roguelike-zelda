package fr.uvsq.pglp.roguelike.personnage;

import java.util.Arrays;

public class Personnage {
	
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
	
	Personnage(PersonnageBuilder builder) {
		
		// Paramètres requis
		this.nom = builder.getNom();
		this.pointsDeVie = builder.getPointsDeVie();
		
		// Paramètres optionnels
		this.caracteristiques = builder.getCaracteristiques();
		this.initiativeBase = builder.getInitiativeBase();
		this.defenseBase = builder.getDefenseBase();
		this.priorite = builder.getPriorite();
		this.contact = builder.getContact();
		this.distance = builder.getDistance();
		this.magique = builder.getMagique();
		this.sac = builder.getSac();
		this.bourse = builder.getBourse();
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
		}
    }
	
}

