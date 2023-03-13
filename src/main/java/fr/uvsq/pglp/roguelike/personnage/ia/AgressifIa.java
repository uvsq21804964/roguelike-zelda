package fr.uvsq.pglp.roguelike.personnage.ia;

import fr.uvsq.pglp.roguelike.personnage.Personnage;

/**
 * Intelligence artificielle pour les PNJs agressifs.
 *
 * <p>Dès qu'un PNJ agressif voit le personnage joueur, il l'attaque et/ou
 * s'approche de lui.</p>
 *
 * @author Tom Abbouz
 * @version Février 2023
 * @see PersonnageIa
 */
public class AgressifIa extends PersonnageIa {
  
  private Difficulte difficulteSaut;

  public AgressifIa(Personnage personnage) {
    super(personnage, "AGRESSIF", true);
    this.difficulteSaut = Difficulte.random();
  }

  @Override
  boolean doitChasser() {
    return true;
  }

  @Override
  boolean doitAider() {
    return false;
  }

  public int getDiffValeur() {
    return difficulteSaut.getValeur();
  }

  public boolean tirage(int mod, Personnage joueur) {
    if(difficulteSaut.getValeur() <= (20 + mod)) {
      if(difficulteSaut.tirage(mod)) {
        return true;
      } else {
        notifier("Vraiment ?! Tu as crû qu'on jouait à saute-mouton "+ joueur.getNom() + " ?!!");
        joueur.notifier("Il est "+  difficulteSaut.getString() 
        + " de sauter par-dessus " + personnage.getNom() + " !");
        return false;
      }
    } else {
      notifier("Arrête de faire le singe " + joueur.getNom() + ", parce que moi gorille!");
      joueur.notifier("Inutile d'essayer, vous manquer de dextérité pour sauter par-dessus "
          + personnage.getNom() + " !");
      return false;
    }
  }
}
