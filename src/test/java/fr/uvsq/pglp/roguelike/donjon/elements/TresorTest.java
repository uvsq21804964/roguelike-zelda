package fr.uvsq.pglp.roguelike.donjon.elements;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TresorTest {
  
 @Test
  void testType() {
    Tresor t = new Tresor(Tile.BOX);
    assertEquals(Tile.BOX, t.getType());
  }

  
  @Test
  void testOuvrir() {
       Tresor t = new Tresor(Tile.BOX);
       t.setDifficulte(new Difficulte(20, "Facile"));
       t.setEchangeable(new Pieces(10));
       PersonnageDonjon p = new PersonnageDonjon("test");
       t.ouvrir(p);
       assertEquals(10, p.getInventaire().getPieces());
     }
  
  @Test
  void testOuvrirBombe() {
       Tresor t = new Tresor(Tile.BOX);
       t.setDifficulte(new Difficulte(20, "Facile"));
       t.setEchangeable(new Pieces(10));
       t.setBombe(new Bombe(10));
       PersonnageDonjon p = new PersonnageDonjon("test");
       t.ouvrir(p);
       assertEquals(90, p.getPv());
     }
}
