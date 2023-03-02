package fr.uvsq.pglp.roguelike.donjon.genererdonjon;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Un algorithme générant un donjon de 9 {@link Salle}s en maximisant l'aléatoire.
 *
 * <p>Chaque couple de {@link Salle}s voisines est marqué par un 
 * {@link CouloirSimple}, soit par un {@link CouloirAvecEquipements},
 * soit par un {@link CouloirAvecEnnemis}, soit par une absence de {@link Couloir}.
 * Les salles sont toutes des {@link SalleAleatoire}s dont la largeur et la longueur
 * varie de 7 à 12 aléatoirement.</p>
 *
 * @see GenerateurDonjon
 * @see SalleAleatoire
 *
 * @author Tom Abbouz
 *
 */
public class GenerateurSallesFixesItemsAleatoires implements GenerateurDonjon {

  private Salle[] salles = new Salle[9];
  private ArrayList<Couloir> couloirs = new ArrayList<Couloir>();
  private char[][] etage;
  private int nbFichier = 0;

  @Override
  public void genererDonjon() {

    for (int i = 0; i < 9; i++) {
      salles[i] = creerSalle(i);
    }

    creationCouloirs();
    creerEtage();
    placerSalles();
    joindreSalles();
    connexe();
    ecrireEtage();
  }

  private void ecrireEtage() {
    nbFichier++;
    File file = new File("EtageDonjon" + nbFichier + ".txt");
    try {
      BufferedWriter bw = new BufferedWriter(new FileWriter(file));

      for (int j = 0; j < etage[0].length; j++) {
        for (int i = 0; i < etage.length; i++) {
          bw.write(etage[i][j]);
        }
        if (j != etage[0].length - 1) {
          bw.newLine();
        }
      }
      bw.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void placerSalles() {

    int l = etage.length;

    boolean[] salleAvecCouloirs = new boolean[9];
    Arrays.fill(salleAvecCouloirs, false);
    for (Couloir c : couloirs) {
      salleAvecCouloirs[c.salle1.numero] = true;
      salleAvecCouloirs[c.salle2.numero] = true;
    }


    for (Salle s : salles) {

      int n = s.numero;
      if (!salleAvecCouloirs[n]) {
        continue;
      }

      for (int i = 0; i < s.plan.length; i++) {
        for (int j = 0; j < s.plan[0].length; j++) {
          char c = s.plan[i][j];
          if (c == '#') {
            c = Character.forDigit(n, 10);
            if (i == 0 || i == s.plan.length - 1) {
              if (j == 0 || j == s.plan[0].length - 1) {
                c = '-';
              }
            }
          } else if (c == ' ') {
            c = '.';
          }
          etage[i + (n % 3) * (l / 3) + 1][j + (n / 3) * (l / 3) + 1] = c;
        }
      }
    }
  }

  private void creerEtage() {

    int nbMinCase = 0;
    for (Salle s : salles) {
      nbMinCase += s.plan.length * s.plan[0].length;
    }

    int largeurEtage = (int) (Math.sqrt(nbMinCase * 3));

    etage = new char[largeurEtage][largeurEtage];
    for (int i = 0; i < largeurEtage; i++) {
      for (int j = 0; j < largeurEtage; j++) {
        etage[i][j] = ' ';
      }
    }
  }

  private void joindreSalles() {

    for (Couloir c : couloirs) {
      char salle1 = Character.forDigit(c.salle1.numero, 10);
      char salle2 = Character.forDigit(c.salle2.numero, 10);

      Integer[] point1 = { 0, 0 };
      Integer[] point2 = { 0, 0 };

      out:
      for (int i = 0; i < etage.length; i++) {
        for (int j = 0; j < etage[0].length; j++) {
          if (etage[i][j] == '-') {
            if ((etage[i - 1][j] == salle1 || etage[i + 1][j] == salle1)
                    && (etage[i][j - 1] == salle1 || etage[i][j + 1] == salle1)) {

              if (point2[0] == 0 && point2[1] == 0) {
                point1[0] = i;
                point1[1] = j;
              } else {
                point1[0] = i;
                point1[1] = j;
                break out;
              }

            }
            if ((etage[i - 1][j] == salle2 || etage[i + 1][j] == salle2)
                    && (etage[i][j - 1] == salle2 || etage[i][j + 1] == salle2)) {

              if (point1[0] == 0 && point1[1] == 0) {
                point2[0] = i;
                point2[1] = j;
              } else {
                point2[0] = i;
                point2[1] = j;
                break out;
              }
            }
          }
        }
      }

      if (point1[0] != point2[0] && point1[0] != point2[0]) {
        point1[0] = 0;
        point1[1] = 0;
        point2[0] = 0;
        point2[1] = 0;
        out:
        for (int j = 0; j < etage[0].length; j++) {
          for (int i = etage.length - 1; i >= 0; i--) {
            if (etage[i][j] == '-') {
              if (etage[i - 1][j] == salle1 || etage[i + 1][j] == salle1
                      && etage[i][j - 1] == salle1 || etage[i][j + 1] == salle1) {

                if (point2[0] == 0 && point2[1] == 0) {
                  point1[0] = i;
                  point1[1] = j;
                } else {
                  point1[0] = i;
                  point1[1] = j;
                  break out;
                }

              }
              if (etage[i - 1][j] == salle2 || etage[i + 1][j] == salle2
                      && etage[i][j - 1] == salle2 || etage[i][j + 1] == salle2) {

                if (point1[0] == 0 && point1[1] == 0) {
                  point2[0] = i;
                  point2[1] = j;
                } else {
                  point2[0] = i;
                  point2[1] = j;
                  break out;
                }
              }
            }
          }
        }
      }
      genererCouloir(point1, point2, c);
      ecrireEtage();
    }
    remplacerChiffresParMurs();
    ecrireEtage();
  }

  private void remplacerChiffresParMurs() {

    for (int i = 0; i < etage.length; i++) {
      for (int j = 0; j < etage[0].length; j++) {
        if (Character.isDigit(etage[i][j]) || etage[i][j] == '-' || etage[i][j] == ' ') {
          etage[i][j] = '#';
        }
      }
    }

    for (int i = 0; i < etage.length; i++) {
      for (int j = 0; j < etage[0].length; j++) {
        if (etage[i][j] == '.') {
          etage[i][j] = ' ';
        }
      }
    }

  }

  private void genererCouloir(Integer[] point1, Integer[] point2, Couloir c) {

    int x1 = point2[0];
    int x0 = point1[0];
    int y1 = point2[1];
    int y0 = point1[1];

    int nbAjout = 0;

    if (c instanceof CouloirAvecEquipements) {
      nbAjout = c.nbEquipement;
    } else if (c instanceof CouloirAvecEnnemis) {
      nbAjout = c.nbEnnemis;
    }

    if (x1 == x0) {
      etage[x0 + 2][y0] = 'P';
      etage[x1 + 2][y1] = 'P';
      etage[x0 + 3][y0] = 'P';
      etage[x1 + 3][y1] = 'P';
      if (y1 > y0) {
        y1--;
        y0++;
      } else {
        y1++;
        y0--;
      }
    } else if (y1 == y0) {
      etage[x0][y0 + 2] = 'P';
      etage[x1][y1 + 2] = 'P';
      etage[x0][y0 + 3] = 'P';
      etage[x1][y1 + 3] = 'P';
      if (x1 > x0) {
        x1--;
        x0++;
      } else {
        x1++;
        x0--;
      }
    }

    int dx = Math.abs(x1 - x0);
    int dy = Math.abs(y1 - y0);

    int sx = x0 < x1 ? 1 : -1;
    int sy = y0 < y1 ? 1 : -1;
    int err = dx - dy;

    if (y0 == y1) {
      while (true) {
        etage[x0][y0 + 1] = '#';
        etage[x0][y0 + 2] = '.';
        etage[x0][y0 + 3] = '.';
        etage[x0][y0 + 4] = '#';

        if (nbAjout != 0 && (int) (Math.random() * 2) == 0) {
          int s = (int) (Math.random() * 2);
          if (c instanceof CouloirAvecEquipements) {
            etage[x0][y0 + 2 + s] = equipement();
          } else if (c instanceof CouloirAvecEnnemis) {
            etage[x0][y0 + 2 + s] = ennemi();
          }
          nbAjout--;
        }

        if (x0 == x1 && y0 == y1) {
          break;
        }

        int e2 = err * 2;
        if (e2 > -dy) {
          err -= dy;
          x0 += sx;
        }
        if (e2 < dx) {
          err += dx;
          y0 += sy;
        }
      }
    } else if (x0 == x1) {
      while (true) {
        etage[x0 + 1][y0] = '#';
        etage[x0 + 2][y0] = '.';
        etage[x0 + 3][y0] = '.';
        etage[x0 + 4][y0] = '#';

        if (nbAjout != 0 && (int) (Math.random() * 2) == 0) {
          int s = (int) (Math.random() * 2);
          if (c instanceof CouloirAvecEquipements) {
            etage[x0 + 2 + s][y0] = equipement();
          } else if (c instanceof CouloirAvecEnnemis) {
            etage[x0 + 2 + s][y0] = ennemi();
          }
          nbAjout--;
        }

        if (x0 == x1 && y0 == y1) {
          break;
        }

        int e2 = err * 2;
        if (e2 > -dy) {
          err -= dy;
          x0 += sx;
        }
        if (e2 < dx) {
          err += dx;
          y0 += sy;
        }
      }
    }
  }

  private void creationCouloirs() {
    creerCouloir(salles[0], salles[1]);
    creerCouloir(salles[0], salles[3]);

    for (int i = 1; i < 9; i++) {
      if (i % 3 != 2) {
        switch ((int) (Math.random() * 3)) {
          case 0:
          case 1:
            creerCouloir(salles[i], salles[i + 1]);
            break;
          default:
            break;
        }
      }

      if (i < 6) {
        switch ((int) (Math.random() * 3)) {
          case 0:
          case 1:
            creerCouloir(salles[i], salles[i + 3]);
            break;
          default:
            break;
        }
      }
    }
  }

  protected Salle creerSalle(int numero) {
    int largeur = genererInt(7, 12);
    int longueur = genererInt(7, 12);
    return new SalleAleatoire(numero, largeur, longueur);
  }

  private int genererInt(int borneInf, int borneSup) {

    Random random = new Random();

    int etendu = Math.max(borneSup - borneInf + 1, 1);

    int nb;
    nb = borneInf + random.nextInt(etendu);

    return nb;
  }

  protected void creerCouloir(Salle salle1, Salle salle2) {

    switch ((int) (Math.random() * 4)) {
      case 0:
        int nbEquipement = ((int) (Math.random() * 2)) + 1;
        couloirs.add(new CouloirAvecEquipements(salle1, salle2, nbEquipement));
        break;
      case 1:
        couloirs.add(new CouloirSimple(salle1, salle2));
        break;
      default:
        int nbEnnemis = ((int) (Math.random() * 3)) + 1;
        couloirs.add(new CouloirAvecEnnemis(salle1, salle2, nbEnnemis));
        break;
    }
  }

  private char equipement() {

    if (((int) (Math.random() * 2)) == 0) {
      char armure = ',';
      switch ((int) (Math.random() * 2)) {
        case 1:
          armure = '/';
          break;
        default:
          break;
      }
      return armure;
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
      return arme;
    }
  }

  private char ennemi() {

    char ennemi = 'A';

    switch ((int) (Math.random() * 10)) {
      case 0:
        ennemi = 'B';
        break;
      case 1:
        ennemi = 'C';
        break;
      case 2:
        ennemi = 'D';
        break;
      case 3:
        ennemi = 'E';
        break;
      default:
        break;
    }

    return ennemi;
  }

  private void connexe() {

    int iteration = 1;

    // Copions etage dans plan
    char[][] plan = new char[etage.length][etage[0].length];
    for (int i = 0; i < plan.length; i++) {
      for (int j = 0; j < plan[0].length; j++) {
        plan[i][j] = etage[i][j];
      }
    }

    // Initialisons la source des déplacements possibles
    out:
    for (int i = 1; i < plan.length - 1; i++) {
      for (int j = 1; j < plan[0].length - 1; j++) {
        if (plan[i][j] != '#') {
          plan[i][j] = '-';
          break out;
        }
      }
    }

    // Propageons les deplacements possibles
    while (iteration < (plan.length - 2) * (plan[0].length - 2)) {
      for (int i = 1; i < plan.length - 1; i++) {
        for (int j = 1; j < plan[0].length - 1; j++) {
          if (plan[i][j] != '#') {
            if (plan[i + 1][j] == '-' || plan[i - 1][j] == '-'
                    || plan[i][j - 1] == '-' || plan[i][j + 1] == '-') {
              plan[i][j] = '-';
            }
          }
        }
      }
      iteration++;
    }

    for (int i = 1; i < plan.length - 1; i++) {
      for (int j = 1; j < plan[0].length - 1; j++) {
        if (plan[i][j] != '-' && plan[i][j] != '#') {
          etage[i][j] = '#';
        }
      }
    }


  }

}
