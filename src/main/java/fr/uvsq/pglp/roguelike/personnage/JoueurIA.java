package fr.uvsq.pglp.roguelike.personnage;

public class JoueurIA extends PersonnageIA {

	public JoueurIA(Personnage personnage) {
		super(personnage);
	}
	
	@Override
	public void notifier(String message) {
		messages.add(message);
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
