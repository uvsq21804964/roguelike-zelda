package fr.uvsq.pglp.roguelike.donjon.genererdonjon;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Un algorithme générant un donjon de 9 {@link SalleFixe}s en minimisant l'aléatoire.
 * 
 * <p>Les {@link SalleFixe}s voisines sont toutes reliées par des {@link CouloirSimple}s en 
 * forme de quadrillage. Il y a toujours deux PNJ et deux équipements par {@link SalleFixe}
 * (choisis aléatoirement).</p>
 *
 * @see GenerateurDonjon
 *
 * @author Tom Abbouz
 *
 */
public class GenerateurPredefini implements GenerateurDonjon {

  private Salle[] salles = new Salle[9];
  private ArrayList<Couloir> couloirs = new ArrayList<Couloir>();
  private char[][] etage;

  @Override
  public void genererDonjon() {

    for (int i = 0; i < 9; i++) {
      salles[i] = creerSalle(i);
    }

    creationCouloirs();
    etage = creerEtage();
    placerSalles();
    joindreSalles();
    ecrireEtage();
  }

  private void ecrireEtage() {

    File file = new File("EtageDonjon.txt");
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

    for (Salle s : salles) {

      int n = s.numero;

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

  private char[][] creerEtage() {

    int nbMinCase = 0;
    for (Salle s : salles) {
      nbMinCase += s.plan.length * s.plan[0].length;
    }

    int largeurEtage = (int) (Math.sqrt(nbMinCase * 2));

    etage = new char[largeurEtage][largeurEtage];
    for (int i = 0; i < largeurEtage; i++) {
      for (int j = 0; j < largeurEtage; j++) {
        etage[i][j] = ' ';
      }
    }

    return etage;
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
      genererCouloir(point1, point2);
    }
    remplacerChiffresParMurs();
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

  private void genererCouloir(Integer[] point1, Integer[] point2) {

    int x1 = point2[0];
    int x0 = point1[0];
    int y1 = point2[1];
    int y0 = point1[1];

    if (x1 == x0) {
      etage[x0 + 1][y0] = 'P';
      etage[x1 + 1][y1] = 'P';
      if (y1 > y0) {
        y1--;
        y0++;
      } else {
        y1++;
        y0--;
      }
    } else if (y1 == y0) {
      etage[x0][y0 + 1] = 'P';
      etage[x1][y1 + 1] = 'P';
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
        etage[x0][y0] = '#';
        etage[x0][y0 + 1] = '.';
        etage[x0][y0 + 2] = '#';

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
        etage[x0][y0] = '#';
        etage[x0 + 1][y0] = '.';
        etage[x0 + 2][y0] = '#';

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

    for (int i = 0; i < 9; i++) {
      if (i >= 3) {
        creerCouloir(salles[i], salles[i - 3]);
      }

      if (i % 3 != 0) {
        creerCouloir(salles[i], salles[i - 1]);
      }
    }
  }

  protected Salle creerSalle(int numero) {
    return new SalleFixe(numero);
  }

  protected void creerCouloir(Salle salle1, Salle salle2) {
    couloirs.add(new CouloirSimple(salle1, salle2));
  }
}

