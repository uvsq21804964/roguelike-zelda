package fr.uvsq.pglp.roguelike.donjon;

public abstract class Salle {

	protected char[][] plan;
	
	public Salle() {
		this.plan = new char[8][8];
		formerMurs();
		ajouterItems();
		ajouterPersonnages();
	}
	
	private void formerMurs() {
		for(int i = 0 ; i < plan.length ; i++) {
			for(int j = 0 ; j < plan[0].length ; i++) {
				plan[i][j] = ' ';
				if(i == 0 || i == plan.length - 1 || j == 0 || j == plan[0].length - 1) {
					plan[i][j] = '#';
				}
			}
		}
	}
	
	public abstract void ajouterItems();
	
	public abstract void ajouterPersonnages();
}
