package fr.uvsq.pglp.roguelike.personnage.ia;

import fr.uvsq.pglp.roguelike.donjon.elements.Tile;
import fr.uvsq.pglp.roguelike.personnage.Personnage;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite représentant les différentes types d'IA à partir du pattern
 * <b>Template Method</b>.
 *
 * <p>Chaque personnage du jeu possède une intelligence artificielle,
 * qui permet à chaque tour soit de chasser le PJ, soit de l'aider, soit
 * de faire quelque chose d'autre, en plus de pouvoir communiquer.</p>
 *
 * <p>Les extensions de cette classe doivent seulement préciser dans quel cas
 * le personnage doit chasser ou aider le PJ. <b>Par défaut</b>, chasser le
 * personnage joueur revient à le suivre et/ou l'attaquer et l'aider revient à
 * lui faire un don d'argent ou d'équipement.</p>
 *
 * @author Tom Abbouz
 * @version Février 2023
 * @see PersonnageDonjon
 * @see AgressifIa
 * @see AmicalIa
 * @see AmicalIa
 * @see DefensifIa
 * @see JoueurIa
 * @see NeutreIa
 */
public abstract class PersonnageIa {

  protected Personnage personnage;
  protected final List<String> messages;
  protected final String type;
  protected boolean attaqueJoueur;

  public PersonnageIa(Personnage personnage, String type, boolean attaqueJoueur) {
    this.personnage = personnage;
    this.messages = new ArrayList<String>();
    this.type = type;
    this.attaqueJoueur = attaqueJoueur;
  }

  /**
   * Déplace le personnage s'il n'y a pas d'obstacle.
   *
   * @param x    Coordonnée X visée
   * @param y    Coordonnée Y visée
   * @param tile La de correspondant à la position (x,y).
   */
  public boolean onEnter(int x, int y, Tile tile) {
    if (tile.isGround()) {
      personnage.setX(x);
      personnage.setY(y);
      return true;
    } else {
      return false;
    }
  }

  public void notifier(String message) {
    messages.add(message);
  }

  public List<String> getMessages() {
    return messages;
  }

    /**
     * Fonction principale du pattern <b>Template method</b>.
     * 
     * <p>Chaque PNJ peut chasser, ou aider le joueur ou errer, selon
     * cetaines conditions propres à leur IA.</p>
     *
     * @see PersonnageIa#doitChasser() conditionsPourChasserPj
     * @see PersonnageIa#doitAider() conditionsPourAiderPj
     */
    public final void onUpdate() {
      if (doitChasser()) {
        chasser(personnageChasse());
      } else if (doitAider()) {
        aider();
      } else {
        errer();
      }
    }

    abstract boolean doitChasser();
  
    abstract boolean doitAider();

    void chasser(Personnage proie) {
      
      notifier("Tu es ma proie " + proie.getNom() + ".");
      
      if(!personnage.attaquer(proie)) {
        int mx = 0;
        if(personnage.getX() < proie.getX()) {
          mx++;
        } else if(personnage.getX() > proie.getX()) {
          mx--;
        }

        int my = 0;
        if(personnage.getY() < proie.getY()) {
          my++;
        } else if(personnage.getY() > proie.getY()) {
          my--;
        }
        if(!personnage.moveBy(mx, my)) {
          personnage.attaquer(proie);
        } else {
          switch((int) (Math.random()*4)) {
          case 0:
            notifier("Cours " + proie.getNom() + ", tu ne peux pas m'échapper!");
            break;
          case 1:
            notifier("J'espère pour toi que tu cours vite " + proie.getNom() + "!");
            break;
          case 2:
            notifier("Si je t'attrape, ça va mal se passer pour toi " + proie.getNom() + "!");
            break;
          case 3:
            notifier("Tu es trop lent pour moi " + proie.getNom() + "!");
            break;
          default:
            break;
          }
        }
      }
    }
    
    protected Personnage personnageChasse() {
      return personnage.getElementEtage().getJoueur();
    }
   
    void aider() {
    }
    
    /**
     * Les PNJ se déplacent de façon aléatoire quand ils ne doivent n'y
     * aider, ni chasser le joueur.
     */
    public void errer() {
      int mx = (int) (Math.random() * 3) - 1;
      int my = (int) (Math.random() * 3) - 1;
  
      Personnage other = personnage.personnage(personnage.getX() + mx, personnage.getY() + my);
  
      if (other == null || !other.getNom().equals(personnage.getNom())) {
        personnage.moveBy(mx, my);
      }
    }
    
    public String getType() {
      return type;
    }
    
    protected boolean attaqueJoueur() {
      return attaqueJoueur;
    }
}
