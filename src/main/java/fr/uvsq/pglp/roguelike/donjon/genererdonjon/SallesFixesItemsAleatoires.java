package fr.uvsq.pglp.roguelike.donjon.genererdonjon;

/**
 * Deuxième client du pattern <b>Strategy</b> utilisant l'algorithme
 * {@link GenerateurSallesFixesItemsAleatoires} dans son constructeur
 * pour générer un donjon.
 *
 * @see GenerateurSallesFixesItemsAleatoires
 * @see GenerationDonjon
 * 
 * @author Tom Abbouz
 * @version Février 2023
 */
public class SallesFixesItemsAleatoires extends GenerationDonjon {

  public SallesFixesItemsAleatoires() {
    super(new GenerateurSallesFixesItemsAleatoires());
  }

}
