package fr.uvsq.pglp.roguelike.ihm.screen;

import fr.uvsq.pglp.roguelike.ihm.Console;

/**
 * Ecran de présentation du jeu.
 *
 * <p>Il faut utiliser la commande "entrer" pour lancer une partie (càd
 * voire l'écran {@link PlayScreen}.</p>
 *
 * @author Tom Abbouz
 * @version Mars 2023
 */
public class StartScreen implements Screen {

  @Override
  public void displayOutput(Console console) {

    console.println("Link... Hyrule et les déesses comptent sur toi");
    console.println("pour reprendre la lumière qui a été volé");
    console.println("par Ganondorf et les Bêtes du Crépuscule...");
    console.println("Tu es l'Elu des déesses, grâce à la Triforce du Courage");
    console.println("que tu possèdes et ta torche, tu pourras combattre les Ombres");
    console.println("et ainsi libérer les âmes nouvellement bannies et vouées");
    console.println("à vivre dans un monde sans lumière...");

    console.sauts(2);

    console.println("Sauve-les de cette solitude, de ce désespoir");
    console.println("qui leur déchire le coeur comme entre chiens et loups.");

    console.sauts(5);

    console.println("Tapez 'entrer [votre paseudonyme]' pour lancer une nouvelle partie.");
  }

  @Override
  public boolean commande(String s) {
    if(s.length() < 8) {
      return false;
    }
    
    String entrer = s.substring(0, 8);
    if (entrer.toLowerCase().contains("entrer ")) {
      return true;
    }
    return false;
  }

  @Override
  public Screen autreScreen(String s) {
    String nom = s.substring(7, s.length());
    return new PlayScreen(nom);
  }
}