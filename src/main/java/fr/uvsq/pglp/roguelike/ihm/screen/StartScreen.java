package fr.uvsq.pglp.roguelike.ihm.screen;

import fr.uvsq.pglp.roguelike.ihm.Console;

public class StartScreen implements Screen {

    @Override
    public void displayOutput(Console console) {

        console.println("Link... Hyrule et les deesses comptent sur toi");
        console.println("pour reprendre la lumiere qui a ete vole");
        console.println("par Ganondorf et les Betes du Crepuscule...");
        console.println("Tu es l'Elu des deesses, grace a la Triforce du Courage");
        console.println("que tu possedes et ta torche, tu pourras combattre les Ombres");
        console.println("et ainsi liberer les ames nouvellement bannies et vouees");
        console.println("a vivre dans un monde sans lumiere...");

        console.sauts(2);

        console.println("Sauve-les de cette solitude, de ce desespoir");
        console.println("qui leur dechire le coeur comme entre chiens et loups.");

        console.sauts(5);

        console.println("tapez 'entrer' pour lancer une nouvelle partie");
    }

    @Override
    public boolean commande(String s) {
        if(s.toLowerCase().equals("entrer")) {
            return true;
        }
        return false;
    }

    @Override
    public Screen autreScreen(String s) {
        if(s.toLowerCase().equals("entrer")) {
            return new PlayScreen();
        }
        return this;
    }
}