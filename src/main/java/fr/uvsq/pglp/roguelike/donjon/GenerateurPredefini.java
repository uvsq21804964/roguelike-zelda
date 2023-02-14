package fr.uvsq.pglp.roguelike.donjon;

public class GenerateurPredefini implements GenerateurDonjon {

	@Override
	public void genererDonjon() {
		
		Salle salles[] = new Salle[9];
		
		for(int i = 0 ; i < 9 ; i++) {
			salles[i] = creerSalle();
		}
		
		for(int i = 0 ; i < 9 ; i++) {
			if(i >= 3) {
				salles[i].connecter(salles[i-3]);
			}
			
			if(i%3 != 0) {
				salles[i].connecter(salles[i-1]);
			}
		}
	}
	
	protected Salle creerSalle()  {
		return new SalleFixe();
	}
}
