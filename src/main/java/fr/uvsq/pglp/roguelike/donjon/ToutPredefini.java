package fr.uvsq.pglp.roguelike.donjon;

/**
 * {@link GenerationDonjon} utilisant l'algorithme {@link GenerateurPredefini}.
 *
 * @see GenerationDonjon
 * @see GenerateurDonjon
 *
 * @author Tom Abbouz
 * @version Mars 2023
 */
public class ToutPredefini extends GenerationDonjon {

  public ToutPredefini() {
    super(new GenerateurPredefini());
  }

}
