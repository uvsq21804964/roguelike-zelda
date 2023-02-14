package fr.uvsq.pglp.roguelike.donjon;

public class Donjon {

	public Donjon() {
		
		GenerationDonjon donjon = new ToutPredefini();
        donjon.genererDonjon();
	}
}
