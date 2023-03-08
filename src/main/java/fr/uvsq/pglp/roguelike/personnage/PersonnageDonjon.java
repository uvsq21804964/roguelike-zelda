package fr.uvsq.pglp.roguelike.personnage;

import fr.uvsq.pglp.roguelike.donjon.MorceauEtage;
import fr.uvsq.pglp.roguelike.donjon.elements.ElementEtage;
import fr.uvsq.pglp.roguelike.donjon.elements.Ouvrable;
import fr.uvsq.pglp.roguelike.donjon.elements.Porte;
import fr.uvsq.pglp.roguelike.donjon.elements.SalleFixe;
import fr.uvsq.pglp.roguelike.donjon.elements.Tile;
import fr.uvsq.pglp.roguelike.donjon.elements.Tresor;
import fr.uvsq.pglp.roguelike.echangeable.ArmureDeCorps;
import fr.uvsq.pglp.roguelike.echangeable.Echangeable;
import fr.uvsq.pglp.roguelike.echangeable.Equipement;
import fr.uvsq.pglp.roguelike.personnage.attributs.Caracteristique;
import fr.uvsq.pglp.roguelike.personnage.attributs.ScoreDeCaracteristique;
import fr.uvsq.pglp.roguelike.personnage.ia.AgressifIa;
import fr.uvsq.pglp.roguelike.personnage.ia.AmicalIa;
import fr.uvsq.pglp.roguelike.personnage.ia.DefensifIa;
import fr.uvsq.pglp.roguelike.personnage.ia.JoueurIa;
import fr.uvsq.pglp.roguelike.personnage.ia.NeutreIa;
import fr.uvsq.pglp.roguelike.personnage.ia.PersonnageIa;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Cette classe représente un personnage (joueur ou PNJ) de donjon.
 *
 * <p>Un personnage de donjon possède un nom, des points de vie,
 * 6 {@link Caracteristique}s, trois scores d'attaque (contact, distance, magique),
 * des points d'initiative et de défense fixes, une bourse pouvant contenir des
 * pièces d'argent, un {@link Sac} pouvant contenir 20 {@link Equipement}s, une position
 * et une certaine IA ({@link PersonnageIa}).</p>
 *
 * <p>De plus, chaque personnage de donjon peut porter une {@link ArmureDeCorps} à la fois,
 * et possède deux mains pour porter des armes et des boucliers.</p>
 *
 * @author Tom Abbouz
 * @version Février 2023
 * @see Personnage
 */
public class PersonnageDonjon implements Personnage {

  private final String nom;
  private final TypeIa typeIa;
  private PersonnageIa intArt;
  private MorceauEtage morceauEtage;

  private int axeX;
  private int axeY;
  
  private final ScoreDeCaracteristique[] caracteristiques;
  private int initiativeBase;
  private int defenseBase;

  PersonnageDonjon(PersonnageBuilder builder) {

    // Paramètres requis
    this.nom = builder.nom;
    this.axeX = builder.axeX;
    this.axeY = builder.axeY;
    this.typeIa = builder.typeIa;
    genererPersonnageIa();
    
    this.caracteristiques = builder.caracteristiques;
    this.initiativeBase = get(Caracteristique.DEX).mod();
    this.defenseBase = (10 + get(Caracteristique.DEX).mod());
  }

  private ScoreDeCaracteristique get(Caracteristique caracteristique) {
    for(ScoreDeCaracteristique s : caracteristiques) {
      if(s.type().equals(caracteristique)) {
        return s;
      }
    }
    return null;
  }

  /**
   * Attribue un sous-type de {@link PersonnageIa} au PersonnageDonjon.
   */
  public void genererPersonnageIa() {
    switch (typeIa) {
      case AGRESSIF:
        intArt = new AgressifIa(this);
        break;
      case AMICAL:
        intArt = new AmicalIa(this);
        break;
      case DEFENSIF:
        intArt = new DefensifIa(this);
        break;
      case JOUEUR:
        intArt = new JoueurIa(this, new ArrayList<String>());
        break;
      case NEUTRE:
        intArt = new NeutreIa(this);
        break;
      default:
        break;
    }
  }

  @Override
  public String getNom() {
    return nom;
  }

  @Override
  public int getX() {
    return axeX;
  }

  @Override
  public void setX(int x) {
    axeX = x;
  }

  @Override
  public int getY() {
    return axeY;
  }

  @Override
  public void setY(int y) {
    axeY = y;
  }

  @Override
  public void setPosition(int x, int y) {
    this.axeX = x;
    this.axeY = y;
  }

  @Override
  public PersonnageIa getIa() {
    return intArt;
  }

  /**
   * Essaie de déplacer le personnage.
   * 
   * <p>Si le personnage peut être déplacer, alors il on appelle la méthode
   * {@link PersonnageIa#onEnter(int, int, Tile)} pour déplacer le PersonneDonjon.</p>
   *
   * @param mx Déplacement souhaité selon l'axe X.
   * @param my Déplacement souhaité selon l'axe Y.
   */
  @Override
  public void moveBy(int mx, int my) {
    if (mx == 0 && my == 0) {
      return;
    }

    if(axeX + mx < 0 || axeX + mx >= morceauEtage.largeur() 
        || axeY + my < 0 || axeY + my >= morceauEtage.longueur() ) {
      Ouvrable ouvrable = morceauEtage.ouvrables(axeX, axeY);
      if(ouvrable != null && !ouvrable.isOuverte()) {
        notifier("Vous ne pouvez pas passer car il y a " + ouvrable.getType().nom() + "!");
        return;
      } else if (isPlayer()) {
        MorceauEtage temp =  morceauEtage;
        morceauEtage = morceauEtage.joueurEntre(ouvrable);
        temp = temp.joueurSort(ouvrable);
      } else {
        return;
      }
    }
    
    Tile tile = morceauEtage.tiles(axeX + mx, axeY + my);

    if (!tile.isGround()) {
      intArt.notifier("C'est " + tile.nom() + " !");
    }
    Personnage other = morceauEtage.personnages(axeX + mx, axeY + my);

    if (other == null) {
      intArt.onEnter(axeX + mx, axeY + my, tile);
    } else {
      intArt.notifier("Il y a déjà " + other.getNom() + " ici.");
    }
  }

  @Override
  public void morceauEtage(ElementEtage elementEtage) {
    this.morceauEtage = (MorceauEtage) elementEtage;
  }

  public MorceauEtage getMorceauEtage() {
    return morceauEtage;
  }

  @Override
  public void notifier(String message, Object... params) {
    intArt.notifier(String.format(message, params));
  }

  @Override
  public ElementEtage getElementEtage() {
    return morceauEtage;
  }

  /**
   * <b>Classe static Builder</b> de {@link PersonnageDonjon}.
   *
   * <p>On utilise le patron <b>Builder</b> afin d'avoir de pouvoir créer
   * des {@link PersonnageDonjon}s par défaut facilement tout en pouvant les
   * personnaliser facilement.</p>
   *
   * @author Tom Abbouz
   * @version Février 2023
   */
  public static class PersonnageBuilder {

    // Paramètres requis
    private final String nom;
    private final int axeX;
    private final int axeY;
    
    // Paramètres optionnels
    private TypeIa typeIa;
    
    private ScoreDeCaracteristique[] caracteristiques;
    private Caracteristique[] priorite;

    /**
     * Constructeur de PersonnageBuilder.
     *
     * @param nom Le nom du personnage.
     */
    public PersonnageBuilder(String nom, int axeX, int axeY) {
      this.nom = nom;
      this.axeX = axeX;
      this.axeY = axeY;
      this.typeIa = TypeIa.AGRESSIF;
      this.priorite = Caracteristique.randomOrder();
      this.caracteristiques = calculCaracteristiques(65, 80);
    }

    private ScoreDeCaracteristique[] calculCaracteristiques(int limInf, int limSup) {
        int nbCar = Caracteristique.values().length;
        ScoreDeCaracteristique[] calculer = new ScoreDeCaracteristique[nbCar];
        int somme = 0;
        int[] arr = new int[nbCar];

        do {
          int[][] resultatsDes = new int[nbCar][4];

          for (int i = 0; i < nbCar; i++) {
            for (int j = 0; j < 4; j++) {
              resultatsDes[i][j] = (int) ((Math.random() * 6) + 1);
            }
          }

          for (int i = 0; i < nbCar; i++) {
            Arrays.sort(resultatsDes[i]);
            arr[i] = resultatsDes[i][3] + resultatsDes[i][2] + resultatsDes[i][1];
          }

          somme = Arrays.stream(arr).sum();

        } while (somme > limSup && somme < limInf);

        Arrays.sort(arr);

        for (int i = 0; i < nbCar; i++) {
          calculer[i] = new ScoreDeCaracteristique(priorite[nbCar - i - 1], arr[i]);
          System.out.println(priorite[nbCar - i - 1].toString() + "a une valeur de : " + arr[i]);
        }
        System.out.println("");
        System.out.println("");
        return calculer;
    }
    
    /**
     * Echange les scores des {@link Caracteristique}s du {@link PersonnageDonjon}
     * selon un certain ordre.
     *
     * @param priorite Le nouvel ordre de priorité pour les {@link Caracteristique}s
     *
     * @see PersonnageDonjon#caracteristiquesActualiser()
     */
    public void setPriorite(Caracteristique[] priorite) {

      if (priorite.length != Caracteristique.values().length) {
        throw new IllegalArgumentException(
                "Vous avez renseigné un ordre de priorité inapproprié."
                        + " Il faut " + Caracteristique.values().length 
                        + " caractéristiques différentes."
                        + " Vous en avez renseigné " + priorite.length + "."
        );
      }

      for (int i = 0; i < priorite.length - 1; i++) {
        for (int j = i + 1; i < priorite.length; j++) {
          if (priorite[i] == priorite[j]) {
            throw new IllegalArgumentException(
                    "Vous avez renseigné un ordre de priorité inapproprié."
                            + " Il faut " + Caracteristique.values().length 
                            + " caractéristiques différentes."
                            + " Vous en avez renseigné deux fois ou plus " 
                            + priorite[j].getNom() + "."
            );
          }
        }
      }

      this.priorite = priorite;
      this.caracteristiques = calculCaracteristiques(65, 80);
    }

    public PersonnageBuilder setIa(TypeIa typeIa) {
      this.typeIa = typeIa;
      return this;
    }

    public PersonnageDonjon build() {
      return new PersonnageDonjon(this);
    }
  }

  public int getInit() {
    return initiativeBase;
  }

  public int getMod(Caracteristique caracteristique) {
    return get(caracteristique).mod();
  }

  @Override
  public void update() {
    intArt.onUpdate();
  }

  
  private boolean isPlayer() {
    return (intArt instanceof JoueurIa);
  }

  @Override
  public Personnage personnage(int i, int j) {
    return morceauEtage.personnages(i, j);
  }

  public void ouvrirPorte() {
    Ouvrable ouvrable = morceauEtage.ouvrables(axeX, axeY);
    if(ouvrable != null) {
      if(ouvrable instanceof Porte) {
        if(!ouvrable.isOuverte()) {
          switch(ouvrable.getType()) {
          case PORTE:
            ouvrable.ouvrir();
            notifier("Vous avez ouvert la porte.");
            break;
          case PORTEACROCHETER:
            notifier("Cette porte ne peut pas être ouverte, il faut la crocheter !");
            break;
          default:
            notifier("Cette porte ne peut pas être simplement ouverte.");
            break;
          }
        } else {
          if (ouvrable.getType().ouvrable() ) {
            notifier("Cette porte a déjà été ouverte.");
          } else if (ouvrable.getType().crochetable() ) {
            notifier("Cette porte a déjà été crochetée.");
          }
        }
      } else {
        notifier("Ce n'est pas une porte.");
      }
    } else {
      notifier("Il n'y a aucune porte ici.");
    }
  }

  public void ouvrirCoffre() {
    Ouvrable ouvrable = morceauEtage.ouvrables(axeX, axeY);
    if(ouvrable != null) {
      if(ouvrable instanceof Tresor) {
        if(!ouvrable.isOuverte()) {
          switch(ouvrable.getType()) {
          case CHEST:
            Echangeable p = ouvrable.ouvrir();
            morceauEtage.addEchangeable(axeX, axeY, p);
            notifier("Vous avez ouvert le trésor.");
            notifier("Il contient " + p.getNom() + "!");
            break;
          case BOX:
            notifier("Ce trésor ne peut pas être ouvert, il doit être forcer !");
            break;
          default:
            notifier("Ce trésor ne peut pas être simplement ouvert.");
            break;
          }
        } else {
          notifier("Ce trésor a déjà été ouvert.");
        }
      } else {
        notifier("Ce n'est pas un trésor.");
      }
    } else {
      notifier("Il n'y a aucun trésor ici.");
    }
  }

  public void crocheterPorte() {
    Ouvrable ouvrable = morceauEtage.ouvrables(axeX, axeY);
    if(ouvrable != null) {
      if(ouvrable instanceof Porte) {
        if(!ouvrable.isOuverte()) {
          switch(ouvrable.getType()) {
          case PORTE:
            notifier("Inutile de crocheter la porte, il suffit de l'ouvrir !");
            break;
          case PORTEACROCHETER:
            if(ouvrable.getDiffValeur() <= (20 + getMod(Caracteristique.DEX))) {
              if(ouvrable.tirage(getMod(Caracteristique.DEX))) {
                ouvrable.ouvrir();
                notifier("Vous avez réussi à crocheter la porte!");
              } else {
                notifier("Cette porte est " + ouvrable.getDifficulte() + " à crocheter.");
              }
            } else {
              notifier("Inutile d'essayer, votre dextérité ne suffit pas pour crocheter cette porte!");
            }
            break;
          default:
            notifier("Cette porte ne peut pas être crochetée.");
            break;
          }
        } else {
          if (ouvrable.getType().ouvrable() ) {
            notifier("Cette porte a déjà été ouverte.");
          } else if (ouvrable.getType().crochetable() ) {
            notifier("Cette porte a déjà été crochetée.");
          }
        }
      } else {
        notifier("Ce n'est pas une porte.");
      }
    } else {
      notifier("Il n'y a aucune porte ici.");
    }
  }

  public void forcerCoffre() {
    
    Ouvrable ouvrable = morceauEtage.ouvrables(axeX, axeY);
    if(ouvrable != null) {
      if(ouvrable instanceof Tresor) {
        if(!ouvrable.isOuverte()) {
          switch(ouvrable.getType()) {
          case CHEST:
            notifier("Inutile de forcer le trésor, il suffit de l'ouvrir !");
            break;
          case BOX:
            if(ouvrable.getDiffValeur() <= (20 + getMod(Caracteristique.CON))) {
              if(ouvrable.tirage(getMod(Caracteristique.CON))) {
                
                Echangeable p = ouvrable.ouvrir();
                morceauEtage.addEchangeable(axeX, axeY, p);
                ouvrable.ouvrir();      
                notifier("Vous avez réussi à forcer le trésor!");
                notifier("Il contient " + p.getNom() + "!");
              } else {
                notifier("Ce trésor est " + ouvrable.getDifficulte() + " à forcer.");
              }
            } else {
              notifier("Inutile d'essayer, votre constitution ne suffit pas pour forcer ce trésor!");
            }
            break;
          default:
            notifier("Ce trésor ne peut pas être forcé.");
            break;
          }
        } else {
          if (ouvrable.getType().ouvrable() ) {
            notifier("Ce trésor a déjà été ouvert.");
          } else if (ouvrable.getType().forcable() ) {
            notifier("Ce trésor a déjà été forcé.");
          }
        }
      } else {
        notifier("Ce n'est pas un trésor.");
      }
    } else {
      notifier("Il n'y a aucun trésor ici.");
    }
  }
}

