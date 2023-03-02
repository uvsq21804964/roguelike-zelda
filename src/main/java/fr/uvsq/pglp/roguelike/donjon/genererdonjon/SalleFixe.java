package fr.uvsq.pglp.roguelike.donjon.genererdonjon;

/**
 * Crée une {@link Salle} de taille 8x8 avec deux PNJ et deux équipements choisis
 * aléatoirement, mais aux positions fixées.
 *
 * @see Salle
 * 
 * @author Tom Abbouz
 * @version Février 2023
 */
public class SalleFixe extends Salle {

  public SalleFixe(int numero) {
    super(numero, 8, 8);
  }

  @Override
  public void ajouterPersonnages() {

    int i = 0;

    while (i < 2) {
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
      plan[6][5 + i] = personnage;
      i++;
    }

  }

  @Override
  public void ajouterItems() {

    char armure = ',';
    switch ((int) (Math.random() * 2)) {
      case 1:
        armure = '/';
        break;
      default:
        break;
    }
    plan[3][3] = armure;

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
    plan[4][3] = arme;
  }
}
