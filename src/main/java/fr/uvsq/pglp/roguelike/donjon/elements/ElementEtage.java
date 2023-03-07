package fr.uvsq.pglp.roguelike.donjon.elements;

import fr.uvsq.pglp.roguelike.echangeable.Echangeable;
import fr.uvsq.pglp.roguelike.personnage.Personnage;

public interface ElementEtage {

  int longueur();
  
  int largeur();

  Tile tiles(int k, int j);

  Ouvrable ouvrables(int k, int j);
  
  Echangeable echangeables(int k, int j);

  Personnage personnages(int k, int j);
  
  void ajouterJoueur();
  
  void joueurSort();
  
  void joueurEntre(Personnage joueur);

  Personnage getJoueur();
}
