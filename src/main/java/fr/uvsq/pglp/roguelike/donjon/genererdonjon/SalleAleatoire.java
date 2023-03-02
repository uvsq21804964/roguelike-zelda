package fr.uvsq.pglp.roguelike.donjon.genererdonjon;

/**
 * Crée une {@link Salle} avec des PNJ et des équipements aux positions 
 * et nombres aléatoires.
 *
 * <p>Il y a, en moyenne, 15 cases par PNJ et 30 par équipements.</p>
 *
 * @see Salle
 * 
 * @author Tom Abbouz
 * @version Février 2023
 */
public class SalleAleatoire extends Salle {

  public SalleAleatoire(int numero, int largeur, int longueur) {
    super(numero, largeur, longueur);
  }

  @Override
  public void ajouterPersonnages() {

    for (int i = 0; i < plan.length; i++) {
      for (int j = 0; j < plan[0].length; j++) {
        if (plan[i][j] == ' ' && ((int) (Math.random() * 15)) == 0) {

          char personnage = 'A';
          switch ((int) (Math.random() * 7)) {
            case 0:
              personnage = 'B';
              break;
            case 1:
              personnage = 'C';
              break;
            case 2:
              personnage = 'D';
              break;
            case 3:
              personnage = 'E';
              break;
            default:
              break;
          }
          plan[i][j] = personnage;
        }
      }
    }
  }

  @Override
  public void ajouterItems() {

    for (int i = 0; i < plan.length; i++) {
      for (int j = 0; j < plan[0].length; j++) {
        if (plan[i][j] == ' ' && ((int) (Math.random() * 30)) == 0) {

          if (((int) (Math.random() * 2)) == 0) {
            char armure = ',';
            switch ((int) (Math.random() * 2)) {
              case 1:
                armure = '/';
                break;
              default:
                break;
            }
            plan[i][j] = armure;
          } else {
            char arme = ';';
            switch ((int) (Math.random() * 4)) {
              case 1:
                arme = ':';
                break;
              case 2:
                arme = '?';
                break;
              case 3:
                arme = '!';
                break;
              default:
                break;
            }
            plan[i][j] = arme;
          }
        }
      }
    }
  }
}
