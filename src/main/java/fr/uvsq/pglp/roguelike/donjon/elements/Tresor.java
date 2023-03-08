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

  private Tile type;
  private boolean ouverte = false;
  private Echangeable echangeable;
  private Difficulte difficulte = null;

  public Tresor(Tile type) {
    this.type = type;
    this.echangeable = echangeableRandom();
    if(!type.equals(Tile.CHEST)) {
      this.difficulte = Difficulte.random();
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
  public Echangeable ouvrir() {
    if(!ouverte) {
      this.ouverte = true;
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
}
