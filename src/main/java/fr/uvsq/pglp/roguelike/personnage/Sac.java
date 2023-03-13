package fr.uvsq.pglp.roguelike.personnage;

import fr.uvsq.pglp.roguelike.echangeable.Arme;
import fr.uvsq.pglp.roguelike.echangeable.ArmeContact;
import fr.uvsq.pglp.roguelike.echangeable.ArmeRechargementLimite;
import fr.uvsq.pglp.roguelike.echangeable.ArmeRechargementSimple;
import fr.uvsq.pglp.roguelike.echangeable.ArmeSansRechargement;
import fr.uvsq.pglp.roguelike.echangeable.ArmureDeCorps;
import fr.uvsq.pglp.roguelike.echangeable.Bouclier;
import fr.uvsq.pglp.roguelike.echangeable.Echangeable;
import fr.uvsq.pglp.roguelike.echangeable.Equipement;
import fr.uvsq.pglp.roguelike.echangeable.Pieces;

public class Sac {

  private Equipement[] equipements;
  private int pieces;
  
  public Sac(int nbMax, int nbPiecesInitial) {
    this.equipements = new Equipement[nbMax];
    this.pieces = nbPiecesInitial;
  }

  public boolean add(Echangeable e) {
    
    if(e instanceof Equipement) {
      for (int i = 0; i < equipements.length; i++) {
        if (equipements[i] == null) {
          equipements[i] = (Equipement) e;
          System.out.println("Modif");
          return true;
        }
      }
      return false;
    } else if(e instanceof Pieces) {
      pieces += ((Pieces) e).getValeur();
      return true;
    }
    return false;
  }

  public void addRandom() {
    
    for (int i = 0; i < equipements.length; i++) {
      equipements[i] = null;
    }
    
    for (int i = 0; i < equipements.length; i++) {
      equipements[i] = equipementRandom();
    }
    
  }
  
  private Equipement equipementRandom() {
    if ((int) (Math.random() * 2) == 0) {
      if ((int) (Math.random() * 2) == 0) {
        return Bouclier.random(6);
      } else {
        return ArmureDeCorps.random(6);
      }
    } else {
      if ((int) (Math.random() * 2) == 0) {
        return ArmeContact.random(6);
      } else {
        switch ((int) (Math.random() * 3)) {
          case 0:
            return ArmeRechargementLimite.random(6);
          case 1:
            return ArmeRechargementSimple.random(6);
          default:
            return ArmeSansRechargement.random(6);
        }
      }
    }
  }

  public int quantite() {
    
    int quantite = 0;
    
    for (int i = 0; i < equipements.length; i++) {
      if (equipements[i] != null) {
        quantite++;
      }
    }
    
    return quantite;
  }

  public Equipement get(int numero) {
    
    if(numero > quantite()) {
      throw new IllegalArgumentException("Il n'y a que "+ quantite() 
      +" équipements dans le sac , mais vous avez demandé le "+ numero +"-ème équipement.");
    }
    System.out.println("Numero : " + numero);
    
    int actuel = 0;
    for(int i = 0 ; i < equipements.length ; i++) {
      if(equipements[i] != null) {
        System.out.println(equipements[i].getNom());
        if(actuel == numero) {
          return equipements[i];
        }
        actuel++;
      }
    }
    System.out.println("");
    System.out.println("");
    
    return null;
  }

  public boolean bourseVide() {
    return (pieces == 0);
  }

  public boolean plein() {
    for (int i = 0; i < equipements.length; i++) {
      if (equipements[i] == null) {
        return false;
      }
    }
    return true;
  }
  
  public boolean payer(int prix) {
    
    if(pieces - prix >= 0) {
      pieces -= prix;
      return true;
    } else {
      return false;
    }
  }

  public int getPieces() {
    return pieces;
  }

  public boolean retirer(Equipement e) {
    for (int i = 0; i < equipements.length; i++) {
      if (equipements[i] == e) {
        equipements[i] = null;
        return true;
      }
    }
    return false;
  }

  public Equipement getType(Equipement e) {
    
    if(e instanceof Arme) {
      for(Equipement arme : equipements) {
        if(arme instanceof Arme) {
          return arme;
        }
      }
    } else if (e instanceof Bouclier) {
      for(Equipement bouclier : equipements) {
        if(bouclier instanceof Bouclier) {
          return bouclier;
        }
      }
    } else if (e instanceof ArmureDeCorps) {
      for(Equipement armure : equipements) {
        if(armure instanceof ArmureDeCorps) {
          return armure;
        }
      }
    }
    
    return null;
  }

}
