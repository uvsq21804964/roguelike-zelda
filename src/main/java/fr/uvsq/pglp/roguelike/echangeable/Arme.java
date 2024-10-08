package fr.uvsq.pglp.roguelike.echangeable;

/**
 * Encapsulation de toutes les armes, qui sont des {@link Equipement}s.
 *
 * <p> Les armes peuvent demandées deux ou une seule main de disponible,
 * et nécessite le lancement d'un {@link De} ou plusieurs, pour attaquer avec.</p>
 *
 * <p> Toutes les armes et uniquement les armes ont un glyph contenant un point ".".
 * Par exemple : '.', '?', ':', etc.</p>
 *
 * @author Tom Abbouz
 * @version Février 2023
 * @see ArmeContact
 * @see ArmeDistance
 * @see Equipement
 */
public interface Arme extends Equipement {
  
  public int getTirageMax();
  
  public int getTirage();

  public boolean isDeuxMains();
}
