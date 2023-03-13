package fr.uvsq.pglp.roguelike.donjon.elements;

import java.util.ArrayList;
import java.util.List;

import fr.uvsq.pglp.roguelike.echangeable.Echangeable;
import fr.uvsq.pglp.roguelike.personnage.Personnage;
import fr.uvsq.pglp.roguelike.personnage.PersonnageDonjon;

/**
 * Encapsulation des différentes pièces d'un même {@link UniversDeJeu}.
 *
 * <p>Un <b>MorceauEtage</b> contient un certain nombre de {@link Personnage}s,
 * d'{@link Equipement}s au sol et d'{@link Ouvrable}s.</p>
 *
 * <p>De plus, le plan d'une pièce est représenté grâce à un tableau de {@link Tuile}s.</p>
 * 
 * <p>L'évolution des {@link Personnage}s l'ElementEtage est mis à jour grâce aux méthodes
 * @link {@link ElementEtage#updatePersosLents()} et 
 * {@link ElementEtage#updatePersosRapides()}.</p>
 *
 * @see Echangeable
 * @see Ouvrable
 * @see Tuile
 * @author Tom Abbouz
 * @version Mars 2023
 */
public interface ElementEtage {

  int longueur();

  int largeur();

  Tuile tiles(int k, int j);

  Ouvrable ouvrables(int k, int j);

  Echangeable echangeables(int k, int j);

  Personnage personnages(int k, int j);

  void ajouterJoueur(String nom);
  
  Personnage getJoueur();

  void updatePersosLents();

  void updatePersosRapides();

  void addEchangeable(int k, int j, Echangeable p);

  ElementEtage joueurEntre(Ouvrable o);
  
  ElementEtage joueurSort(Ouvrable o);

  void addOuvrable(int k, int j, Ouvrable o);
}