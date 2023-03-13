package fr.uvsq.pglp.roguelike.donjon;

import fr.uvsq.pglp.roguelike.donjon.elements.Couloir;
import fr.uvsq.pglp.roguelike.donjon.elements.CouloirSimple;
import fr.uvsq.pglp.roguelike.donjon.elements.Porte;
import fr.uvsq.pglp.roguelike.donjon.elements.Salle;
import fr.uvsq.pglp.roguelike.donjon.elements.SalleFixe;
import fr.uvsq.pglp.roguelike.donjon.elements.Tile;
import java.util.ArrayList;

/**
 * Algorithme générant un {@link Donjon} basique, sans aléatoire.
 *
 * <p>Il n'utilise que des {@link CouloirSimple}s et des 
 * {@link SalleFixe}s.</p>
 *
 * @see GenerateurDonjon
 *
 * @author Tom Abbouz
 * @version Mars 2023
 */
public class GenerateurPredefini implements GenerateurDonjon {

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

    for (int i = 0; i < 9; i++) {
      if (i >= 3) {
        creerCouloir(salles[i], salles[i - 3], true);
      }

      if (i % 3 != 0) {
        creerCouloir(salles[i], salles[i - 1], false);
      }
    }
  }

  protected Salle creerSalle(int numero) {
    return new SalleFixe(numero);
  }

  protected void creerCouloir(Salle salle1, Salle salle2, boolean vertical) {

    int largeur = 8;
    int longueur = 4;

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

    Couloir c = new CouloirSimple(salle1, salle2, largeur, longueur);

    Porte porte1 = new Porte(type, salle1, c);
    Porte porte2 = new Porte(type2, salle2, c);
    c.ajouterPorte(porte1);
    c.ajouterPorte(porte2);
    salle1.ajouterPorte(porte1);
    salle2.ajouterPorte(porte2);
    couloirs.add(c);
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
  public void ajouterJoueur(String nom) {
    salles[0].ajouterJoueur(nom);
  }
}
