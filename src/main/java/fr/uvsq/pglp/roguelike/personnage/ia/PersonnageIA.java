package fr.uvsq.pglp.roguelike.personnage.ia;

import java.util.List;

import fr.uvsq.pglp.roguelike.personnage.Personnage;

public abstract class PersonnageIA {
	
    protected Personnage personnage;

    public PersonnageIA(Personnage personnage) {
        this.personnage = personnage;
        this.personnage.setPersonnageIA(this);
    }

    public void onEnter(int x, int y, Tile tile) {
        if (tile.isGround()) {
            personnage.x = x;
            personnage.y = y;
        } else {
            if (tile.equals(Tile.WALL))
                notifier("C'est une paroi !");
        }
    }

    public final void onUpdate() {
    	if(doitChasser()) {
    		chasser(world.player);
    	} else if (doitAider()) {
    		aider();
    	} else {
    		errer();
    	}
    }

    abstract boolean doitChasser();
    
    abstract boolean doitAider();
    
    public void chasser(Personnage cible) {
        List<Point> points = new Path(personnage, cible.x, cible.y).points();

        if (points != null) {
            int mx = points.get(0).x - personnage.x;
            int my = points.get(0).y - personnage.y;

            personnage.moveBy(mx, my);
        }
    }
    
    public void aider() {
    	switch((int) (Math.random()*2)) {
    	case 0:
    		personnage.jeter(personnage.getSac().getItemAleatoire());
    		break;
    	case 1:
    		personnage.laisserRubisAleatoire();
    		break;
    	}
    }
    
    public void notifier(String message) {}

    public boolean peutVoir(int wx, int wy) {

        if (Math.pow(personnage.x - wx, 2) + Math.pow(personnage.y - wy, 2) > Math.pow(personnage.distanceVue(), 2))
            return false;

        for (Point p : new Ligne(personnage.x, personnage.y, wx, wy)) {
            if (personnage.tile(p.x, p.y).isGround() || p.x == wx && p.y == wy)
                continue;

            return false;
        }

        return true;
    }

    public void errer() {
        int mx = (int) (Math.random() * 3) - 1;
        int my = (int) (Math.random() * 3) - 1;

        Personnage other = personnage.personnage(personnage.x + mx, personnage.y + my);

        if (other == null || !other.getNom().equals(personnage.getNom())) {
            personnage.moveBy(mx, my);
        }
    }

    public Tile souvenirTuile(int wx, int wy) {
        return Tile.UNKNOWN;
    }

    public boolean aProximite(double x, double y) {
    	List<java.awt.Point> points = new Path(personnage, x, y).points();

        if (points != null && points.size() <= 2) {
            return true;
        }
        
        return false;
    }
}