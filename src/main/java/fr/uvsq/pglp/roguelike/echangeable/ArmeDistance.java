package fr.uvsq.pglp.roguelike.echangeable;


/**
 * Encapsulation de toutes les armes à distance.
 *
 * <p> Les armes à distance possèdent une certaine portée et
 * doivent être rechargées.</p>
 *
 * <p> Il en exsite trois types : les {@link ArmeRechargementLimite}s sont reconnaissables
 * par un ';' ; {@link ArmeRechargementSimple}s sont reconnaissables par un '!' et les
 * {@link ArmeSansRechargement}s sont reconnaissables par un '?'.</p>
 *
 * @author Tom Abbouz
 * @version Février 2023
 * @see Arme
 * @see ArmeRechargementLimite
 * @see ArmeRechargementSimple
 * @see ArmeSansRechargement
 */
public interface ArmeDistance extends Arme {

  public double getPortee();

  public int recharger();
}

