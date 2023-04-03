package fr.uvsq.pglp.roguelike.attributs;
import static org.junit.jupiter.api.Assertions.*;
import fr.uvsq.pglp.roguelike.personnage.attributs.DeDeVie;
import org.junit.jupiter.api.Test;

public class DeDeVieTest {

    @Test
    public void testTirage() {
        DeDeVie deDeVie = new DeDeVie(6);
        int resultat = deDeVie.tirage();
        assertTrue(resultat >= 1 && resultat <= 6);
    }

    @Test
    public void testValeurMax() {
        DeDeVie deDeVie = new DeDeVie(10);
        assertEquals(10, deDeVie.valeurMax());
    }
}
