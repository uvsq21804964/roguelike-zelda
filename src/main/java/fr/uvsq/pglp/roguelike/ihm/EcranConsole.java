package fr.uvsq.pglp.roguelike.ihm;

import java.awt.Color;

public interface EcranConsole {
    
    public void println(String s);
    
    public void print(String s);

    public void sauts(int n);

    public void clear();

    public void print(String a, Color c);
    
    public void println(String s, Color c);
}
