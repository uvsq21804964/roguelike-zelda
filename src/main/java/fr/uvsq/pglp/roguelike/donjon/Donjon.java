package fr.uvsq.pglp.roguelike.donjon;

import fr.uvsq.pglp.roguelike.donjon.elements.Couloir;
import fr.uvsq.pglp.roguelike.donjon.elements.Salle;
import fr.uvsq.pglp.roguelike.personnage.Personnage;
import java.util.ArrayList;

/**
 * Génère un étage de donjon contenant des salles et des couloirs.
 *
 * <p>Utilise le pattern Strategy, afin d'utiliser deux générateurs différents :
 * {@link SallesFixesItemsAleatoires} ou {@link ToutPredefini}</p>
 *
 * @see Couloir
 * @see Salle
 * @see SallesFixesItemsAleatoires Premier algorithme pour générer un donjon
 * @see ToutPredefini Deuxième algorithme pour générer un donjon
 * @see UniversDeJeu
 * @author Tom Abbouz
 * @version Mars 2023
 */
public class Donjon implements UniversDeJeu {

  private Salle[] salles = new Salle[9];
  private ArrayList<Couloir> couloirs = new ArrayList<Couloir>();

  /**
   * Constructeur de Donjon.
   *
   * <p>Utilise le pattern Strategy, afin d'utiliser deux générateurs différents :
   * {@link SallesFixesItemsAleatoires} ou {@link ToutPredefini}</p>
   *
   * @see SallesFixesItemsAleatoires Premier algorithme pour générer un donjon
   * @see ToutPredefini Deuxième algorithme pour générer un donjon
   */
  public Donjon(String nom) {

    GenerationDonjon donjon = new SallesFixesItemsAleatoires();
    donjon.genererDonjon(nom);
    salles = donjon.getSalles();
    couloirs = donjon.getCouloirs();
  }

  public Salle[] getSalles() {
    return salles;
  }

  public ArrayList<Couloir> getCouloirs() {
    return couloirs;
  }

  @Override
  public Personnage getJoueur() {
    for (Salle s : salles) {
      if (s.getJoueur() != null) {
        return s.getJoueur();
      }
    }

    for (Couloir c : couloirs) {
      if (c.getJoueur() != null) {
        return c.getJoueur();
      }
    }
    return null;
  }
}
