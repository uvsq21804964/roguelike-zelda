package fr.uvsq.pglp.roguelike.personnage;

public enum ArmeC {
	
	BATON("baton", 4, true, 0),
	BATONFERRE("baton ferre", 4, true, 2),
	DAGUE("dague", 4, false, 3),
	EPEE2MAINS("epee a deux mains", 12, true, 10),
	EPEECOURTE("epee courte", 6, false, 5),
	EPEELONGUE("epee longue", 8, false, 6),
	GOURDIN("gourdin", 4, false, 1),
	HACHE("hache", 8, false, 6),
	HACHE2MAINS("hache a deux mains", 12, true, 10),
	KATANA("katana", 10, true, 12),
	MARTEAUDEGUERRE("marteau de guerre", 4, false, 12),
	MASSEDARMES("masse d'armes", 6, false, 4),
	RAPIERE("rapiere", 6, false, 6),
	VIVELAME("vivelame", 10, true, 12);

	ArmeC(String nom, int i, boolean b, int j) {
		
	}
	
	
}
