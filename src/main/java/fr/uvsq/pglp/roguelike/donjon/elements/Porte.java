package fr.uvsq.pglp.roguelike.donjon.elements;

public class Porte implements Ouvrable {
    
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
        remplacerTuile();
        this.ouverte = true;
    }

    private void remplacerTuile() {
      for(int i = 0 ; i < salle.largeur() ; i++) {
        for(int j = 0 ; j < salle.longueur() ; j++) {
          if(salle.ouvrables(i, j).equals(this)) {
            salle.setTiles(i, j, Tile.FLOOR);
          }
        }
      }
      
      for(int i = 0 ; i < couloir.largeur() ; i++) {
        for(int j = 0 ; j < couloir.longueur() ; j++) {
          if(couloir.ouvrables(i, j).equals(this)) {
            couloir.setTiles(i, j, Tile.FLOOR);
          }
        }
      }
    }
}
