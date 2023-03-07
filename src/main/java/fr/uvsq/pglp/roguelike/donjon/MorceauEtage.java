package fr.uvsq.pglp.roguelike.donjon;

import fr.uvsq.pglp.roguelike.donjon.elements.ElementEtage;
import fr.uvsq.pglp.roguelike.donjon.elements.Ouvrable;
import fr.uvsq.pglp.roguelike.donjon.elements.Porte;
import fr.uvsq.pglp.roguelike.donjon.elements.Tile;
import fr.uvsq.pglp.roguelike.echangeable.ArmeContact;
import fr.uvsq.pglp.roguelike.echangeable.ArmeRechargementLimite;
import fr.uvsq.pglp.roguelike.echangeable.ArmeRechargementSimple;
import fr.uvsq.pglp.roguelike.echangeable.ArmeSansRechargement;
import fr.uvsq.pglp.roguelike.echangeable.ArmureDeCorps;
import fr.uvsq.pglp.roguelike.echangeable.Bouclier;
import fr.uvsq.pglp.roguelike.echangeable.Echangeable;
import fr.uvsq.pglp.roguelike.personnage.Personnage;
import fr.uvsq.pglp.roguelike.personnage.PersonnageDonjon;
import fr.uvsq.pglp.roguelike.personnage.TypeIa;
import java.util.ArrayList;
import java.util.List;

public abstract class MorceauEtage implements ElementEtage {

  private static ArrayList<String> prenoms = new ArrayList<String>();
  protected int largeur;
  protected int longueur;
  protected int nbEquipement;
  protected int nbEnnemis;
  protected Tile[][] tiles;
  protected List<Personnage> personnages = new ArrayList<Personnage>();
  protected Echangeable[][] echangeables;
  protected Ouvrable[][] ouvrables;
  protected Personnage joueur = null;

  protected MorceauEtage(int largeur, int longueur, int nbEquipement, int nbEnnemis) {
    this.largeur = largeur;
    this.longueur = longueur;
    this.tiles = new Tile[largeur][longueur];
    this.ouvrables = new Ouvrable[largeur][longueur];
    this.nbEquipement = nbEquipement;
    this.nbEnnemis = nbEnnemis;
    this.echangeables = new Echangeable[largeur][longueur];

    for (int i = 0; i < largeur; i++) {
      for (int j = 0; j < longueur; j++) {
        tiles[i][j] = Tile.FLOOR;
        echangeables[i][j] = null;
        ouvrables[i][j] = null;
      }
    }

    for (int i = 0; i < largeur; i++) {
      tiles[i][0] = Tile.WALL;
      tiles[i][longueur - 1] = Tile.WALL;
    }

    for (int i = 0; i < longueur; i++) {
      tiles[0][i] = Tile.WALL;
      tiles[largeur - 1][i] = Tile.WALL;
    }

    ajouterEquipements();
    ajouterPrenoms();
    ajouterPnjs();
  }

  private void ajouterPrenoms() {
    prenoms.add("Pierre");
    prenoms.add("Samuel");
    prenoms.add("William");
    prenoms.add("Gabriel");
    prenoms.add("Alexandre");
    prenoms.add("Olivier");
    prenoms.add("Jeremy");
    prenoms.add("Nicolas");
    prenoms.add("Vincent");
    prenoms.add("Anthony");
    prenoms.add("Maxime");
    prenoms.add("Thomas");
    prenoms.add("Felix");
    prenoms.add("Mathieu");
    prenoms.add("Jacques");
    prenoms.add("Jean");
    prenoms.add("Bernard");
    prenoms.add("Eve");
    prenoms.add("Charles");
    prenoms.add("Antoine");
    prenoms.add("Arthur");
    prenoms.add("Tom");
    prenoms.add("Yves");
    prenoms.add("Nicolas");
    prenoms.add("Jeanne");
    prenoms.add("Hélène");
    prenoms.add("Stéphanie");
    prenoms.add("Muriel");
    prenoms.add("Manon");
    prenoms.add("Elisabeth");
    prenoms.add("Camille");
    prenoms.add("Gabrielle");
    prenoms.add("Sarah");
    prenoms.add("Maude");
    prenoms.add("Laurie");
    prenoms.add("Noemie");
    prenoms.add("Audrey");
    prenoms.add("Catherine");
    prenoms.add("Ariane");
    prenoms.add("Alexandra");
    prenoms.add("Jade");
    prenoms.add("Jessica");
  }

  private String randomPrenom() {
    return prenoms.remove((int) (Math.random() * prenoms.size()));
  }

  public abstract void ajouterPorte(Porte porte);

  private void ajouterEquipements() {
    while (nbEquipement > 0) {
      int x = (int) (Math.random() * (tiles.length - 2) + 1);
      int y = (int) (Math.random() * (tiles[0].length - 2) + 1);
      if (tiles[x][y].equals(Tile.FLOOR)) {
        echangeables[x][y] = equipements();
        nbEquipement--;
      }
    }
  }

  private Echangeable equipements() {

    if ((int) (Math.random() * 2) == 0) {
      if ((int) (Math.random() * 2) == 0) {
        return Bouclier.random();
      } else {
        return ArmureDeCorps.random();
      }
    } else {
      if ((int) (Math.random() * 2) == 0) {
        return ArmeContact.random();
      } else {
        switch ((int) (Math.random() * 3)) {
          case 0:
            return ArmeRechargementLimite.random();
          case 1:
            return ArmeRechargementSimple.random();
          default:
            return ArmeSansRechargement.random();
        }
      }
    }
  }

  @Override
  public int longueur() {
    return longueur;
  }

  @Override
  public int largeur() {
    return largeur;
  }

  @Override
  public Tile tiles(int k, int j) {
    return tiles[k][j];
  }

  @Override
  public Ouvrable ouvrables(int k, int j) {
    return ouvrables[k][j];
  }

  @Override
  public Echangeable echangeables(int k, int j) {
    return echangeables[k][j];
  }

  @Override
  public Personnage personnages(int x, int y) {
    for (Personnage p : personnages) {
      if (p.getX() == x && p.getY() == y) {
        return p;
      }
    }
    return null;
  }

  protected void ajouterPnjs() {
    while (nbEnnemis > 0) {
      int x = (int) (Math.random() * (tiles.length - 2) + 1);
      int y = (int) (Math.random() * (tiles[0].length - 2) + 1);
      if (tiles[x][y].equals(Tile.FLOOR)) {
        personnages.add(ennemis(x, y));
        nbEnnemis--;
      }
    }
  }

  @Override
  public void ajouterJoueur() {
    while (true) {
      int x = (int) (Math.random() * (tiles.length - 2) + 1);
      int y = (int) (Math.random() * (tiles[0].length - 2) + 1);
      if (tiles[x][y].equals(Tile.FLOOR) && personnages(x, y) == null) {
        Personnage joueur = new PersonnageDonjon.PersonnageBuilder("Vous", x, y)
                .setIa(TypeIa.JOUEUR)
                .build();
        personnages.add(joueur);
        this.joueur = joueur;
        joueur.morceauEtage(this);
        break;
      }
    }
  }

  @Override
  public void joueurSort() {
    personnages.remove(joueur);
    joueur = null;
  }

  @Override
  public void joueurEntre(Personnage joueur) {
    this.joueur = joueur;
    personnages.add(joueur);
  }

  @Override
  public Personnage getJoueur() {
    return joueur;
  }

  private Personnage ennemis(int x, int y) {
    String prenom = randomPrenom();
    TypeIa typeIa = TypeIa.AGRESSIF;

    if ((int) (Math.random() * 2) == 0) {
      typeIa = TypeIa.random();
    }
    return new PersonnageDonjon.PersonnageBuilder(prenom, x, y)
            .setIa(typeIa)
            .build();
  }

  public void setTiles(int i, int j, Tile tile) {
    tiles[i][j] = tile;
  }
}