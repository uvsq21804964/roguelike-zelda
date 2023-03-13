package fr.uvsq.pglp.roguelike.personnage.ia;

import fr.uvsq.pglp.roguelike.echangeable.Echangeable;
import fr.uvsq.pglp.roguelike.echangeable.Equipement;
import fr.uvsq.pglp.roguelike.personnage.Personnage;
import fr.uvsq.pglp.roguelike.personnage.PersonnageDonjon;

/**
 * Intelligence artificielle pour les PNJs amicaux.
 *
 * <p>Dès qu'un PNJ amical voit le personnage joueur, il l'aide au plus 5 fois.
 * Une aide est soit un don de pièces d'argent ou un don d'équipement.</p>
 *
 * @author Tom Abbouz
 * @version Février 2023
 * @see PersonnageIa
 */
public class AmicalIa extends PersonnageIa {

  private Equipement vendre = null;
  private boolean soutirer = false;
  private Difficulte difficulte;
  private boolean enVente = false;
  
  public AmicalIa(Personnage personnage) {
    super(personnage, "AMICAL", false);
    this.difficulte = Difficulte.random();
  }

  @Override
  boolean doitChasser() {
    return false;
  }

  @Override
  boolean doitAider() {
    if(vendre == null) {
      int tailleSac = ((PersonnageDonjon) personnage).tailleSac();
      if(tailleSac > 0) {
        int choix = (int) (Math.random()*tailleSac);
        vendre = ((PersonnageDonjon) personnage).getEquipement(choix);
      }
    }
    return true;
  }
  
  @Override 
  public void aider() {
    if(!enVente) {
      out:
        for(int i = personnage.getX() - 1 ; i <= personnage.getX() + 1 ; i++) {
          for(int j = personnage.getY() - 1 ; j <= personnage.getY() + 1 ; j++) {
            if(personnage.personnage(i, j) != null 
                && (i != personnage.getX() || j != personnage.getY())) {
              String nom = personnage.personnage(i, j).getNom();
              if(vendre != null) {
                if(personnage.personnage(i, j).getIa() instanceof JoueurIa && soutirer) {
                  notifier("Viens m'acheter un équipement "+ nom + ".");
                  notifier("Je te promets de te donner le vrai prix du marché...");
                } else {
                  notifier("Oh, bonjour " + nom + "!");
                  notifier("On devrait parler un peu, j'ai un équipement à te vendre.");
                }
              } else {
                notifier("En ce moment les affaires marchent à merveille!");
              }
              break out;
            }
          }
        }
    }
    enVente = false;
  }

  public void reponse(String nom) {
    enVente = true;
    if(vendre != null) {
      String prixAmi = "C'est le marché qui fixe le prix, pas moi!";
      if(soutirer) {
        prixAmi = "On ne peut pas te berner toi...";
      }
      notifier(nom + ", je te propose " + vendre.getNom() 
      + " pour la somme de " + prix() + " pièces d'argent.");
      notifier(prixAmi);
    } else {
      notifier("Malheureusement, je n'ai plus rien à vendre " + nom + ". Repasse demain!");
    }
  }

  public int prix() {
    if(vendre != null) {
      if(soutirer) {
        return vendre.getPrix();
      }
      return ((int) (vendre.getPrix() * 1.5));
    }
    return 0;
  }

  public String soutirer(int mod) {
    enVente = true;
    if(soutirer) {
      String s = personnage.getNom() + " vous prends déjà au sérieux.";
      return s;
    } else {
      String nom = personnage.getElementEtage().getJoueur().getNom();
      if(difficulte.getValeur() <= (20 + mod)) {
        if(difficulte.tirage(mod)) {
          soutirer = true;
          notifier("En effet, ce ne sont pas les vrais prix du marché "+ nom + "...");
          notifier("Ne le dis à personne, je t'en supplie ! J'ai une femme "
              + "et quatre enfants à nourrir.");
          return ("Bravo, vous avez réussi à soutirer les vrais prix"
              + " du marché à " + personnage.getNom() + "!");
        } else {
          notifier("Je suis quelqu'un d'honnête, "+ nom + ". Aucune arnaque entre nous!");
          return ("Il est " + difficulte.getString() + " de soutirer des informations à " 
          + personnage.getNom() + "!");
        }
      } else {
        notifier("Houste, du balai ! Ne t'immisce pas "
            + "dans mes affaires vaurien !!");
        return ("Inutile d'essayer, votre charisme est trop faible pour soutirer "
            + "les vrais prix du marché à " + personnage.getNom() + "!");
      }
    }
  }

  public Echangeable enVente() {
    return vendre;
  }

  public void modifierProduitVendu() {
    if(vendre != null) {
      ((PersonnageDonjon) personnage).retirer(vendre);
      vendre = null;
    }
    if(vendre == null) {
      int tailleSac = ((PersonnageDonjon) personnage).tailleSac();
      if(tailleSac > 0) {
        int choix = (int) (Math.random()*tailleSac);
        vendre = ((PersonnageDonjon) personnage).getEquipement(choix);
      } else {
        notifier(personnage.nomJoueur() + ", je n'ai plus rien en stock, repasse demain!");
        personnage.getElementEtage().getJoueur().notifier(personnage.getNom()
            + " n'a plus aucun équipement à vendre.");
      }
    }
  }
}
