package fr.uvsq.pglp.roguelike.personnage.ia;

import fr.uvsq.pglp.roguelike.donjon.elements.Difficulte;
import fr.uvsq.pglp.roguelike.donjon.elements.ElementEtage;
import fr.uvsq.pglp.roguelike.donjon.elements.Porte;
import fr.uvsq.pglp.roguelike.donjon.elements.Tile;
import fr.uvsq.pglp.roguelike.donjon.elements.Tresor;
import fr.uvsq.pglp.roguelike.echangeable.ArmeContact;
import fr.uvsq.pglp.roguelike.echangeable.ArmureDeCorps;
import fr.uvsq.pglp.roguelike.personnage.Personnage;
import fr.uvsq.pglp.roguelike.personnage.PersonnageDonjon;

/**
 * Intelligence artificielle pour les PNJs défensifs.
 *
 * <p>Chaque PNJ défensif possède un point/objet à défendre. Dès ors que le
 * personnage joueur s'approche de trop près de ce point, alors lle
 * PNJ défensif ira attaquer le personnage joueur, jusqu'à ce qu'il s'éloigne assez.</p>
 *
 * @author Tom Abbouz
 * @version Février 2023
 * @see PersonnageIa
 */
public class DefensifIa extends PersonnageIa {

  private int defendreX1;
  private int defendreY1;
  private int defendreX2;
  private int defendreY2;
  private boolean zoneCreer = false;
  
  private fr.uvsq.pglp.roguelike.personnage.ia.Difficulte difficulteFranchir;

  public DefensifIa(Personnage personnage) {
    super(personnage, "DEFENSIF", true);
    this.difficulteFranchir = fr.uvsq.pglp.roguelike.personnage.ia.Difficulte.random();
  }

  private void creerZoneDefense() {
    
    Tresor t = creerTresor();
    ElementEtage element = personnage.getElementEtage();
    
    if(element.ouvrables(personnage.getX(), personnage.getY()) == null ||
        !(element.ouvrables(personnage.getX(), personnage.getY()) instanceof Porte)) {
      element.addOuvrable(personnage.getX(), personnage.getY(), t);
    }
    
    defendreX1 = personnage.getX();
    defendreX2 = personnage.getX();
    defendreY1 = personnage.getY();
    defendreY2 = personnage.getY();
    
    int x1 = (int) (Math.random()*2) + 2;
    int y1 = (int) (Math.random()*2) + 2;
    int x2 = (int) (Math.random()*2) + 2;
    int y2 = (int) (Math.random()*2) + 2;
    
    int distance = 0;
    while(defendreX1 > 1 && distance < x1) {
      defendreX1--;
      distance++;
    }
    
    distance = 0;
    while(defendreY1 > 1 && distance < y1) {
      defendreY1--;
      distance++;
    }
    
    distance = 0;
    while(defendreX2 < (element.largeur() - 2) && distance < x2) {
      defendreX2++;
      distance++;
    }
    
    distance = 0;
    while(defendreY2 < (element.longueur() - 2) && distance < y2) {
      defendreY2++;
      distance++;
    }
  }


  private Tresor creerTresor() {
    Tresor t = new Tresor(Tile.BOX);
    t.setDifficulte(Difficulte.TRESDIFF);
    switch((int) (Math.random()*2)) {
    case 0:
      t.setEchangeable(ArmeContact.MARTEAUDEGUERRE);
      break;
    case 1:
      t.setEchangeable(ArmureDeCorps.COTTE);
      break;
    default:
      break;
    }
    
    return t;
  }


  @Override
  boolean doitChasser() {
    
    if(!zoneCreer) {
      creerZoneDefense();
      zoneCreer = true;
    }
    
    PersonnageDonjon joueur = (PersonnageDonjon) personnage.getElementEtage().getJoueur();
    
    if(joueur == null) {
      return false;
    }
    
    if(dansZoneDefense(joueur.getX(), joueur.getY())) {
      notifier("C'est chez moi ici! Que fais-tu sur mon territoire ?!");
      return true;
    }
    return false;
  }

  @Override
  boolean doitAider() {
    return false;
  }

  @Override
  public void errer() {
    
    int mx = (int) (Math.random() * 3) - 1;
    int my = (int) (Math.random() * 3) - 1;
    
    int objectifx = personnage.getX() + mx;
    int objectify = personnage.getY() + my;
    
    Personnage other = personnage.personnage(objectifx, objectify);

    if ((other == null || !other.getNom().equals(personnage.getNom()))
        && dansZoneDefense(objectifx, objectify)) {
      personnage.moveBy(mx, my);
    }
    
  }


  public boolean dansZoneDefense(int objectifx, int objectify) {
    
    if( objectifx < Math.min(defendreX1, defendreX2) ) {
      return false;
    }
    if( objectify < Math.min(defendreY1, defendreY2) ) {
      return false;
    }
    if( objectifx > Math.max(defendreX1, defendreX2) ) {
      return false;
    }
    if( objectify > Math.max(defendreY1, defendreY2) ) {
      return false;
    }
    
    return true;
  }
  
  public boolean tirage(int mod, Personnage joueur) {
    if(difficulteFranchir.getValeur() <= (20 + mod)) {
      if(difficulteFranchir.tirage(mod)) {
        return true;
      } else {
        notifier("C'était bien essayé " + joueur.getNom() +".");
        notifier("Mais ma barrière est bien trop haute pour toi!");
        joueur.notifier("Vous n'avez pas réussis à franchir la barrière de " + personnage.getNom() + "!");
        return false;
      }
    } else {
      notifier("Ma barrière est beaucoup trop grande pour toi, petit " + joueur.getNom() + "!");
      joueur.notifier("Inutile d'essayer, vous manquer de dextérité pour franchir la barrière de "
          + personnage.getNom() + " !");
      return false;
    }
  }
}
