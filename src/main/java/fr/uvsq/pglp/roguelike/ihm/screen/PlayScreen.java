package fr.uvsq.pglp.roguelike.ihm.screen;

import java.util.ArrayList;

import fr.uvsq.pglp.roguelike.donjon.Donjon;
import fr.uvsq.pglp.roguelike.donjon.elements.AfficherElementEtage;
import fr.uvsq.pglp.roguelike.donjon.elements.Couloir;
import fr.uvsq.pglp.roguelike.donjon.elements.ElementEtage;
import fr.uvsq.pglp.roguelike.donjon.elements.Salle;
import fr.uvsq.pglp.roguelike.ihm.Console;

/**
 * Cette classe affiche l'univers du jeu avec le PJ,
 * les PNJ, les items, ...
 * <p>
 * Elle peut afficher des sous-écrans afin que l'utilisateur
 * puisse, par exemple, gérer son inventaire.
 *
 * @author Tom Abbouz
 * @version janvier 2023
 */
public class PlayScreen implements Screen {

    private Salle salles[];
    private ArrayList<Couloir> couloirs =  new ArrayList<Couloir>();
    private ElementEtage courant;

    public PlayScreen() {
        Donjon donjon = new Donjon();
        this.salles = donjon.getSalles();
        this.couloirs = donjon.getCouloirs();
        this.courant = salles[0];
    }

    @Override
    public void displayOutput(Console console) {
        new AfficherElementEtage(courant).afficher(console);
    }

    @Override
    public boolean commande(String s) {
        return false;
    }

    @Override
    public Screen autreScreen(String s) {
        return this;
    }
}
