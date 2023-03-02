package fr.uvsq.pglp.roguelike.donjon.genererdonjon;

/**
 * Correspond au "Client" du pattern Strategy, pouvant utiliser
 * différent algorithmes de génération de donjon.
 *
 * @see GenerateurPredefini
 * @see GenerateurSallesFixesItemsAleatoires
 * @see SallesFixesItemsAleatoires
 * @See ToutPredefini
 */
public abstract class GenerationDonjon {

  private GenerateurDonjon generateurDonjon;

  /**
   * Constructeur de {@link GenerationDonjon}.
   *
   * @param generationDonjon Un algorithme générant un donjon
   *
   * @see GenerateurPredefini
   * @see GenerateurSallesFixesItemsAleatoires
   * 
   */
  public GenerationDonjon(GenerateurDonjon generationDonjon) {
    this.generateurDonjon = generationDonjon;
  }

  public void genererDonjon() {
    generateurDonjon.genererDonjon();
  }

  // public void SetGenerateurDonjon(GenerateurDonjon typeGenerateur) {
  //   this.generateurDonjon = typeGenerateur;
  // }
}
