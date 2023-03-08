package fr.uvsq.pglp.roguelike.echangeable;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PiecesTest {

    @Test
    public void testGetGlyph() {
        Pieces pieces = new Pieces(10);
        assertEquals('$', pieces.getGlyph());
    }

    @Test
    public void testGetNom() {
        Pieces pieces = new Pieces(10);
        assertEquals("10 pièces d'argent", pieces.getNom());
    }

    @Test
    public void testGetValeur() {
        Pieces pieces = new Pieces(10);
        assertEquals(10, pieces.getValeur());
    }

}

