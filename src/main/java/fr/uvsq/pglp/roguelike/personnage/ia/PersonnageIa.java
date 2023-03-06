package fr.uvsq.pglp.roguelike.personnage.ia;


import fr.uvsq.pglp.roguelike.personnage.Personnage;

/**
 * Classe abstraite représentant les différentes types d'IA à partir du pattern
 * <b>Template Method</b>.
 *
 * <p>Chaque personnage du jeu possède une intelligence artificielle,
 * qui permet à chaque tour soit de chasser le PJ, soit de l'aider, soit
 * de faire quelque chose d'autre.</p>
 *
 * <p>Les extensions de cette classe doivent seulement préciser dans quel cas 
 * le personnage doit chasser ou aider le PJ. <b>Par défaut</b>, chasser le 
 * personnage joueur revient à le suivre et/ou l'attaquer et l'aider revient à 
 * lui faire un don d'argent ou d'équipement.</p>
 *
 * @see PersonnageDonjon
 *
 * @see AgressifIa
 * @see AmicalIa
 * @see AmicalIa
 * @see DefensifIa
 * @see JoueurIa
 * @see NeutreIa
 *
 * @author Tom Abbouz
 * @version Février 2023
 */
public abstract class PersonnageIa {

  protected Personnage personnage;

  public PersonnageIa(Personnage personnage) {
    this.personnage = personnage;
  }

//  /**
//   * Déplace le personnage s'il n'y a pas d'obstacle.
//   *
//   * @param x Coordonnée X visée
//   * @param y Coordonnée Y visée
//   * @param tile La de correspondant à la position (x,y).
//   */
//  public void onEnter(int x, int y, Tile tile) {
//    if (tile.isGround()) {
//      personnage.setX(x);
//      personnage.setY(y);
//    } else {
//      if (tile.equals(Tile.WALL)) {
//        notifier("C'est une paroi !");
//      }
//    }
//  }
//  
//  /**
//   * Fonction principale du pattern <b>Template method</b>.
//   * 
//   * <p>Chaque PNJ peut chasser, ou aider le joueur ou errer, selon
//   * cetaines conditions propres à leur IA.</p>
//   *
//   * @see PersonnageIa#doitChasser() conditionsPourChasserPj
//   * @see PersonnageIa#doitAider() conditionsPourAiderPj
//   */
//  public final void onUpdate() {
//    if (doitChasser()) {
//      chasser(world.player());
//    } else if (doitAider()) {
//      aider();
//    } else {
//      errer();
//    }
//  }
//
//  abstract boolean doitChasser();
//
//  abstract boolean doitAider();
//
//  /**
//   * Le {@link PersonnageDonjon} associé à l'IA chasse une cible.
//   *
//   * @param cible Le {@link PersonnageDonjon} devant être chassé.
//   */
//  public void chasser(Personnage cible) {
//    List<Point> points = new Path(personnage, cible.x, cible.y).points();
//
//    if (points != null) {
//      int mx = points.get(0).x - personnage.x;
//      int my = points.get(0).y - personnage.y;
//
//      personnage.moveBy(mx, my);
//    }
//  }
//
//  /**
//   * Le {@link PersonnageDonjon} associé à l'IA aide le PJ en
//   * faisant un don de pièces ou d'équipement.
//   * 
//   * <p>Ce don est simplement posé au sol, le PJ devant le ramasser.</p>
//   */
//  public void aider() {
//    switch ((int) (Math.random() * 2)) {
//      case 0:
//        personnage.jeter(personnage.getSac().getItemAleatoire());
//        break;
//      case 1:
//        personnage.laisserRubisAleatoire();
//        break;
//      default:
//        break;
//    }
//  }
//
//  public void notifier(String message) {
//  }
//
//  /**
//   * Renvoie <b>true</b> si l'emplacement et visible et 
//   * <b>false</b> sinon.
//   *
//   * @param wx Coordonnée X de l'emplacement visé
//   * @param wy Coordonnée Y de l'emplacement visé
//   * @return true si l'emplacement est visible, false sinon
//   */
//  public boolean peutVoir(int wx, int wy) {
//
//    if (Math.pow(personnage.x - wx, 2) + Math.pow(personnage.getY() - wy, 2) 
//        > Math.pow(personnage.distanceVue(), 2)) {
//      return false; 
//    }
//
//    for (Point p : new Line(personnage.getX(), personnage.getX(), wx, wy)) {
//      if (personnage.tile(p.x, p.y).isGround() || p.x == wx && p.y == wy) {
//        continue;
//      }
//
//      return false;
//    }
//
//    return true;
//  }
//
//  /**
//   * Les PNJ se déplacent de façon aléatoire quand ils ne doivent n'y
//   * aider, ni chasser le joueur.
//   */
//  public void errer() {
//    int mx = (int) (Math.random() * 3) - 1;
//    int my = (int) (Math.random() * 3) - 1;
//
//    Personnage other = personnage.personnage(personnage.getX() + mx, personnage.getY() + my);
//
//    if (other == null || !other.getNom().equals(personnage.getNom())) {
//      personnage.moveBy(mx, my);
//    }
//  }
}
