package fr.uvsq.pglp.roguelike.personnage.ia;

import java.util.List;

import fr.uvsq.pglp.roguelike.donjon.MorceauEtage;
import fr.uvsq.pglp.roguelike.donjon.elements.Ouvrable;
import fr.uvsq.pglp.roguelike.donjon.elements.Porte;
import fr.uvsq.pglp.roguelike.personnage.Personnage;
import fr.uvsq.pglp.roguelike.personnage.PersonnageDonjon;
import fr.uvsq.pglp.roguelike.personnage.attributs.Caracteristique;

/**
 * Intelligence artificielle pour les PNJs neutres.
 *
 * <p>Dès que le personnage joueur attaque un PNJ neutre, celui-ci l'attaque et/ou
 * s'approche du personnage joueur.</p>
 *
 * @author Tom Abbouz
 * @version Février 2023
 * @see PersonnageIa
 */
public class NeutreIa extends PersonnageIa {

  private boolean convaincu = false;
  private Difficulte difficulte;
  
  public NeutreIa(Personnage personnage) {
    super(personnage, "NEUTRE", false);
    this.difficulte = Difficulte.random();
  }

  @Override
  boolean doitChasser() {
    
    if(attaqueJoueur) {
      return true;
    } else if(convaincu && proiePnj() != null) {
      return true;
    }
    
    return false;
  }

  @Override
  protected Personnage personnageChasse() {
    
    if(attaqueJoueur) {
      return personnage.getElementEtage().getJoueur();
    } else { // Càd si (convaincu == true)
      return proiePnj();
    }
  }
  
  private Personnage proiePnj() {

    MorceauEtage element = (MorceauEtage) personnage.getElementEtage();
    
    List<PersonnageDonjon> personnages = element.getPersonnages();
    
    for(PersonnageDonjon cible : personnages) {
      if(cible.getIa().attaqueJoueur()) {
        return cible;
      }
    }
    
    return null;
  }

  @Override
  boolean doitAider() {
    return false;
  }
  
  public void doitAttaquerJoueur() {
    if(!attaqueJoueur) {
      PersonnageDonjon joueur = ((PersonnageDonjon) personnage).getJoueur();
      joueur.notifier(personnage.getNom() + " vous en veut de l'avoir attaqué !");
      notifier("Tu n'aurais jamais dû m'attaquer " + joueur.getNom() + ".");
      notifier("L'heure de la revanche à sonner !");
    }
    attaqueJoueur = true;
  }

  public String convaincre(int mod, String nom) {
    
    if(convaincu) {
      String s = personnage.getNom() + " s'est déjà rallié à votre cause!";
      return s;
    } else {
      if(difficulte.getValeur() <= (20 + mod)) {
        if(difficulte.tirage(mod)) {
          convaincu = true;
          notifier("Tu m'as convaincu "+ nom + ", mort aux ennemis!");
          return ("Bravo, vous avez réussi à convaincre " + personnage.getNom() + " de devenir votre allié!");
        } else {
          notifier("Pourquoi devrais-je t'obéir ?! "+ nom + ", ta cause ne m'intéresse pas!");
          return (personnage.getNom() + " est " + difficulte.getString() + " à convaincre !");
        }
      } else {
        notifier("Va t'en " + nom + ", tu n'es qu'un idiot insolent.");
        return ("Inutile d'essayer, votre intelligence ne suffit pas pour convaincre "
        + personnage.getNom() + " !");
      }
    }
  }
}
