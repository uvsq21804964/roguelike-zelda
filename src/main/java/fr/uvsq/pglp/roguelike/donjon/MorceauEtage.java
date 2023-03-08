package fr.uvsq.pglp.roguelike.donjon;

import fr.uvsq.pglp.roguelike.donjon.elements.Couloir;
import fr.uvsq.pglp.roguelike.donjon.elements.ElementEtage;
import fr.uvsq.pglp.roguelike.donjon.elements.Ouvrable;
import fr.uvsq.pglp.roguelike.donjon.elements.Porte;
import fr.uvsq.pglp.roguelike.donjon.elements.Salle;
import fr.uvsq.pglp.roguelike.donjon.elements.Tile;
import fr.uvsq.pglp.roguelike.donjon.elements.Tresor;
import fr.uvsq.pglp.roguelike.donjon.elements.Tuile;
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
import fr.uvsq.pglp.roguelike.personnage.attributs.Caracteristique;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulation des différentes pièces d'un donjon.
 *
 * <p>Dans notre cas, un <b>MorceauEtage</b> est soit une {@link Salle},
 * soit un {@link Couloir}.</p>
 *
 * <p>Ces pièces sont de formes rectangulaires et peuvent possèder :
 * des {@link Ouvrable}s (tels que des {@link Porte}s et des {@link Tresor}s),
 * des {@link Echangeable}s (tels que des {@link Equipement}s et des {@link Piece}s),
 * et un joueur.</p>
 *
 * <p>De plus, le plan d'une pièce est représenté grâce à un tableau de {@link Tuile}s.</p>
 *
 * @see Couloir
 * @see ElementEtage
 * @see Salle
 *
 * @author Tom Abbouz
 * @version Mars 2023
 */
public abstract class MorceauEtage implements ElementEtage {

  private static ArrayList<String> prenoms = new ArrayList<String>();
  protected int largeur;
  protected int longueur;
  protected int nbEquipement;
  protected int nbEnnemis;
  protected Tile[][] tiles;
  protected List<PersonnageDonjon> personnages = new ArrayList<PersonnageDonjon>();
  protected Echangeable[][] echangeables;
  protected Ouvrable[][] ouvrables;
  protected PersonnageDonjon joueur = null;
  protected List<PersonnageDonjon> persosLents = new ArrayList<PersonnageDonjon>();
  protected List<PersonnageDonjon> persosRapides = new ArrayList<PersonnageDonjon>();

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
        return Bouclier.random(6);
      } else {
        return ArmureDeCorps.random(6);
      }
    } else {
      if ((int) (Math.random() * 2) == 0) {
        return ArmeContact.random(6);
      } else {
        switch ((int) (Math.random() * 3)) {
          case 0:
            return ArmeRechargementLimite.random(6);
          case 1:
            return ArmeRechargementSimple.random(6);
          default:
            return ArmeSansRechargement.random(6);
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
        PersonnageDonjon pnj = null;
        do {
          pnj = ennemis(x, y);
        } while (initialePrenomDejaPrise(pnj));
        pnj.morceauEtage(this);
        personnages.add(pnj);
        nbEnnemis--;
      }
    }
  }

  private boolean initialePrenomDejaPrise(PersonnageDonjon pnj) {
    for(PersonnageDonjon p : personnages) {
      if(p.getNom().equals(pnj.getNom())) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void ajouterJoueur() {
    while (true) {
      int x = (int) (Math.random() * (tiles.length - 2) + 1);
      int y = (int) (Math.random() * (tiles[0].length - 2) + 1);
      if (tiles[x][y].equals(Tile.FLOOR) && personnages(x, y) == null) {
        PersonnageDonjon joueur = new PersonnageDonjon.PersonnageBuilder("Vous", x, y)
                .setIa(TypeIa.JOUEUR)
                .build();
        personnages.add(joueur);
        this.joueur = joueur;
        joueur.morceauEtage(this);
        break;
      }
    }
    trierPersosRapidesEtLents();
  }
  
  @Override
  public MorceauEtage joueurEntre(Ouvrable o) {
    
    for(int i = 0 ; i < largeur ; i++) {
      for(int j = 0 ; j < longueur ; j++) {
        if(ouvrables[i][j] == o && o instanceof Porte) {
          if(this instanceof Salle) {
            MorceauEtage m = ((Porte) o).getCouloir();
            m.ajouterJoueur(joueur, (Porte) o);
            return m;
          } else if(this instanceof Couloir) {
            MorceauEtage m = ((Porte) o).getSalle();
            m.ajouterJoueur(joueur, (Porte) o);
            return m;
          }
        }
      }
    }
    return this;
  }
  
  @Override
  public MorceauEtage joueurSort(Ouvrable o) {
    personnages.remove(joueur);
    joueur = null;
    return this;
  }
  
  public void ajouterJoueur(PersonnageDonjon joueur, Porte p) {

    personnages.add(joueur);
    this.joueur = joueur;
    joueur.morceauEtage(this);
    
    for(int i = 0 ; i < largeur ; i++) {
      for(int j = 0 ; j < longueur ; j++) {
        if(ouvrables[i][j] == p) {
          joueur.setX(i);
          joueur.setY(j);
        }
      }
    }
        
    trierPersosRapidesEtLents();
  }

  private void trierPersosRapidesEtLents() {
    persosRapides.clear();
    persosLents.clear();
    
    for(PersonnageDonjon p : personnages) {
      if(p instanceof PersonnageDonjon) {
        if(p.getInit() > joueur.getInit()) {
          persosRapides.add(p);
        } else if (p.getInit() == joueur.getInit()) {
          if(p.getMod(Caracteristique.SAG) > joueur.getMod(Caracteristique.SAG)) {
            persosRapides.add(p);
          } else {
            persosLents.add(p);
          }
        } else {
          persosLents.add(p);
        }
      }
    }
  }

  @Override
  public Personnage getJoueur() {
    return joueur;
  }

  private PersonnageDonjon ennemis(int x, int y) {
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
  
  @Override 
  public void updatePersosLents() {
    List<PersonnageDonjon> toUpdate = new ArrayList<PersonnageDonjon>(persosLents);
    for (PersonnageDonjon p : toUpdate){
      p.update();
    }
  }

  @Override
  public void updatePersosRapides() {
    List<PersonnageDonjon> toUpdate = new ArrayList<PersonnageDonjon>(persosRapides);
    for (PersonnageDonjon p : toUpdate){
      p.update();
    }
  }

  @Override
  public void addEchangeable(int k, int j, Echangeable p) {
    echangeables[k][j] = p;
  }
}