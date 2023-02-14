package fr.uvsq.pglp.roguelike.donjon;

public abstract class GenerationDonjon {

	private GenerateurDonjon generateurDonjon;
	
	public GenerationDonjon(GenerateurDonjon generationDonjon) {
		this.generateurDonjon = generationDonjon;
	}
	
	public void genererDonjon() {
		generateurDonjon.genererDonjon();
	}
	
//	public void SetGenerateurDonjon(GenerateurDonjon typeGenerateur) {
//		this.generateurDonjon = typeGenerateur;
//	}
}
