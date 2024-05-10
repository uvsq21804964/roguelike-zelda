package fr.uvsq.pglp.roguelike.echangeable;

// import fr.uvsq.pglp.roguelike.interfaces.Arme;
// import fr.uvsq.pglp.roguelike.interfaces.Equipement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static fr.uvsq.pglp.roguelike.echangeable.ArmeRechargementSimple.ARBALETEPOING;

public class ArmeRechargementSimpleTest {

    @Test
    public void testRandom() {
      
      ArmeRechargementSimple arme = ArmeRechargementSimple.random(20);
      assertNotNull(arme);
      assertTrue(arme.getPrix() < 20);
    
      
      arme = ArmeRechargementSimple.random(1000);
      assertNotNull(arme);
      assertTrue(arme.getPrix() < 1000);
    
       
      arme = ArmeRechargementSimple.random(1);
      assertEquals(ARBALETEPOING, arme);
    }
    

  @Test
  public void testGet() {
    ArmeRechargementSimple arme = ArmeRechargementSimple.ARBALETEPOING;

    assertEquals("une arbalète de poing", arme.getNom());
    assertEquals(6, arme.getTirageMax());
    assertFalse(arme.isDeuxMains());
    assertEquals(10.0, arme.getPortee());
    assertEquals(8, arme.getPrix());
    assertEquals('!', arme.getGlyph());
  }
}
