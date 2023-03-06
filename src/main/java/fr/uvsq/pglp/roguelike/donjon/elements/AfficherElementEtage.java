package fr.uvsq.pglp.roguelike.donjon.elements;

import java.awt.Color;

import fr.uvsq.pglp.roguelike.ihm.EcranConsole;
import fr.uvsq.pglp.roguelike.personnage.Personnage;
import fr.uvsq.pglp.roguelike.personnage.ia.AmicalIa;
import fr.uvsq.pglp.roguelike.personnage.ia.DefensifIa;
import fr.uvsq.pglp.roguelike.personnage.ia.JoueurIa;
import fr.uvsq.pglp.roguelike.personnage.ia.NeutreIa;
import fr.uvsq.pglp.roguelike.personnage.ia.PersonnageIa;

public class AfficherElementEtage {

    private ElementEtage element;

    public AfficherElementEtage(ElementEtage element) {
        this.element = element;
    }


    public void afficher(EcranConsole console) {

          for(int j = 0 ; j < element.longueur() ; j++) {
            
            for(int k = 0 ; k < element.largeur() ; k++) {
 
                String a = ".";
                Color c = Color.GRAY;
                
                if (element.personnages(k, j) == element.getJoueur()) {
                  a = "@";
                  c = Color.WHITE;
                } else if (element.personnages(k, j) != null) {
                  String nom = element.personnages(k, j).getNom();
                  a = nom.substring(0, 1);
                  c = couleurPersonnage(element.personnages(k, j));
                } else if (element.echangeables(k, j) != null) {
                  a = "" + element.echangeables(k, j).getGlyph();
                  c = Color.GREEN;
                } else if(element.portes(k,j) != null) {
                    a = "0";
                    c = Color.RED;
                } else if (element.tiles(k,j).equals(Tile.WALL)) {
                    a = "#";
                    c = Color.WHITE;
                }
                console.print(a, c);
            }
            console.println("");
        }
    }


    private Color couleurPersonnage(Personnage personnage) {
       PersonnageIa personnageIa = personnage.getIa();
       
       if(personnageIa instanceof JoueurIa) {
         return Color.WHITE;
       } else if(personnageIa instanceof DefensifIa) {
         return Color.ORANGE;
       } else if(personnageIa instanceof AmicalIa) {
         return Color.PINK;
       } else if(personnageIa instanceof NeutreIa) {
         return Color.ORANGE;
       } else {
         return Color.CYAN;
       }
    }

}
