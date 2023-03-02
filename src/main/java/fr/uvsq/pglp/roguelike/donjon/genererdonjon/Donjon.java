package fr.uvsq.pglp.roguelike.donjon.genererdonjon;

/**
 * La classe <b>Donjon</b> génère un étage à l'aide du pattern
 * <b>Strategy</b>. 
 * 
 * <p>Dans notre cas, il génère 9 {@link SalleAleatoire}s, avec des equipements et 
 * des PNJ de facon aléatoire. De plus, les {@link Couloir}s peuvent 
 * être vide, contenir des equipements ou contenir des PNJ.</p>
 *
 * @see UniversDeJeu
 * @see GenerationDonjon
 *
 * @author Tom Abbouz
 *
 */
public class Donjon implements UniversDeJeu {

  /**
   * Constructeur de {@link Donjon}.
   * Génère un étage de donjon à partir de la classe 
   * {@link SallesFixesItemsAleatoires}.
   *
   * @see GenerationDonjon
   * 
   */
  public Donjon() {

    GenerationDonjon donjon = new SallesFixesItemsAleatoires();
    generer(donjon);
  }
  
  
  @Override
  public void generer(GenerationDonjon donjon) {
    donjon.genererDonjon();
  }
}
