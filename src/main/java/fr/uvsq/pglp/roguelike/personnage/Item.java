package fr.uvsq.pglp.roguelike.personnage;

import java.awt.Color;
import java.io.Serializable;

public class Item {

	private final char glyph;
    private final int price;
    private final Color color;
    private final String name;
    
    private int attackValue;
    private int defenseValue;

    public Item(char glyph, Color color, String name, int price) {
        this.glyph = glyph;
        this.color = color;
        this.name = name;
        this.price = price;
        this.attackValue = 0;
        this.defenseValue = 0;
    }

    public int attackValue() {
        return attackValue;
    }

    public void modifyAttackValue(int amount) {
        attackValue += amount;
    }

    public int defenseValue() {
        return defenseValue;
    }

    public void modifyDefenseValue(int amount) {
        defenseValue += amount;
    }

    public int foodValue() {
        return foodValue;
    }

    public void modifyFoodValue(int amount) {
        foodValue += amount;
    }

    public int sleepValue() {
        return sleepValue;
    }

    public void modifySleepValue(int amount) {
        sleepValue += amount;
    }

    public char glyph() {
        return glyph;
    }

    public int guerisonValue() {
        return guerisonValue;
    }

    public void modifyGuerisonValue(int amount) {
        guerisonValue += amount;
    }

    public int price() {
        return price;
    }

    public Color color() {
        return color;
    }

    public String name() {
        return name;
    }
    
    private int rangedAttackValue;
    public int rangedAttackValue() { return rangedAttackValue; }
    public void modifyRangedAttackValue(int amount) { rangedAttackValue += amount; }
    
    public boolean vendable() {
        return foodValue == 0 && price > 0 && name != "Exaclibur" && name != "Clef du boss";
    }

    public boolean equippable() {
        return attackValue > 0 || defenseValue > 0;
    }

    public boolean mangeable() {
        return foodValue != 0 || guerisonValue != 0;
    }

    public String details() {
        String details = "";

        if (attackValue != 0) {
            details += "     Bonus d'attaque:" + attackValue;
        }
        if (defenseValue != 0) {
            details += "     Bonus de defense:" + defenseValue;
        }
        if (guerisonValue != 0) {
            details += "     Gain points de vie:" + guerisonValue;
        }
        if (foodValue != 0) {
            details += "     Gain points de faim:" + foodValue;
        }
        if (sleepValue != 0) {
            details += "     Gain points d'energie:" + sleepValue;
        }
        if (vendable()) {
            int prix = price / 5;
            details += "     Prix de vente :" + prix;
        }

        return details;
    }

    @Override
    public String toString() {

        String s = "attackValue : " + attackValue;
        s += " defenseValue : " + defenseValue;
        s += " foodValue : " + foodValue;
        s += " sleepValue : " + sleepValue;
        s += " glyph : " + glyph;
        s += " guerisonValue : " + guerisonValue;
        s += " price : " + price;
        s += " color : " + color;
        s += " name : " + name;

        return s;
    }
}
