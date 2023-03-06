package fr.uvsq.pglp.roguelike.ihm;

@FunctionalInterface
public interface Command {
    public void apply(String s);
}
