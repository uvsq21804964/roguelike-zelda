package fr.uvsq.pglp.roguelike.echangeable;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeTest {

    @Test
    public void testTirageSimple() {
        De de = new De(6);
        int result = de.tirage();
        assertTrue(result >= 1 && result <= 6);
    }

    @Test
    public void testTirageMultiple() {
        De de = new De(2, 6);
        int sommeTirage = de.tirage();
        assertTrue(sommeTirage >= 2 && sommeTirage <= 12);
    }

}