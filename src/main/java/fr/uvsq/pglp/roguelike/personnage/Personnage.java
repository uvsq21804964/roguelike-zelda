package fr.uvsq.pglp.roguelike.personnage;

public interface Personnage {

	boolean canEnter(int x, int y);

	void moveBy(int mx, int my);

	void laisserRubisAleatoire();

	Object getSac();

	Object getNom();
	
	int getX();
	
	int getY();

	double distanceVue();

}
