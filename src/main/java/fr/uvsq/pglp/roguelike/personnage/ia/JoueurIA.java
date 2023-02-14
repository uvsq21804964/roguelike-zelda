package fr.uvsq.pglp.roguelike.personnage.ia;

import java.util.List;

import fr.uvsq.cprog.roguelike.FieldOfView;
import fr.uvsq.pglp.roguelike.donjon.Tile;
import fr.uvsq.pglp.roguelike.personnage.Personnage;

public class JoueurIA extends PersonnageIA {

	private final List<String> messages;
    private final FieldOfView fov;
    
    
	public JoueurIA(Personnage personnage, List<String> messages, FieldOfView fov) {
		super(personnage);
		this.messages = messages;
        this.fov = fov;
	}
	
	@Override
	public void notifier(String message) {
		//messages.add(message);
	}
	
	@Override
    public Tile souvenirTuile(int wx, int wy) {
        return fov.tile(wx, wy);
    }
	
	@Override
	public boolean peutVoir(int wx, int wy) {
        return fov.isVisible(wx, wy);
    }
	
	@Override
	boolean doitChasser() {
		return false;
	}
	
	@Override
	boolean doitAider() {
		return false;
	}

}
