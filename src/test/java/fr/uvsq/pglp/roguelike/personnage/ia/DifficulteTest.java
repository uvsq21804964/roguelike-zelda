package fr.uvsq.pglp.roguelike.personnage.ia;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DifficulteTest {
  
  @Test
  public void testGetString() {
    Difficulte d = Difficulte.FACILE;
    assertEquals("facile", d.getString());
  }

  @Test
  public void testRandom() {
    Difficulte d = Difficulte.random();
    assertNotNull(d);
  }


  @Test
  public void testGetValeur() {
    Difficulte d = Difficulte.DIFFICILE;
    assertEquals(15, d.getValeur());
  }

  @Test
  public void testTirageFalse() {
    Difficulte d = Difficulte.EXTDIFF;
    assertFalse(d.tirage(-10));
  }

  @Test
  public void testTirageTrue() {
    Difficulte d = Difficulte.MOYEN;
    assertTrue(d.tirage(5));
  }


}

    