package fr.uvsq.pglp.roguelike.donjon;

/**
 * {@link GenerationDonjon} utilisant l'algorithme {@link GenerateurSallesFixesItemsAleatoires}.
 *
 * @see GenerationDonjon
 * @see GenerateurDonjon
 *
 * @author Tom Abbouz
 * @version Mars 2023
 */
public class SallesFixesItemsAleatoires extends GenerationDonjon {

  public SallesFixesItemsAleatoires() {
    super(new GenerateurSallesFixesItemsAleatoires());
  }

}
