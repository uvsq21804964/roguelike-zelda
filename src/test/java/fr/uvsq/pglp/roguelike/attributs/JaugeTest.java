package fr.uvsq.pglp.roguelike.personnage.attributs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JaugeTest {

  private Jauge jauge;

  @BeforeEach
  public void setUp() {
    jauge = new Jauge(50, 100);
  }

  @Test
  public void testInitialisation() {
    assertEquals(50, jauge.valeur());
    assertEquals(100, jauge.valeurMax());
    assertFalse(jauge.pleine());
    assertFalse(jauge.vide());
    assertEquals(0.5, jauge.taux(), 0.01);
  }

  @Test
  public void testModifier() {
    int gain = jauge.modifier(30);
    assertEquals(80, jauge.valeur());
    assertEquals(30, gain);
    assertFalse(jauge.pleine());
    assertFalse(jauge.vide());
    assertEquals(0.8, jauge.taux(), 0.01);

    gain = jauge.modifier(-20);
    assertEquals(60, jauge.valeur());
    assertEquals(-20, gain);
    assertFalse(jauge.pleine());
    assertFalse(jauge.vide());
    assertEquals(0.6, jauge.taux(), 0.01);

    gain = jauge.modifier(60);
    assertEquals(100, jauge.valeur());
    assertEquals(40, gain);
    assertTrue(jauge.pleine());
    assertFalse(jauge.vide());
    assertEquals(1.0, jauge.taux(), 0.01);

    gain = jauge.modifier(-110);
    assertEquals(0, jauge.valeur());
    assertEquals(-60, gain);
    assertFalse(jauge.pleine());
    assertTrue(jauge.vide());
    assertEquals(0.0, jauge.taux(), 0.01);
  }

  @Test
  public void testAjoutable() {
    assertTrue(jauge.ajoutable(20));
    assertFalse(jauge.ajoutable(60));
    assertTrue(jauge.ajoutable(-50));
    assertFalse(jauge.ajoutable(-60));
    assertFalse(jauge.ajoutable(200));
    assertFalse(jauge.ajoutable(-200));
  }

  @Test
  public void testFixerTaux() {
    int difference = jauge.fixerTaux(0.75f);
    assertEquals(25, difference);
    assertTrue(jauge.ajoutable(difference));

    difference = jauge.fixerTaux(0.25f);
    assertEquals(-25, difference);
    assertTrue(jauge.ajoutable(difference));
  }

}
