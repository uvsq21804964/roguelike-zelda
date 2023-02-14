package fr.uvsq.pglp.roguelike.personnage.ia;

import fr.uvsq.pglp.roguelike.personnage.Personnage;

public class NeutreIA extends PersonnageIA {

	public NeutreIA(Personnage personnage) {
		super(personnage);
	}
	
	@Override
	boolean doitChasser() {

		//TODO
		return false;
		
	}
	
	@Override
	boolean doitAider() {
		return false;
	}

}
