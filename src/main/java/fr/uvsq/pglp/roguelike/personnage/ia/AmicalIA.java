package fr.uvsq.pglp.roguelike.personnage.ia;

public class AmicalIA extends PersonnageIA {

	private final int nbMaxAides = (int) (Math.random()*5) + 1;
	private int nbAides = 0;
	
	public AmicalIA(Personnage personnage) {
		super(personnage);
	}
	
	@Override
	boolean doitChasser() {
		return false;
	}
	
	@Override
	boolean doitAider() {
		
		if(nbMaxAides > nbAides) {
			nbAides ++;
			return true;
		}
		
		return false;
	}
}
