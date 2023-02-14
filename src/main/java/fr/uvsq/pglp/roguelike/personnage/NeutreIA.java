package fr.uvsq.pglp.roguelike.personnage;

public class NeutreIA extends PersonnageIA {

	public NeutreIA(Personnage personnage) {
		super(personnage);
	}
	
	@Override
	boolean doitChasser() {
		
	}
	
	@Override
	boolean doitAider() {
		return false;
	}

}
