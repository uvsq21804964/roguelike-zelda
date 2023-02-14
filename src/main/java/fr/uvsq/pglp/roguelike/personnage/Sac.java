package fr.uvsq.pglp.roguelike.personnage;

class Sac {

    private final Equipement[] items;

    public Sac(int max) {
        items = new Equipement[max];
    }

    public Equipement[] getItems() {
        return items;
    }

    public Equipement get(int i) {
        return items[i];
    }
    
    public Equipement getItemAleatoire() {
    	
    	int nbItems = 0;
    	for(Equipement e : items) {
    		if(e != null) {
    			nbItems++;
    		}
    	}
    	
    	if(nbItems == 0) {
    		return null;
    	}
    	
    	Equipement[] itemsNonNull = new Equipement[nbItems];
    	nbItems = 0;
    	for(Equipement e : items) {
    		if(e != null) {
    			itemsNonNull[nbItems] = e;
    			nbItems++;
    		}
    	}
    	
    	int alea = (int) (Math.random()*nbItems);
    	return itemsNonNull[alea];
    }

    public void add(Equipement item) {
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
