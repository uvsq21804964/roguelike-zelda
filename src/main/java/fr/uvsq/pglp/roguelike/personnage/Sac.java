package fr.uvsq.pglp.roguelike.personnage;

class Sac {

    private final Item[] items;

    public Sac(int max) {
        items = new Item[max];
    }

    public Item[] getItems() {
        return items;
    }

    public Item get(int i) {
        return items[i];
    }

    public void add(Item item) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = item;
                break;
            }
        }
    }

    public void remove(Item item) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == item) {
                items[i] = null;
                return;
            }
        }
    }

    public boolean isFull() {
        return size() == items.length;
    }

    public int size() {
        int size = 0;
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null)
                size++;
        }
        return size;
    }

    public boolean contains(String str) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                if (items[i].name() == str)
                    return true;
            }
        }
        return false;
    }
}
