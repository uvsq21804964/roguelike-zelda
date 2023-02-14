package fr.uvsq.pglp.roguelike.donjon;

import asciiPanel.AsciiPanel;

import java.awt.*;
import java.io.Serializable;

/**
 * Cette classe représente les <code>Rubis</code>
 * (= la monnaie du jeu).
 * On peut les utiliser pour acheter des items à des marchands,
 * ou en recevoir si on lui en vend.
 * Les ennemis laissent, parfois, des rubis quand on les tue.
 * On peut trouver des rubis dans les caisses 'X'.
 *
 * @author Tom Abbouz
 * @version janvier 2023
 */
public class Rubis implements Serializable {
    private static final long serialVersionUID = -6291473801270860737L;

    private final char glyph;
    private int value;
    private final Color color;
    private final String name;

    public Rubis(Color color) {
        this.glyph = '$';
        this.color = color;
        this.value = 1;
        if (color == AsciiPanel.blue) {
            this.value = 5;
        } else if (color == AsciiPanel.yellow) {
            this.value = 10;
        } else if (color == AsciiPanel.red) {
            this.value = 20;
        } else if (color == AsciiPanel.brightMagenta) {
            this.value = 50;
        } else if (color == AsciiPanel.brightCyan) {
            this.value = 100;
        } else if (color == AsciiPanel.brightYellow) {
            this.value = 300;
        }
        this.name = "Rubis";
    }

    public char glyph() {
        return glyph;
    }

    public int value() {
        return value;
    }

    public Color color() {
        return color;
    }

    public String name() {
        return name;
    }
}