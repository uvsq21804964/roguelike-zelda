package fr.uvsq.pglp.roguelike.donjon.elements;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PorteTest {

     private Salle salle1;
     private Salle salle2;
     private Couloir couloir;
     private Porte porte;

    @BeforeEach
    public void creationTest() {
        salle1 = new SalleFixe(3);
        salle2 = new SalleFixe(4);

        couloir = new CouloirSimple(salle1, salle2,3,4);
        porte = new Porte(Tile.PORTE, salle1, couloir);
    }

    @Test
    public void testGetType() {
        Assertions.assertEquals(Tile.PORTE, porte.getType());
    }

    @Test
    public void testGetSalle() {
        Assertions.assertEquals(salle1, porte.getSalle());
    }

    @Test
    public void testGetCouloir() {
        Assertions.assertEquals(couloir, porte.getCouloir());
    }

    @Test
    public void testIsOuverte() {
        Assertions.assertFalse(porte.isOuverte());
    }
  
}