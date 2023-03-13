package fr.uvsq.pglp.roguelike.ihm.screen;

import fr.uvsq.pglp.roguelike.donjon.Donjon;
import fr.uvsq.pglp.roguelike.donjon.MorceauEtage;
import fr.uvsq.pglp.roguelike.donjon.elements.AfficherElementEtage;
import fr.uvsq.pglp.roguelike.donjon.elements.Couloir;
import fr.uvsq.pglp.roguelike.donjon.elements.ElementEtage;
import fr.uvsq.pglp.roguelike.donjon.elements.Salle;
import fr.uvsq.pglp.roguelike.ihm.Console;
import fr.uvsq.pglp.roguelike.personnage.Personnage;
import fr.uvsq.pglp.roguelike.personnage.PersonnageDonjon;
import fr.uvsq.pglp.roguelike.personnage.attributs.Caracteristique;
import fr.uvsq.pglp.roguelike.personnage.ia.AgressifIa;
import fr.uvsq.pglp.roguelike.personnage.ia.AmicalIa;
import fr.uvsq.pglp.roguelike.personnage.ia.DefensifIa;
import fr.uvsq.pglp.roguelike.personnage.ia.JoueurIa;
import fr.uvsq.pglp.roguelike.personnage.ia.NeutreIa;
import fr.uvsq.pglp.roguelike.personnage.ia.PersonnageIa;

import java.awt.Color;
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
  public PlayScreen(String nom) {
    Donjon donjon = new Donjon(nom);
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
    displayPrenoms(console);
    displayMessages(console, messages);
    
    courant.updatePersosRapides();
  }

  private void displayPrenoms(Console console) {
    List<PersonnageDonjon> personnages = ((MorceauEtage) courant).getPersonnages();
    console.sauts(1);
    for(PersonnageDonjon p : personnages) {
      if(p != joueur) {
        console.println(infos(p), couleurPersonnage(p));
        ecrireMessagesPnj(p, console);
      }
    }
    
    console.sauts(1);
    console.println(infos((PersonnageDonjon) joueur), Color.WHITE);
    displaySacJoueur(console);
  }

  private void displaySacJoueur(Console console) {

    int tailleSac = ((PersonnageDonjon) joueur).tailleSac();
     
    if(tailleSac > 0) {
       

       String equipements = "";
       
       for(int i = 0 ; i < tailleSac ; i++) {
         if( i%3 == 0) {
           console.println("");
           console.print("         ");
         }
         equipements += " - " + ((PersonnageDonjon) joueur).getEquipement(i).getNom();
         while(equipements.length() < 23 && i%3!=2) {
           equipements += " ";
         }
         console.print(equipements);
         equipements = "";
       }
       console.print(equipements);
     }
  }

  private void ecrireMessagesPnj(PersonnageDonjon p, Console console) {
      
      List<String> messagesPnj = p.getIa().getMessages();
      
        for (int i = 0 ; i < messagesPnj.size(); i++) {
          console.print("          - ", couleurPersonnage(p));
          console.println(messagesPnj.get(i));
        }
        messagesPnj.clear();
  }

  private String infos(PersonnageDonjon p) {
    
    String infos = p.getNom();
    
    while(infos.length() != 10) {
      infos += " ";
    }
    
    String typeIa = p.getIa().getType();
    
    while(typeIa.length() != 8) {
      typeIa += " ";
    }
    
    infos += "- " + typeIa + " -";
    
    Caracteristique[] car = Caracteristique.values();
    
    for(Caracteristique c : car) {
      infos += " " + c + ":" + p.getVal(c) + "/" + p.getMod(c);
    }
    return infos;
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
  
  private Color couleurPersonnage(Personnage personnage) {
    PersonnageIa personnageIa = personnage.getIa();

    if (personnageIa instanceof JoueurIa) {
      return Color.WHITE;
    } else if (personnageIa instanceof DefensifIa) {
      return Color.ORANGE;
    } else if (personnageIa instanceof AmicalIa) {
      return Color.PINK;
    } else if (personnageIa instanceof NeutreIa) {
      return Color.ORANGE;
    } else if (personnageIa instanceof AgressifIa) {
      return new Color(180, 0, 180);
    } else {
      return Color.CYAN;
    }
  }
}
