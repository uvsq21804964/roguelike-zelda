package fr.uvsq.pglp.roguelike.donjon.elements;

public class Porte {
    
    private Tile type;
    private Salle salle;
    private Couloir couloir;
    private boolean ouverte = false;

    public Porte(Tile type, Salle salle, Couloir couloir) {
        this.type = type;
        this.salle = salle;
        this.couloir = couloir;
    }

    public Tile getType() {
        return type;
    }

    public Salle getSalle() {
        return salle;
    }

    public Couloir getCouloir() {
        return couloir;
    }

    public boolean isOuverte() {
        return ouverte;
    }

    public void ouvrir() {
        this.ouverte = true;
    }
}
