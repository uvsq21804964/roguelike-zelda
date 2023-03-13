package fr.uvsq.pglp.roguelike.donjon.elements;

import fr.uvsq.pglp.roguelike.donjon.MorceauEtage;
import fr.uvsq.pglp.roguelike.echangeable.Echangeable;
import fr.uvsq.pglp.roguelike.personnage.Personnage;

/**
 * Représentation d'unz porte.
 *
 * <p>Une porte peut être ouverte ou fermée, et peut nécessiter d'être
 * crocheter selon son type. Son type est reconnu par un {@link Tile}.</p>
 *
 * @author Tom Abbouz
 * @version Mars 2023
 */
public class Porte implements Ouvrable {

  private Tile type;
  private Salle salle;
  private Couloir couloir;
  private boolean ouverte = false;
  private Difficulte difficulte = null;

  /**
   * Constructeur de Porte.
   *
   * @param type Le type de porte, crochetable ou classique.
   * @param salle La {@link Salle} que la porte relie.
   * @param couloir La {@link Couloir} que la porte relie.
   */
  public Porte(Tile type, Salle salle, Couloir couloir) {
    if(!type.equals(Tile.PORTE) && !type.equals(Tile.PORTEACROCHETER)) {
      throw new IllegalArgumentException("Un trésor doit être constitué d'une tuile "
          + Tile.PORTE.toString() + " ou " + Tile.PORTEACROCHETER.toString() + ".\n "
          + "Mais vous avez donné une tuile " + type.toString() + ".");
    }
    this.type = type;
    this.salle = salle;
    this.couloir = couloir;
    if(!type.equals(Tile.PORTE)) {
      this.difficulte = Difficulte.random();
    }
  }

  @Override
  public Tile getType() {
    return type;
  }

  public Salle getSalle() {
    return salle;
  }

  public Couloir getCouloir() {
    return couloir;
  }

  @Override
  public boolean isOuverte() {
    return ouverte;
  }

  @Override
  public Echangeable ouvrir(Personnage p) {
    this.ouverte = true;
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

  @Override
  public String etudier(ElementEtage e) {
    if(e instanceof Salle) {
      if(couloir instanceof CouloirSimple) {
        return "C'est un couloir vide.";
      } else if(couloir instanceof CouloirAvecEquipements) {
        return "Ce couloir est une véritable caverne d'Ali Baba !";
      } else if(couloir instanceof CouloirAvecEnnemis) {
        return "Une embuscade vous attend dans ce couloir ! Faites attention !!";
      }
    } else if (e instanceof Couloir) {
      return ("Cette salle contient " + salle.nbEquipements() + " équipements "
          + " et " + salle.nbEnnemis() + " personnages.");
    }
    return null;
  }

  public String ecouter(MorceauEtage morceauEtage) {
    if(morceauEtage instanceof Salle) {
      if(couloir instanceof CouloirSimple) {
        return "Aucun bruit, il n'y a probablement personne de l'autre côté.";
      } else if(couloir instanceof CouloirAvecEquipements) {
        return "Aucun bruit, il n'y a probablement personne de l'autre côté.";
      } else if(couloir instanceof CouloirAvecEnnemis) {
        return "Il y a du bruit ! Peut-être qu'une embuscade m'attend de l'autre côté !!";
      }
    } else if (morceauEtage instanceof Couloir) {
      switch(salle.nbEnnemis()) {
      case 0:
        return "Aucun bruit, il n'y a probablement personne de l'autre côté.";
      case 1:
      case 2:
        return "Il y a un peu de bruit...";
      default :
        return "Il y a beaucoup de bruit !";
      }
    }
    return null;
  }

  
}
