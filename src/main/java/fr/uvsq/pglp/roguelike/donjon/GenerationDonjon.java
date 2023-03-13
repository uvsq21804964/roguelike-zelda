package fr.uvsq.pglp.roguelike.donjon;

import fr.uvsq.pglp.roguelike.donjon.elements.Couloir;
import fr.uvsq.pglp.roguelike.donjon.elements.Salle;
import java.util.ArrayList;

/**
 * Génération d'un donjon grâce à un {@link GenerateurDonjon}.
 *
 * <p>Respect du pattern <b>Strategy</b> par l'utilisation de différents algorithmes
 * de génération de {@link Donjon}.</p>
 *
 * @see GenerateurDonjon
 * @see GenerateurPredefini
 * @see GenerateurSallesFixesItemsAleatoires
 *
 * @author Tom Abbouz
 * @version Mars 2023
 */
public abstract class GenerationDonjon {

  private GenerateurDonjon generateurDonjon;

  public GenerationDonjon(GenerateurDonjon generationDonjon) {
    this.generateurDonjon = generationDonjon;
  }

  public void genererDonjon(String nom) {
    generateurDonjon.genererDonjon();
    generateurDonjon.ajouterJoueur(nom);
  }

  public Salle[] getSalles() {
    return generateurDonjon.getSalles();
  }

  public ArrayList<Couloir> getCouloirs() {
    return generateurDonjon.getCouloirs();
  }

  //public void SetGenerateurDonjon(GenerateurDonjon typeGenerateur) {
  //this.generateurDonjon = typeGenerateur;
  //}
}
