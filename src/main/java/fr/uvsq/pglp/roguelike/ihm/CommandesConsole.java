package fr.uvsq.pglp.roguelike.ihm;

public interface CommandesConsole {
    
    void actionInexistante(String[] commands);

    void commandePartiellementInexistante(String[] commands);

    void mauvaisNombreMots(String[] commands);

    void personnageInexistant(String[] commands);
    
    void message(String s);
    
    public void doCommand(String s);
}
