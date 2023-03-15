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
    displaySacJoueur(console);
    displayScoreJoueur(console);
    displayMessages(console, messages);
    
    courant.updatePersosRapides();
  }

  private void displayScoreJoueur(Console console) {
    console.sauts(1);
    
    int pvActuels = ((PersonnageDonjon) joueur).getPointsDeVie().valeur();
    int pvMax = ((PersonnageDonjon) joueur).getPointsDeVie().valeurMax();
    String pointsDeVie = pvActuels + " / " + pvMax;
    Color couleurPv = Color.WHITE;
    if(((PersonnageDonjon) joueur).getPointsDeVie().taux() >= 0.75 ) {
      couleurPv = Color.GREEN;
    } else if(((PersonnageDonjon) joueur).getPointsDeVie().taux() <= 0.25 ) {
      couleurPv = Color.RED;
    }
    console.print(pointsDeVie + " pv", couleurPv);
    
    // TODO Score ATTAQUE et SCORE DEFENSE;
  }

  private void displayPrenoms(Console console) {
    List<PersonnageDonjon> personnages = ((MorceauEtage) courant).getPersonnages();
    console.sauts(1);
    for(PersonnageDonjon p : personnages) {
      System.out.println(p.getNom() + " a une defense de "  + p.getDefense());
      if(p != joueur) {
        console.println(infos(p), couleurPersonnage(p));
        displayEquiperPnj(console, p);
        ecrireMessagesPnj(p, console);
      }
    }
    
    console.sauts(1);
    console.println(infos((PersonnageDonjon) joueur), Color.WHITE);
  }
  
  private void displayEquiperPnj(Console console, PersonnageDonjon p) {
    int tailleSac = p.tailleSac();
    
    if(tailleSac > 0) {
       
       String equipements = "";
       Color c = couleurPersonnage(p);
       
       console.print("         ");
       
       for(int i = 0 ; i < tailleSac ; i++) {
         equipements += " - " + p.getEquipement(i).getNom();
         while(equipements.length() < 24) {
           equipements += " ";
         }
         if(p.porte(p.getEquipement(i))) {
           console.print(equipements, c);
         }
         equipements = "";
       }
       console.print(equipements, c);
     }
    console.println("");
  }

  private void displaySacJoueur(Console console) {

    int tailleSac = ((PersonnageDonjon) joueur).tailleSac();
     
    if(tailleSac > 0) {
       

       String equipements = "";
       Color c = Color.WHITE;
       
       for(int i = 0 ; i < tailleSac ; i++) {
         c = Color.WHITE;
         if( i%3 == 0) {
           console.println("");
           console.print("         ");
         }
         equipements += " - " + ((PersonnageDonjon) joueur).getEquipement(i).getNom();
         while(equipements.length() < 24 && i%3!=2) {
           equipements += " ";
         }
         if(((PersonnageDonjon) joueur).porte(((PersonnageDonjon) joueur).getEquipement(i))) {
           c = Color.CYAN;
         }
         console.print(equipements, c);
         equipements = "";
       }
       console.print(equipements, c);
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
    boolean mort = true;
    
    List<PersonnageDonjon> personnages = ((MorceauEtage) courant).getPersonnages();
    for(PersonnageDonjon p : personnages) {
      if(p == joueur) {
        mort = false;
      }
    }
    
    if(mort) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public Screen autreScreen(String s) {
    
    boolean mort = true;
    
    List<PersonnageDonjon> personnages = ((MorceauEtage) courant).getPersonnages();
    for(PersonnageDonjon p : personnages) {
      if(p == joueur) {
        mort = false;
      }
    }
    
    if(mort) {
      return new LoseScreen();
    } else {
      return this;
    }
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
