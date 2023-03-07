package fr.uvsq.pglp.roguelike.donjon;

import fr.uvsq.pglp.roguelike.donjon.elements.Couloir;
import fr.uvsq.pglp.roguelike.donjon.elements.Salle;
import java.util.ArrayList;

public interface GenerateurDonjon {
  public void genererDonjon();

  public Salle[] getSalles();

  public ArrayList<Couloir> getCouloirs();

  void ajouterJoueur();
}
