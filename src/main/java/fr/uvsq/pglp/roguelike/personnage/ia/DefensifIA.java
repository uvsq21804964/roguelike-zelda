package fr.uvsq.pglp.roguelike.personnage.ia;

import java.awt.Point;
import java.util.List;

public class DefensifIA extends PersonnageIA {
	
	private int xDefendre;
	private int yDefendre;
	
	@Override
	boolean doitChasser() {
		List<java.awt.Point> points = new Path(player, xDefendre, yDefendre).points();

        if (points != null && points.size() <= 6) {
            return true;
        }
        return false;
	}
	
	@Override
	boolean doitAider() {
		return false;
	}
	
	
	public DefensifIA(Personnage personnage) {
		super(personnage);
	}
	
	@Override
	public void errer() {
		int mx = (int) (Math.random() * 3) - 1;
        int my = (int) (Math.random() * 3) - 1;

        Personnage other = personnage.personnage(personnage.x + mx, personnage.y + my);

        List<java.awt.Point> points = new Path(personnage, xDefendre, yDefendre).points();

        if (points != null && points.size() <= 6) {
	        if (other == null || !other.getNom().equals(personnage.getNom())) {
	            personnage.moveBy(mx, my);
	        }
        }
	}

}
