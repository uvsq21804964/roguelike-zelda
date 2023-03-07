package fr.uvsq.pglp.roguelike.ihm;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import fr.uvsq.pglp.roguelike.ihm.screen.PlayScreen;
import fr.uvsq.pglp.roguelike.ihm.screen.Screen;
import fr.uvsq.pglp.roguelike.ihm.screen.StartScreen;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;

public class ConsoleTexte implements Console {
    
    public JFrame frame;
    public JTextPane console;
    public JTextField input;
    public JScrollPane scrollpane;

    private Screen screen = new StartScreen();

    public StyledDocument document;

    private CommandFactory cf = CommandFactory.init(this); 

    ArrayList<String> recent_used = new ArrayList<String>();
    int recent_used_id = 0;
    int recent_used_maximum = 1000;

    boolean loop = false;
    int loop_times = 1;
    int loop_times_temp = 1;

    public ConsoleTexte() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        frame = new JFrame("Roguelike PGLP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        console = new JTextPane();
        console.setEditable(false);
        console.setFont(new Font("Courier New", Font.PLAIN, 12));
        console.setOpaque(false);

        document = console.getStyledDocument();

        input = new JTextField();
        input.setEditable(true);
        input.setFont(new Font("Courier New", Font.PLAIN, 12));
        input.setForeground(Color.WHITE);
        input.setCaretColor(Color.GREEN);
        input.setOpaque(false);

        input.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                String text = input.getText();

                if (text.length() > 1) {

                    recent_used.add(text);
                    recent_used_id = 0;

                    doCommand(text);
                    scrollBottom();
                    input.selectAll();
                }

            }
        });

        input.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (recent_used_id < (recent_used.size() - 1) && recent_used_id < (recent_used_maximum - 1)) {
                        recent_used_id++;
                    }

                    String select = recent_used.get(recent_used.size() - recent_used_id - 1);

                    if(recent_used.size() - recent_used_id - 2 > 0) {
                        if(recent_used.get(recent_used.size() - recent_used_id - 2).equals(select)) {
                            if (recent_used_id < (recent_used.size() - 1) && recent_used_id < (recent_used_maximum - 1)) {
                                keyPressed(e);
                            }
                        }
                    }

                    input.setText(select);

                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (recent_used_id > 0) {
                        recent_used_id--;
                    }

                    

                    String select = recent_used.get(recent_used.size() - recent_used_id - 1);

                    if(recent_used.size() - recent_used_id < recent_used.size()) {
                        if(recent_used.get(recent_used.size() - recent_used_id).equals(select)) {
                            if (recent_used_id > 0) {
                                keyPressed(e);
                            }
                        }
                    }

                    input.setText(select);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
            
            @Override
            public void keyTyped(KeyEvent e) {}
        });

        scrollpane = new JScrollPane(console);
        scrollpane.setBorder(null);
        scrollpane.setOpaque(false);
        scrollpane.getViewport().setOpaque(false);

        frame.add(input, BorderLayout.SOUTH);
        frame.add(scrollpane, BorderLayout.CENTER);

        frame.getContentPane().setBackground(new Color(50, 50, 50));

        frame.setSize(660, 350);
        frame.setLocationRelativeTo(null);

        frame.setResizable(false);
        frame.setVisible(true);

        screen.displayOutput(this);
    }

    @Override 
    public void doCommand(String s) {

        final String[] commands = s.split(" ");

        for (int i = 0 ; i < loop_times ; i ++) {
            try {

                if (screen.commande(s)) {
                    screen = screen.autreScreen(s);
                } else if(screen instanceof PlayScreen) {
                      if (commands.length != 2) {
                          mauvaisNombreMots(commands);
                      } else {
      
                          if (cf.containCommand(commands[0].toLowerCase())) {
                              cf.executeCommand(commands[0].toLowerCase(), commands[1].toLowerCase());
                          } else if(commands[0].equalsIgnoreCase("loop")) {
      
                              boolean isNumeric = true;
                              for (int j = 0; j < commands[1].length(); j++) {
                                  if (!Character.isDigit(commands[1].charAt(j))) {
                                      isNumeric = false;
                                  }
                              }
      
                              if(!isNumeric) {
                                  actionInexistante(commands);
                              } else {
                                  loop_times_temp = Integer.parseInt(commands[1]);
                                  loop = true;
      
                                  message("La prochaine commande va être répétée " + loop_times_temp + " fois.");
                              }
      
                          } else {
                              actionInexistante(commands);
                          }
                      }
                  } else {
                    actionInexistante(commands);
                  }

            } catch (Exception ex) {
                println("Erreur -> " + ex.getMessage(), new Color(255,155,155));
            }
        }

        if (loop) {
            loop_times = loop_times_temp;
            loop_times_temp = 1;
        } else {
            loop_times = 1;
            loop_times_temp = 1;
        }

        clear();
        screen.displayOutput(this);
    }

    public void scrollTop() {
        console.setCaretPosition(0);
    }

    public void scrollBottom() {
        console.setCaretPosition(console.getDocument().getLength());
    }

    @Override
    public void print(String s) {
        print(s, new Color(255,255,255));
    }

    @Override
    public void print(String s, Color c) {
        
        Style style = console.addStyle("Style", null);
        StyleConstants.setForeground(style, c);

        try {
            document.insertString(document.getLength(), s, style);
        } catch (Exception e) {}
    }

    @Override
    public void println(String s) {
        println(s, Color.WHITE);
    }

    @Override
    public void println(String s, Color c) {
        print(s + "\n", c);
    }

    @Override
    public void sauts(int n) {
        for (int i = 0 ; i < n ; i++) {
            println("");
        }
    }

    @Override
    public void clear() {

        try {
            document.remove(0, document.getLength());
        } catch (Exception e) {}
    }

    @Override
    public void actionInexistante(String[] commands) {
        String message = "";

        for (int j = 0 ; j < commands.length ; j++) {
            message += commands[j];

            if(j != commands.length - 1) {
                message += " ";
            }
        }

        JOptionPane.showMessageDialog(null, "L'action ' '" + message + "' n'existe pas.\n"
        + "Vous pouvez utiliser la commande 'liste actions' pour voire toutes les actions possibles.", "Action inexistante", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void commandePartiellementInexistante(String[] commands) {
        JOptionPane.showMessageDialog(null, "L'action '" + commands[0] + "' existe, mais pas pour l'objet '" + commands[1] + "'.\n"
        + "Vous pouvez utiliser la commande 'liste "+ commands[0] +"' pour voire toutes les objets possibles pour cette action.", "Objet inexistant pour cette action", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void mauvaisNombreMots(String[] commands) {
        String message = "";

        for (int j = 0 ; j < commands.length ; j++) {
            message += commands[j];

            if(j != commands.length - 1) {
                message += " ";
            }
        }

        JOptionPane.showMessageDialog(null, "La commande '" + message + "' n'existe pas.\n"
        + "Une commande doit contient toujours deux mots, mais celle renseignée en contient " + commands.length + ".\n"
        + "Vous pouvez utiliser la commande 'liste actions' pour voire toutes les actions possibles.", "Commande avec mauvais nombre de mots", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void personnageInexistant(String[] commands) {
        JOptionPane.showMessageDialog(null, "Le personnage '" + commands[1] + "' n'existe pas"
        + " ou n'est pas a porté pour l'action '" + commands[0] + "'.\n"
        + "Référez-vous à la liste des personnages accesibles à la droite de l'écran du jeu.", "Personnage inaccessible", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void message(String s) {
        JOptionPane.showMessageDialog(null, s, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    public PlayScreen getScreen() {
      return (PlayScreen) screen;
    }
}

// Inspiré de https://www.youtube.com/watch?v=7pYw1WxDgzg&ab_channel=Vallentin


// if (commands[0].equalsIgnoreCase("clear")) {
//     clear();
// } else 
// if(commands[0].equalsIgnoreCase("popup")) {
//     String message = "";
//     for (int j = 0 ; j < commands.length ; j++) {
//         message += commands[j];
//         if(j != commands.length - 1) {
//             message += " ";
//         }
//     }
//     JOptionPane.showMessageDialog(null, "La commande '" + message + "' n'existe pas.\n"
//     + "Vous pouvez utiliser la commande 'liste commandes' pour voire toutes les commandes possibles.", "Commande inexistante", JOptionPane.INFORMATION_MESSAGE);
// } else if(commands[0].equalsIgnoreCase("loop")) {
//     loop_times_temp = Integer.parseInt(commands[1]);
//     loop = true;
//     println("La prochaine commande va être répétée " + loop_times_temp + " fois.", trace, new Color(155,155,255));
// } else {
//     println(s, trace, new Color(255,255,255));
// }