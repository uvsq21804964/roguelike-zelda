package fr.uvsq.pglp.roguelike.donjon;

import fr.uvsq.pglp.roguelike.donjon.elements.Couloir;
import fr.uvsq.pglp.roguelike.donjon.elements.Salle;
import java.util.ArrayList;

/**
 * Encapsulation des différents algorithmes pour générer un {@link Donjon}.
 *
 * <p>Afin de respecter le pattern <b>Strategy</b>.</p>
 *
 * @see GenerateurPredefini
 * @see GenerateurSallesFixesItemsAleatoires
 *
 * @author Tom Abbouz
 * @version Mars 2023
 */
public interface GenerateurDonjon {
  public void genererDonjon();

  public Salle[] getSalles();

  public ArrayList<Couloir> getCouloirs();

  void ajouterJoueur(String nom);
}
