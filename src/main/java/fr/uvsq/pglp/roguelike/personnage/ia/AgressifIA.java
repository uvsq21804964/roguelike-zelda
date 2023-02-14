package fr.uvsq.pglp.roguelike.personnage.ia;

public class AgressifIA extends PersonnageIA {

	public AgressifIA(Personnage personnage) {
		super(personnage);
	}
	
	@Override
	boolean doitChasser() {
		return true;
	}
	
	@Override
	boolean doitAider() {
		return false;
	}
}
