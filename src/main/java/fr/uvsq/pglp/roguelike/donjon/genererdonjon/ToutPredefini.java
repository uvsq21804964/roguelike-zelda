package fr.uvsq.pglp.roguelike.donjon.genererdonjon;

/**
 * Premier client du pattern <b>Strategy</b> utilisant l'algorithme
 * {@link GenerateurPredefini} dans son constructeur pour générer un donjon.
 *
 * @see GenerateurPredefini
 * @see GenerationDonjon
 * 
 * @author Tom Abbouz
 * @version Février 2023
 */
public class ToutPredefini extends GenerationDonjon {

  public ToutPredefini() {
    super(new GenerateurPredefini());
  }

}
