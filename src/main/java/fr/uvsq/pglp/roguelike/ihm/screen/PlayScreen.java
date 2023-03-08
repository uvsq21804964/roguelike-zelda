package fr.uvsq.pglp.roguelike.ihm.screen;

import fr.uvsq.pglp.roguelike.donjon.Donjon;
import fr.uvsq.pglp.roguelike.donjon.elements.AfficherElementEtage;
import fr.uvsq.pglp.roguelike.donjon.elements.Couloir;
import fr.uvsq.pglp.roguelike.donjon.elements.ElementEtage;
import fr.uvsq.pglp.roguelike.donjon.elements.Salle;
import fr.uvsq.pglp.roguelike.ihm.Console;
import fr.uvsq.pglp.roguelike.personnage.Personnage;
import fr.uvsq.pglp.roguelike.personnage.PersonnageDonjon;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe affiche l'univers du jeu avec le PJ,
 * les PNJ, les items, ...
 *
 * <p> Elle peut afficher des sous-écrans afin que l'utilisateur
 * puisse, par exemple, gérer son inventaire.</p>
 *
 * @author Tom Abbouz
 * @version janvier 2023
 */
public class PlayScreen implements Screen {

  private Salle[] salles;
  private ArrayList<Couloir> couloirs = new ArrayList<Couloir>();
  private ElementEtage courant;
  private Personnage joueur;
  private List<String> messages;

  /**
   * Constructeur de {@link PlayScreen}.
   */
  public PlayScreen() {
    Donjon donjon = new Donjon();
    this.salles = donjon.getSalles();
    this.couloirs = donjon.getCouloirs();
    this.joueur = donjon.getJoueur();
    this.courant = joueur.getElementEtage();
    this.messages = joueur.getIa().getMessages();
  }

  @Override
  public void displayOutput(Console console) {
    
    this.courant = joueur.getElementEtage();
    
    courant.updatePersosLents();
    
    new AfficherElementEtage(courant).afficher(console);
    displayMessages(console, messages);
    
    courant.updatePersosRapides();
  }

  private void displayMessages(Console console, List<String> messages) {
    console.sauts(3);
    for (int i = 0; i < messages.size(); i++) {
      console.println(messages.get(i));
    }
    messages.clear();
  }

  @Override
  public boolean commande(String s) {
    return false;
  }

  @Override
  public Screen autreScreen(String s) {
    return this;
  }

  public PersonnageDonjon getJoueur() {
    return (PersonnageDonjon) joueur;
  }
}
