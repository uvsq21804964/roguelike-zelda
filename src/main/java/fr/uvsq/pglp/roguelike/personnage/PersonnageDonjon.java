package fr.uvsq.pglp.roguelike.personnage;

import fr.uvsq.pglp.roguelike.donjon.MorceauEtage;
import fr.uvsq.pglp.roguelike.donjon.elements.Couloir;
import fr.uvsq.pglp.roguelike.donjon.elements.ElementEtage;
import fr.uvsq.pglp.roguelike.donjon.elements.Ouvrable;
import fr.uvsq.pglp.roguelike.donjon.elements.Porte;
import fr.uvsq.pglp.roguelike.donjon.elements.Salle;
import fr.uvsq.pglp.roguelike.donjon.elements.Tile;
import fr.uvsq.pglp.roguelike.donjon.elements.Tresor;
import fr.uvsq.pglp.roguelike.echangeable.Arme;
import fr.uvsq.pglp.roguelike.echangeable.ArmeContact;
import fr.uvsq.pglp.roguelike.echangeable.ArmeDistance;
import fr.uvsq.pglp.roguelike.echangeable.ArmureDeCorps;
import fr.uvsq.pglp.roguelike.echangeable.Bouclier;
import fr.uvsq.pglp.roguelike.echangeable.Echangeable;
import fr.uvsq.pglp.roguelike.echangeable.Equipement;
import fr.uvsq.pglp.roguelike.echangeable.Pieces;
import fr.uvsq.pglp.roguelike.personnage.attributs.Caracteristique;
import fr.uvsq.pglp.roguelike.personnage.attributs.DeDeVie;
import fr.uvsq.pglp.roguelike.personnage.attributs.Jauge;
import fr.uvsq.pglp.roguelike.personnage.attributs.ScoreDeCaracteristique;
import fr.uvsq.pglp.roguelike.personnage.ia.AgressifIa;
import fr.uvsq.pglp.roguelike.personnage.ia.AmicalIa;
import fr.uvsq.pglp.roguelike.personnage.ia.DefensifIa;
import fr.uvsq.pglp.roguelike.personnage.ia.JoueurIa;
import fr.uvsq.pglp.roguelike.personnage.ia.NeutreIa;
import fr.uvsq.pglp.roguelike.personnage.ia.PersonnageIa;
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
  
  private Sac sac;
  
  private ArmureDeCorps armureDeCorps;
  private Bouclier bouclier;
  private Arme[] arme = new Arme[2];
  
  private int scoreContact;
  private int scoreDistance;
  
  private Jauge pointsDeVie;
  private DeDeVie deDeVie;
  

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
    
    this.sac = builder.sac;
    
    this.arme[0] = builder.arme[0];
    this.arme[1] = builder.arme[1];
    this.bouclier = builder.bouclier;
    this.armureDeCorps = builder.armureDeCorps;
    
    this.scoreContact = (getMod(Caracteristique.FOR) + 1);
    this.scoreDistance = (getMod(Caracteristique.DEX) + 1);
    
    this.deDeVie = builder.deDeVie;
    int pointsDeVieMax = deDeVie.valeurMax() + getMod(Caracteristique.CON) + 1;
    this.pointsDeVie = new Jauge(pointsDeVieMax, pointsDeVieMax);
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
        intArt = new JoueurIa(this);
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
  public boolean moveBy(int mx, int my) {
    if (mx == 0 && my == 0) {
      return false;
    }
    
    if(isPlayer()) {
      DefensifIa defIa = null;
      for(Personnage p : morceauEtage.getPersonnages()) {
        if(p.getIa() instanceof DefensifIa) {
            defIa = ((DefensifIa) p.getIa());
            if(defIa.dansZoneDefense(axeX + mx, axeY + my) && !defIa.dansZoneDefense(axeX, axeY)) {
              notifier("Vous ne pouvez pas passer car il y a une barrière à franchir.");
              p.notifier("Ahah, ma barrière n'est-elle pas jolie " + nom + " ?");
              return false;
            } else if (!defIa.dansZoneDefense(axeX + mx, axeY + my) && defIa.dansZoneDefense(axeX, axeY)) {
              notifier("Vous ne pouvez pas passer car il y a une barrière à franchir.");
              p.notifier("Ahah, ma barrière n'est-elle pas jolie " + nom + " ?");
              return false;
            }
        }
      }
    }

    if(axeX + mx < 0 || axeX + mx >= morceauEtage.largeur() 
        || axeY + my < 0 || axeY + my >= morceauEtage.longueur() ) {
      Ouvrable ouvrable = morceauEtage.ouvrables(axeX, axeY);
      if(ouvrable != null && !ouvrable.isOuverte() && isPlayer()) {
        notifier("Vous ne pouvez pas passer car il y a " + ouvrable.getType().nom() + "!");
        return false;
      } else if (isPlayer()) {
        MorceauEtage temp =  morceauEtage;
        morceauEtage = morceauEtage.joueurEntre(ouvrable);
        temp = temp.joueurSort(ouvrable);
      } else {
        return false;
      }
    }
    
    Tile tile = morceauEtage.tiles(axeX + mx, axeY + my);

    if (!tile.isGround()) {
      if(isPlayer()) {
        intArt.notifier("C'est " + tile.nom() + " !");
      }
      return false;
    }
    Personnage other = morceauEtage.personnages(axeX + mx, axeY + my);

    if (other == null) {
      intArt.onEnter(axeX + mx, axeY + my, tile);
      return true;
    } else if (isPlayer()) {
      intArt.notifier("Il y a déjà " + other.getNom() + " ici.");
      return false;
    }
    return false;
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
    
    private Sac sac;
    
    private ArmureDeCorps armureDeCorps;
    private Bouclier bouclier;
    private Arme[] arme = new Arme[2];
    
    private DeDeVie deDeVie;

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
      this.sac = new Sac(5, 0);
      this.arme[0] = null;
      this.arme[1] = null;
      this.bouclier = null;
      this.armureDeCorps = null;
      this.deDeVie = new DeDeVie(((int) (Math.random()*5)*2) + 4);
    }
    
    public PersonnageBuilder setSac(int taille, int nbPieces) {
      this.sac = new Sac(taille, nbPieces);
      return this;
    }
    
    public PersonnageBuilder setDe(int valeurMax) {
      this.deDeVie = new DeDeVie(valeurMax);
      return this;
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
        }
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
    public PersonnageBuilder setPriorite(Caracteristique[] priorite) {

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
      return this;
    }

    public PersonnageBuilder setIa(TypeIa typeIa) {
      this.typeIa = typeIa;
      return this;
    }
    
    public PersonnageBuilder addEquipement(Equipement e) {
      this.sac.add(e);
      return this;
    }
    
    public PersonnageBuilder addEquipementRandom() {
      this.sac.addRandom();
      return this;
    }
    
    public PersonnageBuilder equiper(Equipement e) {
      if(typeIa.equals(TypeIa.JOUEUR) && e instanceof Arme) {
        this.arme[0] = (Arme) e;
      } else if (!(typeIa.equals(TypeIa.AMICAL))) {
        this.arme[0] = (Arme) sac.getType(ArmeContact.BATON);
        if(!this.arme[0].isDeuxMains()) {
          this.bouclier = (Bouclier) sac.getType(Bouclier.GRANDBOUCLIER);
        }
        this.armureDeCorps = (ArmureDeCorps) sac.getType(ArmureDeCorps.CHEMISE);
      }
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
  
  public int getVal(Caracteristique caracteristique) {
    return get(caracteristique).val();
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
            ouvrable.ouvrir(this);
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
            Echangeable p = ouvrable.ouvrir(this);
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
                ouvrable.ouvrir(this);
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
                
                Echangeable p = ouvrable.ouvrir(this);
                morceauEtage.addEchangeable(axeX, axeY, p);
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

  public void commandeRamasser(String s) {
    
    Echangeable e = morceauEtage.echangeables(axeX, axeY);
    boolean ramasser = false;
    
    if(e != null) {
      switch (s) {
      case "pièce":
      case "pièces":
        if(e instanceof Pieces) {
          ramasser = true;
        }
        break;
      case "arme":
        if(e instanceof Arme) {
          ramasser = true;
        }
        break;
      case "armure":
        if(e instanceof ArmureDeCorps) {
          ramasser = true;
        }
        break;
      case "bouclier":
        if(e instanceof Bouclier) {
          ramasser = true;
        }
        break;
      case "équipement":
        if(e instanceof Equipement) {
          ramasser = true;
        }
        break;
      case "tout":
        ramasser = true;
        break;
      default:
        break;
      }
      
      if(ramasser) {
        ramasser();
      } else {
        if(s.equals("armure")) {
          notifier("Il n'y a pas d'armure de corps à ramasser ici, seulement "+ e.getNom() +".");
        } else if(s.equals("arme") || s.equals("équipement")) {
          notifier("Il n'y a pas d'" + s + " à ramasser ici, seulement "+ e.getNom() +".");
        } else {
          notifier("Il n'y a pas de " + s + " à ramasser ici, seulement "+ e.getNom() +".");
        }
      }
    } else {
      notifier("Il n'y a rien par terre.");
    }
  }

  private void ramasser() {

    Echangeable e = morceauEtage.echangeables(axeX, axeY);
    
    if(e != null) {
      if(sac.contient(e)) {
        notifier("Vous possèder déjà " + e.getNom() + ", inutile de vous encombrer.");
      } else {
        if(sac.add(e)) {
          morceauEtage.addEchangeable(axeX, axeY, null);
          notifier("Vous avez ramassé " + e.getNom() + ".");
        } else {
          if(e instanceof Pieces) {
            notifier("Vous ne pouvez pas ramassé "+ e.getNom() 
            +" car il n'y a plus de place dans votre bourse.");
          } else if (e instanceof Equipement) {
            notifier("Vous ne pouvez pas ramassé "+ e.getNom() 
            +" car il n'y a plus de place dans votre sac.");
          }
        }
      }
    }
  }

  public int tailleSac() {
    return sac.quantite();
  }

  public Equipement getEquipement(int i) {
    return sac.get(i);
  }

  @Override
  public boolean attaquer(Personnage proie) {
    if(arme[0] == null && arme[1] == null) {
      notifier("Vous ne pouvez pas attaquer car vous ne portez aucune arme.");
      return false;
    }
    
    boolean armeDistance = false;
    
    boolean toucher = false;
    
    if(arme[0] != null && arme[0] instanceof ArmeDistance) {
      armeDistance = true;
      attaquerAvec(proie, arme[0]);
      toucher = true;
    }
    if(arme[1] != null && arme[1] instanceof ArmeDistance) {
      armeDistance = true;
      attaquerAvec(proie, arme[1]);
      toucher = true;
    }
    
    boolean proche = false;
    
    for(int i = axeX - 1 ; i <= axeX + 1 ; i++) {
      for(int j = axeY - 1 ; j <= axeY + 1 ; j++) {
        if(proie.getX() == i && proie.getY() == j && (i != axeX || j != axeY)) {
          proche = true;
        }
      }
    }
    
    
    if(!proche) {
      if(!armeDistance && getIa() instanceof JoueurIa) {
        notifier(proie.getNom() + " est inatteignable car vous ne portez aucune arme à distance.");
      }
    }
    
    if(procheDe(proie)) {
      if(arme[0] != null && arme[0] instanceof ArmeContact) {
        attaquerAvec(proie, arme[0]);
        toucher = true;
      }
      if(arme[1] != null && arme[1] instanceof ArmeContact) {
        attaquerAvec(proie, arme[1]);
        toucher = true;
      }
    }
    
    if(toucher && proie.getIa() instanceof NeutreIa) {
      ((NeutreIa) proie.getIa()).doitAttaquerJoueur();
    }
    
    return toucher;
  }

  public boolean mort() {
    return pointsDeVie.vide();
  }

  private void attaquerAvec(Personnage proie, Arme arme) {
    
    if(arme == null) {
      return;
    }
    
    if(arme instanceof ArmeDistance) {
      
      if(((ArmeDistance) arme).getPortee() < distance(proie, this)) {
        notifier("Impossible d'attaquer " + proie.getNom() + " avec "
            + arme.getNom() + " car sa portée est insuffisante.");
      }
      
      if(tirage(proie, scoreDistance +  arme.getTirageMax(), arme.getNom())) {
        int degat = arme.getTirage();
        ((PersonnageDonjon) proie).modifierPv(degat);
        proie.notifier("Aïe, " + degat + " dégâts infligés à distance "
            + "par " + nom + " utilisant " + arme.getNom() + ".");
        notifier("Hop, " + degat + " points de vie en moins pour " + proie.getNom() + " grâce à " + arme.getNom() + ".");
      }
    }
    
    if(arme instanceof ArmeContact) {
      
      if(!procheDe(proie)) {
        notifier("Impossible d'attaquer " + proie.getNom() + " avec "
            + arme.getNom() + " car il n'est pas à proximité.");
      }
      
      if(tirage(proie, scoreContact + arme.getTirageMax(), arme.getNom())) {
        int degat = arme.getTirage() + getMod(Caracteristique.FOR);
        ((PersonnageDonjon) proie).modifierPv(degat);
        proie.notifier("Aïe, " + degat + " dégâts infligés au contact "
            + "par " + nom + " utilisant " + arme.getNom() + ".");
        notifier("Hop, " + degat + " points de vie en moins pour " + proie.getNom() + " grâce à " + arme.getNom() + ".");
      }
    }
  }
  
  public boolean modifierPv(int degat) {

     pointsDeVie.modifier(- degat);
     
     if(pointsDeVie.vide()) {
       return true;
     }
     
     return true;
  }

  private int distance(Personnage p1, Personnage p2) {
    int distX = Math.abs(p1.getX() - p2.getX());
    int distY = Math.abs(p1.getY() - p2.getY());
    
    int d = (int) Math.sqrt(distX*distX + distY * distY);
    return d;
  }

  public boolean tirage(Personnage proie, int score, String nomArme) {
    int defense = ((PersonnageDonjon) proie).getDefense();
    if(defense < (20 + score)) {
      int tirage = ((int) (Math.random()*20)+1);
      if(defense <= (tirage + score)) {
        return true;
      } else {
        notifier("Zut, j'ai raté mon attaque avec "+ nomArme + " sur " + proie.getNom() + " ?!!");
        proie.notifier("Ahah " + nom + ", j'ai esquivé ton attaque avec "+ nomArme + " !");
        return false;
      }
    } else {
      notifier("La défense de " + proie.getNom() + ", "
          + "est trop grande pour pouvoir l'attaquer avec "+ nomArme +" !");
      return false;
    }
  }

  private boolean procheDe(Personnage proie) {
    for(int i = axeX - 1 ; i <= axeX + 1 ; i++) {
      for(int j = axeY - 1 ; j <= axeY + 1 ; j++) {
        if((i != axeX || j != axeY) && proie.getX() == i && proie.getY() == j) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean convaincre(String s) {
    for(PersonnageDonjon p : morceauEtage.getPersonnages()) {
      if(p.getNom().toLowerCase().equals(s)) {
        if((p.getIa() instanceof NeutreIa)) {
          convaincre(p);
        } else {
          notifier(p.getNom() + " n'est pas ouvert à la discussion, "
              + "inutile d'essayer de le convaincre.");
          if(p.getIa() instanceof AmicalIa) {
            p.notifier(getNom() + ", je suis désolé mais ce n'est pas mon"
                + "genre de me battre.");
            p.notifier("Si tu veux " + getNom() + ", on peut parler afin de te "
                + "vendre un équipement.");
          } else {
            p.notifier(getNom() + ", ta cause ne sera jamais la mienne!");
          }
        }
        return true;
      }
    }
    String initiale = s.substring(0,1).toUpperCase();
    String reste = s.substring(1,s.length()).toLowerCase();
    s = initiale + reste;
    notifier("Il n'y a aucun " + s + " dans cette salle.");
    return false;
  }

  private void convaincre(PersonnageDonjon p) {
    
    boolean entendu = false;
    
    for(int i = axeX - 1 ; i <= axeX + 1 ; i++) {
      for(int j = axeY - 1 ; j <= axeY + 1 ; j++) {
        if(morceauEtage.personnages(i,j) == p) {
          entendu = true;
        }
      }
    }
    
    if(!entendu) {
      notifier(p.getNom() + " ne peut pas être convaincu car il ne vous entends pas.");
    } else {
      if(p.getIa() instanceof NeutreIa) {
        String s = ((NeutreIa) p.getIa()).convaincre(getMod(Caracteristique.INT), nom);
        notifier(s);
      } else {
        notifier("Vous ne pouvez pas convaincre " + p.getNom() + " de se rallier à vous!");
      }
    }
  }

  public boolean parler(String s) {
    for(PersonnageDonjon p : morceauEtage.getPersonnages()) {
      if(p.getNom().toLowerCase().equals(s)) {
        if((p.getIa() instanceof AmicalIa)) {
          parler(p);
        } else {
          notifier(p.getNom() + " n'est pas ouvert à la discussion, "
              + "il ne veut pas parler avec vous.");
          if(p.getIa() instanceof NeutreIa) {
            p.notifier(getNom() + ", je suis pressé et je n'ai pas d'argent...");
          } else if(p.getIa() instanceof DefensifIa) {
            p.notifier(getNom() + ", ne t'approches pas de mon territoire, c'est"
                + "tout ce que j'ai à te dire!");
          } else {
              p.notifier(getNom() + ", je ne veux pas parler mais te tuer!");
          }
        }
        return true;
      }
    }
    String initiale = s.substring(0,1).toUpperCase();
    String reste = s.substring(1,s.length()).toLowerCase();
    s = initiale + reste;
    notifier("Il n'y a aucun " + s + " dans cette salle.");
    return false;
  }

  private void parler(PersonnageDonjon p) {

    boolean entendu = false;
    
    for(int i = axeX - 1 ; i <= axeX + 1 ; i++) {
      for(int j = axeY - 1 ; j <= axeY + 1 ; j++) {
        if(morceauEtage.personnages(i,j) == p) {
          entendu = true;
        }
      }
    }
    
    if(!entendu) {
      notifier(p.getNom() + " ne peut pas parler avec vous car il ne vous entends pas.");
    } else {
      if(p.getIa() instanceof AmicalIa) {
        notifier(p.getNom() + " vous propose un équipement à vendre.");
        ((AmicalIa) p.getIa()).reponse(getNom());
      } else {
        notifier(p.getNom() + " n'a rien à vous dire.");
      }
        
    }
  }

  public boolean soutirer(String s) {
    for(PersonnageDonjon p : morceauEtage.getPersonnages()) {
      if(p.getNom().toLowerCase().equals(s)) {
        if((p.getIa() instanceof AmicalIa)) {
          soutirer(p);
        } else {
          notifier(p.getNom() + " n'est pas ouvert à la discussion, "
              + "vous ne pourrez rien lui soutirer.");
          if(p.getIa() instanceof NeutreIa) {
            p.notifier(getNom() + ", je suis pressé et je n'ai pas d'argent...");
          } else if(p.getIa() instanceof DefensifIa) {
            p.notifier(getNom() + ", ne t'approches pas de mon territoire, c'est"
                + "tout ce que j'ai à te dire!");
          } else {
              p.notifier(getNom() + ", je ne veux pas parler mais te tuer!");
          }
        }
        return true;
      }
    }
    String initiale = s.substring(0,1).toUpperCase();
    String reste = s.substring(1,s.length()).toLowerCase();
    s = initiale + reste;
    notifier("Il n'y a aucun " + s + " dans cette salle.");
    return false;
  }

  private void soutirer(PersonnageDonjon p) {
    boolean entendu = false;
    
    for(int i = axeX - 1 ; i <= axeX + 1 ; i++) {
      for(int j = axeY - 1 ; j <= axeY + 1 ; j++) {
        if(morceauEtage.personnages(i,j) == p) {
          entendu = true;
        }
      }
    }
    
    if(!entendu) {
      notifier(p.getNom() + " ne peut pas parler avec vous car il ne vous entends pas.");
    } else {
      if(p.getIa() instanceof AmicalIa) {
        String s = ((AmicalIa) p.getIa()).soutirer(getMod(Caracteristique.CHA));
        notifier(s);
      } else {
        notifier(p.getNom() + " n'a aucun information intéressante à vous donner.");
      }
    }
  }
  
  @Override
  public String nomJoueur() {
    return morceauEtage.getJoueur().getNom();
  }
  
  public PersonnageDonjon getJoueur() {
    return (PersonnageDonjon) morceauEtage.getJoueur();
  }

  public boolean acheter(String s) {
    for(PersonnageDonjon p : morceauEtage.getPersonnages()) {
      if(p.getNom().toLowerCase().equals(s)) {
        if((p.getIa() instanceof AmicalIa)) {
          acheter(p);
        } else {
          notifier(p.getNom() + " n'a rien à vendre.");
          if(p.getIa() instanceof NeutreIa) {
            p.notifier(getNom() + ", je n'ai aucun équipement à te vendre.");
          } else if(p.getIa() instanceof DefensifIa) {
            p.notifier(getNom() + ", ahah je suis sûr que tu es un voleur!"
                + " Je n'ai rien à te vendre!");
          } else {
              p.notifier(getNom() + ", n'essaie pas d'acheter la paix! Je veux juste te tuer.");
          }
        }
        return true;
      }
    }
    String initiale = s.substring(0,1).toUpperCase();
    String reste = s.substring(1,s.length()).toLowerCase();
    s = initiale + reste;
    notifier("Il n'y a aucun " + s + " dans cette salle.");
    return false;
  }

  private void acheter(PersonnageDonjon p) {
    boolean entendu = false;
    
    for(int i = axeX - 1 ; i <= axeX + 1 ; i++) {
      for(int j = axeY - 1 ; j <= axeY + 1 ; j++) {
        if(morceauEtage.personnages(i,j) == p) {
          entendu = true;
        }
      }
    }
    
    if(!entendu) {
      notifier(p.getNom() + " ne peut pas parler avec vous car il ne vous entends pas.");
    } else {
      if(p.getIa() instanceof AmicalIa) {
        if (demunis()) {
            p.notifier("Je ne marchande pas avec les demunis, mon cher "+ nom + "!");
            notifier("Votre bourse est vide!");
            notifier("Trouver et ouvrez des trésors pour gagner des pièces d'argent.");
        } else if (sacPlein()) {
            p.notifier("Vous semblez déjà bien chargé "+ nom + "!");
            notifier("Votre sac est plein, il ne peut pas contenir d'équipement supplémentaire.");
        } else {
          Echangeable equipEnVente = ((AmicalIa) p.getIa()).enVente();
          if(equipEnVente != null) {
            int prix = ((AmicalIa) p.getIa()).prix();
            if(sac.payer(prix)) {
              if(sac.add(equipEnVente)) {
                notifier("Vous avez acheté " + equipEnVente.getNom() + " à " + p.getNom() 
                + " pour la somme de " + prix + " pièces d'argent.");
                p.notifier("Et " + equipEnVente.getNom() + " pour " + nom 
                    + ", "+ equipEnVente.getNom().toUpperCase() + " !");
                ((AmicalIa) p.getIa()).modifierProduitVendu();
              } else {
                notifier("Votre sac est plein, il ne peut pas contenir d'équipement supplémentaire.");
              }
            } else {
              notifier("Vous n'avez pas assez de pièces d'argent pour acheter " 
            + equipEnVente.getNom() + " à " + p.getNom() + ".");
              notifier("Il vous manque " + (prix - sac.getPieces()) + " pièces d'argent !");
              p.notifier("Ohh " + equipEnVente.getNom() + " ne vous va pas de toute manière " + nom + "...");
            }
          } else {
            p.notifier("Je n'ai plus rien à vendre "+ nom + ", repasse demain !");
            notifier(p.getNom() + " ne possède plus d'équipements à vendre.");
          }
        }
      } else {
        notifier(p.getNom() + " n'a rien à vendre.");
      }
    }
  }

  private boolean sacPlein() {
    return sac.plein();
  }

  private boolean demunis() {
    return sac.bourseVide();
  }

  public boolean retirer(Equipement e) {
    return sac.retirer(e);
  }

  public boolean sauter(int mx, int my) {

    if(mx != 0 && my != 0) {
      throw new IllegalArgumentException("On ne peut sauter que selon un axe."
          + " Il faut donc que 'mx' ou 'my' soit nul. Vous avez renseigné "
          + "pour mx : " + mx + " et pour my : " + my + ".");
    }
    
    if(Math.abs(mx) != 2 && Math.abs(my) != 2) {
      throw new IllegalArgumentException("On ne peut sauter que de deux cases."
          + " Il faut donc que 'mx' ou 'my' soit égal à 2. Vous avez renseigné "
          + "pour mx : " + mx + " et pour my : " + my + ".");
    }
    
    if (mx == 0 && my == 0) {
      return false;
    }

    if(axeX + mx <= 0 || axeX + mx >= morceauEtage.largeur() - 1
        || axeY + my <= 0 || axeY + my >= morceauEtage.longueur() - 1 ) {
      if(morceauEtage instanceof Couloir) {
        notifier("Vous ne pouvez pas sauter hors du couloir!");
      } else if (morceauEtage instanceof Salle){
        notifier("Vous ne pouvez pas sauter hors de la salle!");
      }
      return false;
    }
    
    if(mx != 0) {
      int signe = 1;
      if(mx < 0) {
        signe = -1;
      }

      for(int i = 1 ; i <= Math.abs(mx) ; i++) {
        int j = i*signe;
        Tile tile = morceauEtage.tiles(axeX + j, axeY);
        Personnage other = morceauEtage.personnages(axeX + j, axeY);
        
        String direction = "";
        if (mx > 0) {
          direction = "droite";
        } else if (mx < 0) {
          direction = "gauche";
        }
        
        if (!tile.isGround()) {
          if(isPlayer()) {
            notifier("Impossible de sauter vers la "+ direction +".");
            notifier("Vous risqueriez de vous cogner contre " + tile.nom() + "!");
           }
          return false;
        }
        
        if(mx != j) {
            if(other != null) {
              if(!sauter(other)) {
                notifier("Vous n'avez pas réussi à sauter vers la "+ direction +".");
                  return false;
                }
              } else {
                notifier("Il n'y a personne par-dessus qui sauter vers la "+ direction +".");
                return false;
              }
           } else if (other != null){
             notifier("Impossible de sauter vers la "+ direction +".");
             notifier("Vous risqueriez de vous cogner contre " + other.getNom() + "!");
           }
        }
    } else if (my != 0) {
      int signe = 1;
      if(my < 0) {
        signe = -1;
      }
        for(int i = 1 ; i <= Math.abs(my) ; i++) {
          int j = i*signe;
          Tile tile = morceauEtage.tiles(axeX, axeY + j);
          Personnage other = morceauEtage.personnages(axeX, axeY + j);
          
          String direction = "";
          if (my > 0) {
            direction = "le bas";
          } else if (my < 0) {
            direction = "le haut";
          }
          
          if (!tile.isGround()) {
            if(isPlayer()) {
              notifier("Impossible de sauter vers "+ direction +".");
              notifier("Vous risqueriez de vous cogner contre " + tile.nom() + "!");
             }
            return false;
          }
          
          if(my != j) {
            if(other != null) {
              if(!sauter(other)) {
                notifier("Vous n'avez pas réussi à sauter vers "+ direction +".");
                return false;
              }
            } else {
              notifier("Il n'y a personne par-dessus qui sauter vers "+ direction +".");
              return false;
            }
          } else if (other != null){
            notifier("Impossible de sauter vers "+ direction +".");
            notifier("Vous risqueriez de vous cogner contre " + other.getNom() + "!");
          }
        }
      }
      intArt.onEnter(axeX + mx, axeY + my, morceauEtage.tiles(axeX + mx, axeY + my));
      return true;
   }

  private boolean sauter(Personnage p) {
    
    PersonnageIa pIa = p.getIa();
    if(p.getIa() instanceof AgressifIa) {
        if(((AgressifIa) pIa).tirage(getMod(Caracteristique.DEX), this)) {
          p.notifier("Woaw, un véritable macaque ce " + nom +".");
          notifier("Vous avez sauter par-dessus " + p.getNom() + "!");
          return true;
        }
        return false;
    }
    p.notifier("Woaw, un véritable acrobate ce " + nom +".");
    notifier("Vous avez sauter par-dessus " + p.getNom() + "!");
    return true;
  }

  public boolean franchir(int mx, int my) {
    if(mx != 0 && my != 0) {
      throw new IllegalArgumentException("On ne peut franchir que selon un axe."
          + " Il faut donc que 'mx' ou 'my' soit nul. Vous avez renseigné "
          + "pour mx : " + mx + " et pour my : " + my + ".");
    }
    
    if(Math.abs(mx) != 1 && Math.abs(my) != 1) {
      throw new IllegalArgumentException("On ne peut franchir qu'une case à la fois."
          + " Il faut donc que 'mx' ou 'my' soit égal à 1. Vous avez renseigné "
          + "pour mx : " + mx + " et pour my : " + my + ".");
    }
    
    if (mx == 0 && my == 0) {
      return false;
    }

    if(axeX + mx <= 0 || axeX + mx >= morceauEtage.largeur() - 1
        || axeY + my <= 0 || axeY + my >= morceauEtage.longueur() - 1 ) {
      if(morceauEtage instanceof Couloir) {
        notifier("Vous ne pouvez pas franchir les murs du couloir!");
      } else if (morceauEtage instanceof Salle){
        notifier("Vous ne pouvez pas franchir les murs de la salle!");
      }
      return false;
    }
    
    
    boolean defensif = false;
    
    for(Personnage p : morceauEtage.getPersonnages()) {
      if(p.getIa() instanceof DefensifIa) {
        defensif = true;
      }
    }
    
    if(!defensif) {
      if(morceauEtage instanceof Couloir) {
        notifier("Il n'y a aucun territoire à franchir dans ce couloir!");
      } else if (morceauEtage instanceof Salle){
        notifier("Il n'y a aucun territoire à franchir dans cette salle!");
      }
      return false;
    }
    
    DefensifIa defIa = null;
    Personnage pnjDef = null;
    boolean franchir = false;
    for(Personnage p : morceauEtage.getPersonnages()) {
      if(p.getIa() instanceof DefensifIa) {
          defIa = ((DefensifIa) p.getIa());
          pnjDef = p;
          if(defIa.dansZoneDefense(axeX + mx, axeY + my) && !defIa.dansZoneDefense(axeX, axeY)) {
            franchir = true;
            break;
          } else if (!defIa.dansZoneDefense(axeX + mx, axeY + my) && defIa.dansZoneDefense(axeX, axeY)) {
            franchir = true;
            break;
          }
      }
    }
    
    String direction = "";
    if (mx > 0) {
      direction = "la droite";
    } else if (mx < 0) {
      direction = "la gauche";
    } else if (my > 0) {
      direction = "le bas";
    } else if (my < 0) {
      direction = "le haut";
    }
    
    if(!franchir) {
      notifier("Il n'y a aucune barrière à franchir vers "+ direction +".");
      return false;
    }
    
    if (mx > 0) {
      direction = "à droite";
    } else if (mx < 0) {
      direction = "à gauche";
    } else if (my > 0) {
      direction = "en bas";
    } else if (my < 0) {
      direction = "en haut";
    }
    
    Personnage p = morceauEtage.personnages(axeX + mx, axeY + my);
    if(p != null) {
      notifier("Vous ne pouvez pas franchir la barrière "+ direction +" car il y a déjà " 
    + p.getNom() + " de l'autre côté.");
      return false;
    }
    
    if(((DefensifIa) defIa).tirage(getMod(Caracteristique.DEX), this)) {
      defIa.notifier(nom + ", tu es inconscient mon pauvre, je vais te tuer!");
      notifier("Vous avez réussi à franchir  la barrière de " + pnjDef.getNom() + "!");
      intArt.onEnter(axeX + mx, axeY + my, morceauEtage.tiles(axeX + mx, axeY + my));
      return true;
    }
    
    return false;
  }

  public void etudierPorte(String s) {
    Ouvrable ouvrable = morceauEtage.ouvrables(axeX, axeY);
    if(ouvrable != null) {
      if(ouvrable instanceof Porte) {
        if(!ouvrable.isOuverte()) {
          switch(s) {
          case "porte":
            if(ouvrable.getDiffValeur() <= (20 + getMod(Caracteristique.SAG))) {
              if(ouvrable.tirage(getMod(Caracteristique.SAG))) {
                notifier(((Porte) ouvrable).ecouter(morceauEtage));
              } else {
                notifier("Cette porte est " + ouvrable.getDifficulte() + " à étudier.");
              }
            } else {
              notifier("Inutile d'essayer, votre sagesse ne suffit pas pour écouter à travers la porte!");
            }
            break;
          case "salle":
            notifier("La porte est fermée, impossible d'observer les alentours !");
            break;
          }
        } else {
          switch(s) {
          case "porte":
            notifier("La porte est ouverte, inutile d'écouter à travers celle-ci !");
            break;
          case "salle":
            notifier(((Porte) ouvrable).etudier(morceauEtage));
            break;
          }
        }
      } else {
        notifier("Vous ne pouvez pas étudier de " + s + " depuis votre "
            + "position, uniquement un coffre.");
        notifier("Celui-ci, contient peut-être un piège.");
      }
    } else {
      notifier("Vous êtes au milieu de la salle, vous ne pouvez pas étudier de " 
      + s + " depuis votre position.");
    }
  }

  public void etudierCoffre(String s) {
    Ouvrable ouvrable = morceauEtage.ouvrables(axeX, axeY);
    if(ouvrable != null) {
      if(ouvrable instanceof Tresor) {
        if(!ouvrable.isOuverte()) {
            if(ouvrable.getDiffValeur() <= (20 + getMod(Caracteristique.INT))) {
              if(ouvrable.tirage(getMod(Caracteristique.INT))) {
                notifier(((Tresor) ouvrable).etudier(null));
              } else {
                notifier("Ce coffre est " + ouvrable.getDifficulte() + " à étudier.");
              }
            } else {
              notifier("Inutile d'essayer, votre intelligence ne suffit pas pour étudier ce coffre!");
            }
          } else {
          notifier("Ce trésor à déjà été ouvert, inutile de l'étudier.");
          }
      } else {
        if(ouvrable.isOuverte()) {
          notifier("Vous ne pouvez pas étudier de " + s + " depuis votre "
              + "position, uniquement une porte.");
          notifier("Peut-être qu'elle cache une embuscade.");
        } else {
          notifier("Vous devriez étudier la salle afin de savoir si une "
              + "embuscade vous y attend...");
        }
      }
    } else {
      notifier("Il n'y a aucun coffre ici.");
    }
  }
  
  public Arme getArme1() {
    return arme[0];
  }
  
  public void setArme(Arme arme) {
    
    if(arme == null) {
      throw new IllegalArgumentException("Vous devez donner une valeur non-nulle."
          + " Si vous souhaitez retirer une arme, utiliser la méthode 'desequipper(Echangeable e)'");
    }
    
    if(arme.isDeuxMains()) {
      if(bouclier != null) {
        desequipper(bouclier);
      }
      if(this.arme[0] != null) {
        desequipper(this.arme[0]);
      }
      if(this.arme[1] != null) {
        desequipper(this.arme[1]);
      }
    } else {
      if (this.arme[1] != null && this.arme[1].isDeuxMains()) {
        desequipper(this.arme[1]);
      } else if(this.arme[0] != null && this.arme[0].isDeuxMains()) {
        desequipper(this.arme[1]);
      } else if (this.bouclier != null && this.arme[1] != null) {
        desequipper(this.arme[1]);
      } else if (this.bouclier != null && this.arme[0] != null) {
        desequipper(this.arme[0]);
      }
    }
     
    if(this.arme[1] == null) {
      this.arme[1] = arme;
    } else if(this.arme[0] == null) {
      this.arme[0] = arme;
    }
    notifier("Désormais, vous portez " + arme.getNom() + ".");
  }
  
  public Arme getArme2() {
    return arme[1];
  }
  
  public Bouclier getBouclier() {
    return bouclier;
  }
  
  public void setBouclier(Bouclier bouclier) {
    
    if(bouclier == null) {
      throw new IllegalArgumentException("Vous devez donner une valeur non-nulle."
          + " Si vous souhaitez retirer un bouclier, utiliser la méthode 'desequipper(Echangeable e)'");
    }
    
    if(this.bouclier != null) {
      desequipper(this.bouclier);
    }
    
    if(arme[0] != null && arme[0].isDeuxMains()) {
      desequipper(arme[0]);
    } else if(arme[1] != null && arme[1].isDeuxMains()) {
      desequipper(arme[1]);
    } else if(arme[1] != null && arme[0] != null) {
      desequipper(this.arme[1]);
    }
    
    this.bouclier = bouclier;
    notifier("Désormais, vous portez " + bouclier.getNom() + ".");
  }
  
  public ArmureDeCorps getArmureDeCorps() {
    return armureDeCorps;
  }
  
  public void setArmureDeCorps(ArmureDeCorps armure) {
    
    if(armure == null) {
      throw new IllegalArgumentException("Vous devez donner une valeur non-nulle."
          + " Si vous souhaitez retirer un bouclier, utiliser la méthode 'desequipper(Echangeable e)'");
    }
    
    if(armureDeCorps != null) {
      desequipper(armureDeCorps);
    }
    
    this.armureDeCorps = armure;
    notifier("Désormais, vous portez " + armure.getNom() + ".");
  }
  
  public boolean desequipper(Equipement e) {
    
    if(e == null) {
      return false;
    }
    
    if(e.equals(bouclier)) {
      bouclier = null;
      notifier("Vous avez retiré " + e.getNom() + ".");
      notifier("Vous ne portez plus de bouclier.");
      return true;
    }
    
    if(e.equals(armureDeCorps)) {
      armureDeCorps = null;
      notifier("Vous avez retiré " + e.getNom() + ".");
      notifier("Vous ne portez plus d'armure de corps.");
      return true;
    }
    
    if(e.equals(arme[0])) {
      arme[0] = null;
      notifier("Vous avez retiré " + e.getNom() + ".");
      if(arme[1] == null && arme[0] == null) {
        notifier("Vous ne portez plus d'armes.");
      }
      return true;
    }
    
    if(e.equals(arme[1])) {
      arme[1] = null;
      notifier("Vous avez retiré " + e.getNom() + ".");
      if(arme[1] == null && arme[0] == null) {
        notifier("Vous ne portez plus d'armes.");
      }
      return true;
    }
    
    return false;
  }
  
  

  public void desequiper(int i) {
    
    switch(i) {
    case 1:
      if(arme[0] != null) {
        desequipper(arme[0]);
      } else {
        notifier("Votre main gauche est déjà libre.");
      }
      break;
    case 2:
      if(arme[1] != null) {
        desequipper(arme[1]);
      } else {
        notifier("Votre main droite est déjà libre.");
      }
      break;
    case 3:
      if(bouclier != null) {
        desequipper(bouclier);
      } else {
        notifier("Vous ne pouvez pas retirer de bouclier car vous n'en porter aucun.");
      }
      break;
    case 4:
      if(armureDeCorps != null) {
        desequipper(armureDeCorps);
      } else {
        notifier("Vous ne pouvez pas retirer d'armure de corps car vous n'en porter aucune.");
      }
    default:
      break;
    }
  }

  public void equiper(int i) {

    if( i > sac.quantite()) {
      notifier("Il n'y aucun équipement à l'emplacement " + i + " dans votre sac.");
    }
    
    Equipement e = sac.get(i - 1);
    
    if(e instanceof ArmureDeCorps) {
      setArmureDeCorps((ArmureDeCorps) e);
    } else if(e instanceof Arme) {
      setArme((Arme) e);
    } else if(e instanceof Bouclier) {
      setBouclier((Bouclier) e);
    }
    
  }

  public boolean porte(Equipement e) {

     if(e instanceof Arme) {
       return getArme1() == e || getArme2() == e;
     } else if (e instanceof Bouclier) {
       return e == getBouclier();
     } else if (e instanceof ArmureDeCorps) {
       return e == getArmureDeCorps();
     }
     
     return false;
  }
  
  public Jauge getPointsDeVie() {
    return pointsDeVie;
  }
  
  public int getDefense() {
    
    int defense = defenseBase;
    
    if(armureDeCorps != null) {
      defense += armureDeCorps.getBonusDef();
    }
    
    if(bouclier != null) {
      defense += bouclier.getBonusDef();
    }
    
    return defense;
  }

  public boolean attaquer(String s) {
    for(PersonnageDonjon p : morceauEtage.getPersonnages()) {
      if(p.getNom().toLowerCase().equals(s)) {
          attaquer(p);
          return true;
      }
    }
    String initiale = s.substring(0,1).toUpperCase();
    String reste = s.substring(1,s.length()).toLowerCase();
    s = initiale + reste;
    notifier("Il n'y a aucun " + s + " dans cette salle.");
    return false;
  }
}

