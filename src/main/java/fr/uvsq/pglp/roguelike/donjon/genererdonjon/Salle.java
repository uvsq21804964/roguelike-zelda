package fr.uvsq.pglp.roguelike.donjon.genererdonjon;

/**
 * Encapsulation des différents types de salle.
 *
 * <p>Une salle est rectangulaire et possède un certains nombre
 * de PNJ et d'equipements. Le nombre de trésor dépend de la 
 * surface de la Salle.</p>
 *
 * @see SalleAleatoire
 * @see SalleFixe
 *
 * @author Tom Abbouz
 *
 */
public abstract class Salle {

  protected char[][] plan;
  protected int numero;

  /**
   * Constructeur de la classe {@link Salle}.
   *
   * @param numero Le numero de la salle dans l'étage du donjon
   * @param largeur La largeur (horizontale) de la salle
   * @param longueur La longueur (verticale) de la salle
   * 
   */
  public Salle(int numero, int largeur, int longueur) {
    this.plan = new char[largeur][longueur];
    this.numero = numero;
    formerMurs();
    ajouterItems();
    ajouterPersonnages();
    ajouterTresor();
  }

  private void formerMurs() {
    for (int i = 0; i < plan.length; i++) {
      for (int j = 0; j < plan[0].length; j++) {
        plan[i][j] = ' ';
        if (i == 0 || i == plan.length - 1 || j == 0 || j == plan[0].length - 1) {
          plan[i][j] = '#';
        }
      }
    }
  }

  private void ajouterTresor() {

    for (int i = 0; i < plan.length; i++) {
      for (int j = 0; j < plan[0].length; j++) {
        if (plan[i][j] == ' ') {
          switch ((int) (Math.random() * 100)) {
            case 0:
              char typeTresor = 'x';
              if ((int) (Math.random() * 2) == 0) {
                typeTresor = 'X';
              }
              plan[i][j] = typeTresor;
              break;
            default:
              break;
          }
        }
      }
    }
  }

  public abstract void ajouterItems();

  public abstract void ajouterPersonnages();
}
