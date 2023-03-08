package fr.uvsq.pglp.roguelike.echangeable;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BouclierTest {

    @Test
    void testRandom() {
        Bouclier bouclier = Bouclier.random(11);
        assertNotNull(bouclier);
        assertTrue(bouclier.getPrix() < 11);
    }
}