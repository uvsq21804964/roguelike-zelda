package fr.uvsq.pglp.roguelike.ihm.screen;

import fr.uvsq.pglp.roguelike.ihm.Console;

public class LoseScreen implements Screen {

  @Override
  public void displayOutput(Console console) {

    
    
    console.println("Vous etes mort avant de ramener la lumiere sur Hyrule...");

    console.sauts(2);
    
    console.println("Ganondorf et les Betes du Crepuscule n'ont plus aucun ennemi");
    console.println("pouvant rivaliser avec eux. Les Ombres se sont donc imposees");
    console.println("sur Hyrule, et ses habitants ont ete damnes a y vivre comme des fantomes");
    console.println("dans une solitude et un desespoir sans fond.");

    console.sauts(1);
    
    console.println("La Sagesse ne suffisant pas sans le Courage, les deesses ont du abandonner");
    console.println("ces terres qu'elles cherissaient tant.");

    console.sauts(1);

    console.println("Les coeurs des chiens comme des loups ont ete fendus et");
    console.println("le sang ne cessera jamais de couler, comme les larmes d'un enfant abandonne...");
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