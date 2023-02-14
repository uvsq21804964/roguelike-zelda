package fr.uvsq.pglp.roguelike.donjon;

import asciiPanel.AsciiPanel;

import java.awt.*;

/**
 * Cette classe représente les différents types de tuiles
 * étant affichées : un glyphe et une couleur pour l'afficher.
 * Les tuiles sont représentées sous la forme d'une énumération
 * car leur variété est en nombre très limité.
 *
 * @author Tom Abbouz
 * @version janvier 2023
 */
public enum Tile {
    FLOOR((char) 250, AsciiPanel.yellow, "Un sol en marbre digne des architectes hyruliens de jadis."),
    WALL((char) 177, AsciiPanel.yellow, "Un mur aux motifs anciens dont le sens est cryptes."),
    BOUNDS('x', AsciiPanel.brightBlack, "Au-dela du temple, un autre monde."),
    UNKNOWN(' ', AsciiPanel.white, "Encore inconnu, mais rien ne vous empeche de decouvrir les lieux..."),
    BOX('X', new Color(17, 200, 38), "Une caisse contenant surement des rubis ou des equipements."),
    P('P', AsciiPanel.red, "Une porte bien mystérieuse.");

    private final char glyph;
    private final Color color;
    private final String details;

    Tile(char glyph, Color color, String details) {
        this.glyph = glyph;
        this.color = color;
        this.details = details;
    }

    public char glyph() {
        return glyph;
    }

    public Color color() {
        return color;
    }

    public String details() {
        return details;
    }

    public boolean isGround() {
        return this != WALL && this != BOUNDS;
    }
}