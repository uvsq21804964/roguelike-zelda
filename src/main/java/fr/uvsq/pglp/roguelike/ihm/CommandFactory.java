package fr.uvsq.pglp.roguelike.ihm;

import java.util.HashMap;
import java.util.Map;

/**
 * Représentation de l'ensemble des commandes possibles de {@link ConsoleTexte}.
 *
 * <p>Utilisation du pattern <b>Command</b>.
 *
 * @see Command
 *
 * @author Tom Abbouz
 * @version Mars 2023
 */
public class CommandFactory {

  private static Console console;
  private static Facade facade;
  private final Map<String, Command> commands;

  private CommandFactory(Console console) {
    this.commands = new HashMap<>();
    CommandFactory.console = console;
  }

  /**
   * Initialisation des commandes possibles.
   *
   * @param console {@link Console} depuis laquelle on reçoit les commandes.
   * @return une instance de {@link CommandFactory} avec de nouvelles {@link Command}s.
   */
  public static CommandFactory init(Console console) {
    CommandFactory cf = new CommandFactory(console);

    cf.addCommand("déplacer", (String s) -> {
      deplacer(s);
    });
    cf.addCommand("sauter", (String s) -> {
      sauter(s);
    });
    cf.addCommand("franchir", (String s) -> {
      franchir(s);
    });

    cf.addCommand("ramasser", (String s) -> {
      ramasser(s);
    });

    cf.addCommand("parler", (String s) -> {
      parler(s);
    });
    cf.addCommand("soutirer", (String s) -> {
      soutirer(s);
    });
    cf.addCommand("convaincre", (String s) -> {
      convaincre(s);
    });
    cf.addCommand("acheter", (String s) -> {
      acheter(s);
    });
    cf.addCommand("attaquer", (String s) -> {
      attaquer(s);
    });

    cf.addCommand("ouvrir", (String s) -> {
      ouvrir(s);
    });
    cf.addCommand("crocheter", (String s) -> {
      crocheter(s);
    });
    cf.addCommand("forcer", (String s) -> {
      forcer(s);
    });

    cf.addCommand("étudier", (String s) -> {
      etudier(s);
    });

    cf.addCommand("liste", (String s) -> {
      liste(s);
    });
    
    cf.addCommand("équiper", (String s) -> {
      equiper(s);
    });
    
    cf.addCommand("déséquiper", (String s) -> {
      desequiper(s);
    });

    return cf;
  }

  private static void commandePartiellementInexistante(String s, String action) {
    s = action + " " + s;
    String[] commands = s.split(" ");
    console.commandePartiellementInexistante(commands);
  }

  private static void personnageInexistant(String s, String action) {
    s = action + " " + s;
    String[] commands = s.split(" ");
    console.personnageInexistant(commands);
  }

  private static void message(String s) {
    console.message(s);
  }

  private static void deplacer(String s) {
    if (s.equals("droite") || s.equals("gauche")
            || s.equals("haut") || s.equals("bas")) {
      facade.deplacer(s);
    } else {
      commandePartiellementInexistante(s, "déplacer");
    }
  }

  private static void sauter(String s) {
    if (s.equals("droite") || s.equals("gauche")
            || s.equals("haut") || s.equals("bas")) {
      facade.sauter(s);
    } else {
      commandePartiellementInexistante(s, "sauter");
    }
  }

  private static void franchir(String s) {
    if (s.equals("droite") || s.equals("gauche")
            || s.equals("haut") || s.equals("bas")) {
      facade.franchir(s);
    } else {
      commandePartiellementInexistante(s, "franchir");
    }
  }

  private static void ramasser(String s) {
    if (s.equals("pièces") || s.equals("arme")
            || s.equals("armure") || s.equals("bouclier")
            || s.equals("équipement") || s.equals("tout")
            || s.equals("pièce")) {
      facade.ramasser(s);
    } else {
      commandePartiellementInexistante(s, "ramasser");
    }
  }

  private static void parler(String s) {
    if (!facade.parler(s)) {
      personnageInexistant(s, "parler");
    }
  }

  private static void soutirer(String s) {
    if (!facade.soutirer(s)) {
      personnageInexistant(s, "soutirer");
    }
  }

  private static void convaincre(String s) {
    if (!facade.convaincre(s)) {
      personnageInexistant(s, "convaincre");
    }
  }
  
  private static void acheter(String s) {
    if (!facade.acheter(s)) {
      personnageInexistant(s, "acheter");
    }
  }

  private static void attaquer(String s) {
    if (!facade.attaquer(s)) {
      personnageInexistant(s, "attaquer");
    }
  }

  private static void ouvrir(String s) {
    if (s.equals("porte") || s.equals("coffre")) {
      facade.ouvrir(s);
    } else {
      commandePartiellementInexistante(s, "ouvrir");
    }
  }

  private static void crocheter(String s) {
    if (s.equals("porte") || s.equals("coffre")) {
      facade.crocheter(s);
    } else {
      commandePartiellementInexistante(s, "crocheter");
    }
  }

  private static void forcer(String s) {
    if (s.equals("porte") || s.equals("coffre")) {
      facade.forcer(s);
    } else {
      commandePartiellementInexistante(s, "forcer");
    }
  }

  private static void etudier(String s) {
    if (s.equals("porte") || s.equals("salle") || s.equals("coffre")) {
      facade.etudier(s);
    } else {
      commandePartiellementInexistante(s, "étudier");
    }
  }

  private static void liste(String s) {
    if (s.equals("équipements") || s.equals("sac")
            || s.equals("commandes")) {
      console.println(s);
    } else {
      commandePartiellementInexistante(s, "liste");
    }
  }
  
  private static void equiper(String s) {
    int i = Integer.valueOf(s);
    if(i >= 1 && i <= 12)  {
      facade.equiper(i);
    } else {
      commandePartiellementInexistante(s, "déséquiper");
    }
  }
  
  private static void desequiper(String s) {
    int i = Integer.valueOf(s);
    if(i >= 1 && i <= 4)  {
      facade.desequiper(i);
    } else {
      commandePartiellementInexistante(s, "déséquiper");
    }
  }

  public void addCommand(String name, Command command) {
    this.commands.put(name, command);
  }

  /**
   * Exécute une {@link Command} si elle existe.
   *
   * @param command1 Action de la commande de l'utilisateur.
   * @param command2 Objet de la commande de l'utilisateur.
   */
  public void executeCommand(String command1, String command2) {
    if (this.commands.containsKey(command1)) {
      this.commands.get(command1).apply(command2);
    }
  }

  /**
   * Teste si la {@link CommandFactory} contient la commande en entrée.
   *
   * @param name Action de la commande de l'utilisateur
   * @return <b>true</b> seulement si la commande en entrée a été implémentée.
   */
  public boolean containCommand(String name) {
    if (facade == null) {
      CommandFactory.facade = new Facade(console);
    }
    if (this.commands.containsKey(name)) {
      return true;
    }
    return false;
  }
}
