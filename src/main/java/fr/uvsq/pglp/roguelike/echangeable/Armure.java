package fr.uvsq.pglp.roguelike.echangeable;

/**
 * Encapsulation de toutes les armures, qui sont des {@link Equipement}s.
 *
 * <p> Les armures peuvent permettent au joueur de gagner un bonus de défense.</p>
 *
 * <p> il en existe deux types : {@link ArmureDeCorps} (reconnaissable par un
 * '/') et {@link Bouclier} (reconnaissable par une ',').</p>
 *
 * @author Tom Abbouz
 * @version Février 2023
 * @see ArmureDeCorps
 * @see Bouclier
 * @see Equipement
 */
public interface Armure extends Equipement {

  public int getBonusDef();
}
