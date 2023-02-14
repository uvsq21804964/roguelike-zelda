package fr.uvsq.pglp.roguelike.donjon;

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
    FLOOR((char) 250, new  Color(128,128,0)),
    WALL((char) 177, new  Color(128,128,0)),
    BOUNDS('x', new  Color (128,128,128)),
    UNKNOWN(' ', new  Color(192,192,192)),
    BOX('X', new Color(17, 200, 38)),
    P('P', new  Color(128,0,0));

    private final char glyph;
    private final Color color;

    Tile(char glyph, Color color) {
        this.glyph = glyph;
        this.color = color;
    }

    public char glyph() {
        return glyph;
    }

    public Color color() {
        return color;
    }

    public boolean isGround() {
        return this != WALL && this != BOUNDS;
    }
}