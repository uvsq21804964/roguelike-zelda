package fr.uvsq.pglp.roguelike.echangeable;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ArmeContactTest {

    @Test
    void testGetNom() {
        assertEquals("hache", ArmeContact.HACHE.getNom());
    }

    @Test
    void testGetTirage() {
        assertTrue(ArmeContact.HACHE.getTirage() >= 1 && ArmeContact.HACHE.getTirage() <= 8);
    }

    @Test
    void testIsDeuxMains() {
        assertFalse(ArmeContact.HACHE.isDeuxMains());
        assertTrue(ArmeContact.VIVELAME.isDeuxMains());
    }

    @Test
    void testGetPrix() {
        assertEquals(0, ArmeContact.HACHE.getPrix());
    }

    @Test
    void testGetGlyph() {
        assertEquals(':', ArmeContact.RAPIERE.getGlyph());
    }

    @Test
    void testRandom() {
        for (int i = 0; i < 5; i++) {
            ArmeContact armeContact = ArmeContact.random(6);
            assertTrue(armeContact.getPrix() < 6);
        }
    }
}