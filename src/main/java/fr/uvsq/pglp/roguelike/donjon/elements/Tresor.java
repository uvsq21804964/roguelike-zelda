package fr.uvsq.pglp.roguelike.donjon.elements;

import fr.uvsq.pglp.roguelike.echangeable.ArmeContact;
import fr.uvsq.pglp.roguelike.echangeable.ArmeRechargementLimite;
import fr.uvsq.pglp.roguelike.echangeable.ArmeRechargementSimple;
import fr.uvsq.pglp.roguelike.echangeable.ArmeSansRechargement;
import fr.uvsq.pglp.roguelike.echangeable.ArmureDeCorps;
import fr.uvsq.pglp.roguelike.echangeable.Bouclier;
import fr.uvsq.pglp.roguelike.echangeable.Echangeable;
import fr.uvsq.pglp.roguelike.echangeable.Equipement;
import fr.uvsq.pglp.roguelike.echangeable.Pieces;
import fr.uvsq.pglp.roguelike.personnage.Personnage;
import fr.uvsq.pglp.roguelike.personnage.PersonnageDonjon;

/**
 * Représentation d'un trésor.
 *
 * <p>Une trésor peut être ouvert ou fermée, et peut nécessiter d'être
 * forcer selon son type. Son type est reconnu par un {@link Tile}.</p>
 *
 * @see Ouvrable
 *
 * @author Tom Abbouz
 * @version Mars 2023
 */
public class Tresor implements Ouvrable {

  private final Tile type;
  private boolean ouverte = false;
  private Echangeable echangeable;
  private Difficulte difficulte = null;
  private Bombe bombe;

  public Tresor(Tile type) {
    
    if(!type.equals(Tile.BOX) && !type.equals(Tile.CHEST)) {
      throw new IllegalArgumentException("Un trésor doit être constitué d'une tuile "
          + Tile.BOX.toString() + " ou " + Tile.CHEST.toString() + ".\n Mais vous avez donné "
          + "une tuile " + type.toString() + ".");
    }
    this.type = type;
    this.echangeable = echangeableRandom();
    if(!type.equals(Tile.CHEST)) {
      this.difficulte = Difficulte.random();
    }
    this.bombe = null;
    if(((int) (Math.random()*5)) == 4) {
      bombe = new Bombe(((int) (Math.random()*2)) + 1);
    }
  }

  private Echangeable echangeableRandom() {
    if(type.equals(Tile.BOX)) {
      return equipementRandom();
    } else if (type.equals(Tile.CHEST)) {
      return piecesRandom();
    }
    return null;
  }

  private Pieces piecesRandom() {
    int qte = 0;
    
    switch((int)(Math.random()*3)) {
    case 0:
    case 1:
      qte = 1;
      break;
    case 2:
      qte = 2;
      break;
    default:
      break;
    }
    
    return new Pieces(qte);
  }

  private Equipement equipementRandom() {
    if ((int) (Math.random() * 2) == 0) {
      if ((int) (Math.random() * 2) == 0) {
        return Bouclier.random(100);
      } else {
        return ArmureDeCorps.random(100);
      }
    } else {
      if ((int) (Math.random() * 2) == 0) {
        return ArmeContact.random(100);
      } else {
        switch ((int) (Math.random() * 3)) {
          case 0:
            return ArmeRechargementLimite.random(100);
          case 1:
            return ArmeRechargementSimple.random(100);
          default:
            return ArmeSansRechargement.random(100);
        }
      }
    }
  }

  @Override
  public Tile getType() {
    return type;
  }

  @Override
  public boolean isOuverte() {
    return ouverte;
  }

  @Override
  public Echangeable ouvrir(Personnage p) {
    if(!ouverte) {
      this.ouverte = true;
      if(bombe != null) {
        p.modifierPv(-bombe.degat());
        p.notifier("Ce trésor était piégé !");
        p.notifier("Vous avez perdu " + bombe.degat() + " points de vie !");
      }
      return this.echangeable;
    }
    return null;
  }

  @Override
  public boolean tirage(int mod) {
    int tirage = ((int) (Math.random()*20)+1);
    if(difficulte.getValeur() <= (tirage + mod)) {
      return true;
    }
    return false;
  }

  @Override
  public String getDifficulte() {
    return difficulte.getString();
  }
  
  @Override
  public int getDiffValeur() {
    return difficulte.getValeur();
  }
  
  public void setEchangeable(Echangeable e) {
    echangeable = e;
  }
  
  public void setDifficulte(Difficulte d) {
    difficulte = d;
  }

  @Override
  public String etudier(ElementEtage e) {
    if(bombe != null) {
      return "Oh, ce trésor est piégé, il contient une bombe!";
    } else {
      switch(type) {
      case BOX:
        return "Vous pouvez forcer ce trésor sans risque!";
      case CHEST:
        return "Vous pouvez ouvrir ce trésor sans risque!";
      default:
        return null;
      }
    }
  }
}
