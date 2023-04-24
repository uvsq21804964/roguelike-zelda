package fr.uvsq.pglp.roguelike.personnage.attributs;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class CaracteristiqueTest {

    @Test
    public void testGetNom() {
        assertEquals("Charisme", Caracteristique.CHA.getNom());
        assertEquals("Constitution", Caracteristique.CON.getNom());
        assertEquals("Dexterite", Caracteristique.DEX.getNom());
        assertEquals("Force", Caracteristique.FOR.getNom());
        assertEquals("Intelligence", Caracteristique.INT.getNom());
        assertEquals("Sagesse", Caracteristique.SAG.getNom());
    }

    @Test
    public void testRandomOrder() {
        Caracteristique[] c = Caracteristique.randomOrder();

        // Vérifie que le tableau contient toutes les caractéristiques
        Set<Caracteristique> expected = new HashSet<>(Arrays.asList(Caracteristique.values()));
        Set<Caracteristique> actual = new HashSet<>(Arrays.asList(c));
        assertEquals(expected, actual);

        // Vérifie que le tableau est différent de l'ordre initial
        assertFalse(Arrays.equals(Caracteristique.values(), c));
    }
}
