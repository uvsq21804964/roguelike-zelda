package fr.uvsq.pglp.roguelike.donjon;

import fr.uvsq.pglp.roguelike.donjon.elements.Couloir;
import fr.uvsq.pglp.roguelike.donjon.elements.CouloirAvecEnnemis;
import fr.uvsq.pglp.roguelike.donjon.elements.CouloirAvecEquipements;
import fr.uvsq.pglp.roguelike.donjon.elements.CouloirSimple;
import fr.uvsq.pglp.roguelike.donjon.elements.Porte;
import fr.uvsq.pglp.roguelike.donjon.elements.Salle;
import fr.uvsq.pglp.roguelike.donjon.elements.SalleAleatoire;
import fr.uvsq.pglp.roguelike.donjon.elements.Tile;
import java.util.ArrayList;
import java.util.Random;


public class GenerateurSallesFixesItemsAleatoires implements GenerateurDonjon {

  private Salle[] salles = new Salle[9];
  private ArrayList<Couloir> couloirs = new ArrayList<Couloir>();

  @Override
  public void genererDonjon() {

    for (int i = 0; i < 9; i++) {
      salles[i] = creerSalle(i);
    }

    creationCouloirs();
  }

  private void creationCouloirs() {
    creerCouloir(salles[0], salles[1], false);
    creerCouloir(salles[0], salles[3], true);

    for (int i = 1; i < 9; i++) {
      if (i % 3 != 2) {
        switch ((int) (Math.random() * 3)) {
          case 0:
          case 1:
            creerCouloir(salles[i], salles[i + 1], false);
            break;
          default:
            break;
        }
      }

      if (i < 6) {
        switch ((int) (Math.random() * 3)) {
          case 0:
          case 1:
            creerCouloir(salles[i], salles[i + 3], true);
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
    int nb = borneInf + random.nextInt(etendu);
    return nb;
  }

  protected void creerCouloir(Salle salle1, Salle salle2, boolean vertical) {

    int largeur = (int) (Math.random() * 6) + 7;
    int longueur = (int) (Math.random() * 2) + 4;

    if (vertical) {
      int tmp = largeur;
      largeur = longueur;
      longueur = tmp;
    }

    Tile type = Tile.PORTE;
    if ((int) (Math.random() * 2) == 0) {
      type = Tile.PORTEACROCHETER;
    }
    Tile type2 = Tile.PORTE;
    if ((int) (Math.random() * 2) == 0) {
      type2 = Tile.PORTEACROCHETER;
    }

    int a = (int) (Math.random() * 4);

    if (a == 0) {
      int nbEquipement = ((int) (Math.random() * 2)) + 1;
      Couloir c = new CouloirAvecEquipements(salle1, salle2, nbEquipement, largeur, longueur);

      Porte porte1 = new Porte(type, salle1, c);
      Porte porte2 = new Porte(type2, salle2, c);
      c.ajouterPorte(porte1);
      c.ajouterPorte(porte2);
      salle1.ajouterPorte(porte1);
      salle2.ajouterPorte(porte2);
      couloirs.add(c);
    } else if (a == 1) {
      Couloir c = new CouloirSimple(salle1, salle2, largeur, longueur);

      Porte porte1 = new Porte(type, salle1, c);
      Porte porte2 = new Porte(type2, salle2, c);
      c.ajouterPorte(porte1);
      c.ajouterPorte(porte2);
      salle1.ajouterPorte(porte1);
      salle2.ajouterPorte(porte2);
      couloirs.add(c);
    } else {
      int nbEnnemis = ((int) (Math.random() * 3)) + 1;
      Couloir c = new CouloirAvecEnnemis(salle1, salle2, nbEnnemis, largeur, longueur);

      Porte porte1 = new Porte(type, salle1, c);
      Porte porte2 = new Porte(type2, salle2, c);
      c.ajouterPorte(porte1);
      c.ajouterPorte(porte2);
      salle1.ajouterPorte(porte1);
      salle2.ajouterPorte(porte2);
      couloirs.add(c);
    }
  }

  @Override
  public Salle[] getSalles() {
    return salles;
  }

  @Override
  public ArrayList<Couloir> getCouloirs() {
    return couloirs;
  }

  @Override
  public void ajouterJoueur() {
    salles[0].ajouterJoueur();
  }
}
