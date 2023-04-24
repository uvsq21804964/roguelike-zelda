package fr.uvsq.pglp.roguelike.personnage.attributs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ScoreDeCaracteristiqueTest {

    @Test
    void ScoreDeCaracteristiqueTest() {
        Caracteristique force = Caracteristique.FOR;
        ScoreDeCaracteristique scoreForce = new ScoreDeCaracteristique(force, 18);
        Assertions.assertEquals(force, scoreForce.type());
        Assertions.assertEquals("Force", scoreForce.nom());
        Assertions.assertEquals(18, scoreForce.val());
        Assertions.assertEquals(4, scoreForce.mod());
    }

    @Test
    void ScoreDeCaracteristiqueInvalideTest() {
        Caracteristique intelligence = Caracteristique.INT;
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new ScoreDeCaracteristique(intelligence, 0));
    }
}
