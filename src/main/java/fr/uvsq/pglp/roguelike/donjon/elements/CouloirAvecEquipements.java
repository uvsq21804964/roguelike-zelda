package fr.uvsq.pglp.roguelike.donjon.elements;

public class CouloirAvecEquipements extends Couloir {

  public CouloirAvecEquipements(Salle salle1, Salle salle2, int nbEquipement, 
      int largeur, int longueur) {
    super(salle1, salle2, nbEquipement, 0, largeur, longueur);
  }
}