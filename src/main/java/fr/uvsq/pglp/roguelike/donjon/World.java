package fr.uvsq.pglp.roguelike.donjon;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import asciiPanel.AsciiPanel;
import fr.uvsq.pglp.roguelike.personnage.Personnage;

/**
 * Cette classe est responsable de la gestion du monde
 * créé par <code>WorldBuilder</code> et affiché par 
 * <code>Playscreen</code>.
 *
 * @author Tom Abbouz
 * @version janvier 2023
 */
public class World implements Serializable {
	private static final long serialVersionUID = -7000789467948936550L;
	
	private Tile[][] tiles;
	private int numeroSalle;
	private Equipement[][] items;
	private Rubis[][] rubises;
	
	private int width;
	public int width() { return width; }
	
	private int height;
	public int height() { return height; }
	
	private List<Personnage> personnages;
	
	public World(Tile[][] tiles, int numeroSalle){
		this.numeroSalle = numeroSalle;
		this.tiles = tiles;
		this.width = tiles.length;
		this.height = tiles[0].length;
		this.items = new Equipement[width][height];
		this.rubises = new Rubis [width][height];
		this.personnages = new ArrayList<Personnage>();
	}
	
	public void equal(World world){
		this.numeroSalle = world.numeroSalle();
		this.tiles = world.tiles();
		addDoors(true);
		this.width = world.tiles.length;
		this.height = world.tiles[0].length;
		this.items = world.items();
		this.rubises = world.rubises();;
		this.personnages = world.creatures();;
	}
	
	private Tile[][] tiles() {
		return tiles;
	}
	
	private Equipement[][] items() {
		return items;
	}
	
	private Rubis[][] rubises() {
		return rubises;
	}
	
	public List<Personnage> personnages() {
		return personnages;
	}
	
	public Personnage personnage(int x, int y){
		for (Personnage c : personnages){
			if (c.x == x && c.y == y)
				return c;
		}
		return null;
	}
	
	public Tile tile(int x, int y){
		if (x < 0 || x >= width || y < 0 || y >= height)
			return Tile.BOUNDS;
		else
			return tiles[x][y];
	}
	
	public Equipement item(int x, int y){
	    return items[x][y];
	}
	
	public Rubis rubis(int x, int y){
	    return rubises[x][y];
	}
	
	public char glyph(int x, int y){
	    Personnage creature = personnage(x, y);
	    if (creature != null)
	        return creature.glyph();
	    
	    if (item(x,y) != null)
	        return item(x,y).glyph();
	    
	    if (rubis(x,y) != null)
	        return rubis(x,y).glyph();
	    
	    return tile(x, y).glyph();
	}
	
	public Color color(int x, int y){
	    Personnage if (creature != null)
	        return creature.color();
	    
	    if (item(x,y) != null)
	        return item(x,y).color();
	    
	    if (rubis(x,y) != null)
	        return rubis(x,y).color();
	    
	    return tile(x, y).color();
	}
	
	public void addAtEmptyLocation(Creature creature){
		int x;
		int y;
		
		do {
			x = (int)(Math.random() * width);
			y = (int)(Math.random() * height);
		} 
		while (!tile(x,y).isGround() || creature(x,y) != null);
		
		creature.x = x;
		creature.y = y;
		creatures.add(creature);
	}
	
	public void addLocationZombie(Creature creature, int x, int y){		
		creature.x = x;
		creature.y = y;
		creatures.add(creature);
	}
	
	public void addAtEmptyLocation(Item item) {
	    int x;
	    int y;
	    
	    do {
	        x = (int)(Math.random() * width);
	        y = (int)(Math.random() * height);
	    }
	    while (!tile(x,y).isGround() || item(x,y) != null);
	    
	    items[x][y] = item;
	}
	
	public void update(){
		List<Creature> toUpdate = new ArrayList<Creature>(creatures);
		for (Creature creature : toUpdate){
			creature.update();
		}
	}

	public void boxToRubis(int x, int y) {
		tiles[x][y] = Tile.FLOOR;
		int a = (int)(Math.random() * 100);
		Color color = AsciiPanel.green;
		if (a >= 99 ) {
			color = AsciiPanel.brightYellow;
		} else if (a >= 96 ) {
			color = AsciiPanel.brightCyan;
		} else if (a >= 91 ) {
			color = AsciiPanel.brightMagenta;
		} else if (a >= 81 ) {
			color = AsciiPanel.red;
		} else if (a >= 71 ) {
			color = AsciiPanel.yellow;
		} else if (a >= 51 ) {
			color = AsciiPanel.blue;
		}
		Rubis rubis = new Rubis(color);
		Item argent = new Item('$', color, "rubis", 0);
    	rubises[x][y] = rubis;
	    items[x][y] = argent;
	}
	
	public void leaveRubis(int a, int x, int y) {

		Color color = AsciiPanel.green;
		if (a >= 10 ) {
			color = AsciiPanel.brightYellow;
		} else if (a == 9 ) {
			color = AsciiPanel.brightCyan;
		} else if (a == 8 || a == 7 ) {
			color = AsciiPanel.brightMagenta;
		} else if (a == 6 || a == 5 ) {
			color = AsciiPanel.red;
		} else if (a == 4 || a == 3 ) {
			color = AsciiPanel.yellow;
		} else if (a == 2 ) {
			color = AsciiPanel.blue;
		}
		Rubis rubis = new Rubis(color);
		Item argent = new Item('$', color, "rubis", 0);
    	rubises[x][y] = rubis;
	    items[x][y] = argent;
	}
	
	public void boxToArmor(int x, int y) {
		tiles[x][y] = Tile.FLOOR;
		CreatureFactory creatureFactory = new CreatureFactory(this);
		Item armor = creatureFactory.randomArmor();
	    items[x][y] = armor;
	}
	
	public void boxToWeapon(int x, int y) {
		tiles[x][y] = Tile.FLOOR;
		CreatureFactory creatureFactory = new CreatureFactory(this);
		Item weapon = creatureFactory.randomWeapon();
	    items[x][y] = weapon;
	}
	
	public void remove(Creature other) {
		creatures.remove(other);
	}
	
	public void addLocationPlayer(Creature creature, int salleprec){
        int x = 0;
        int y = 0;

        if (salleprec == 0) {
	        File file = new File("src/main/resources/Save/SalleSave.txt");
	        
	        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
	
		      String line;
		      int j = 0;
		      
		      out :
		      while ((line = br.readLine()) != null) {
		        for (int i = 0; i < line.length(); i++) {
		          if(line.charAt(i) == '@') {
		        	  x = i;
		        	  y = j;
		        	  break out;
		          }
		        }
		        j++;
		      }
		    } catch (FileNotFoundException e) {
		      System.err.printf("Le fichier 'Salle" + numeroSalle + ".txt' n'a pas été trouvé.");
		    } catch (IOException e) {        
		      System.err.printf("Impossible de lire le contenu du fichier 'Salle1" + numeroSalle + ".txt'");
		    }
        } else {
        	
        	char salle = Character.forDigit(salleprec, 10);
        	File file = new File("src/main/resources/Salles/Salle" + numeroSalle + ".txt");
	        int choix = (int)(Math.random() * 2);
	        		
	        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
	
		      String line;
		      int j = 0;
		      
		      out :
		      while ((line = br.readLine()) != null) {
		        for (int i = 0; i < line.length(); i++) {
		          if(line.charAt(i) == salle) {
		        	  x = i;
		        	  y = j;
		        	  if( choix == 0) {
		        		  break out; 
		        	  } else {
		        		  choix = 0;
		        	  }
		          }
		        }
		        j++;
		      }
		    } catch (FileNotFoundException e) {
		      System.err.printf("Le fichier 'Salle" + numeroSalle + ".txt' n'a pas été trouvé.");
		    } catch (IOException e) {        
		      System.err.printf("Impossible de lire le contenu du fichier 'Salle" + numeroSalle + ".txt'");
		    }
        }

        creature.x = x;
        creature.y = y;
        creatures.add(creature);
    }

	public int getNumeroSalle() {
		return numeroSalle;
	}
	
	public void addBoxes() {

        File file = new File("src/main/resources/Salles/Salle" + numeroSalle + ".txt");
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

	      String line;
	      int j = 0;
	      
	      while ((line = br.readLine()) != null) {
	        for (int i = 0; i < line.length(); i++) {
	          if(line.charAt(i) == 'X') {
	        	  Item caisse = new Item('X', new Color(17,200,38), "Caisse", 0);
	        	  items[i][j] = caisse;
	        	  tiles[i][j] = Tile.BOX; 
	          }
	        }
	        j++;
	      }
	    } catch (FileNotFoundException e) {
	      System.err.printf("Le fichier 'Salle" + numeroSalle + ".txt' n'a pas été trouvé.");
	    } catch (IOException e) {        
	      System.err.printf("Impossible de lire le contenu du fichier 'Salle" + numeroSalle + ".txt'");
	    }
	}
	
	public void addDoors(boolean boussole) {

        File file = new File("src/main/resources/Salles/Salle" + numeroSalle + ".txt");
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

	      String line;
	      int j = 0;
	      
    	  while ((line = br.readLine()) != null) {
  	        for (int i = 0; i < line.length(); i++) {
  	          switch(line.charAt(i)){
  	          case '1':
  	          case '2':
  	          case '3':
  	          case '4':
  	          case '5':
  	          case '6':
  	          case '7':
  	          case '8':
  	          case '9':
  	        	  tiles[i][j] = Tile.P; 
  	          }
  	        }
  	        j++;
  	      }
    	  
	    } catch (FileNotFoundException e) {
	      System.err.printf("Le fichier 'Salle" + numeroSalle + ".txt' n'a pas été trouvé.");
	    } catch (IOException e) {        
	      System.err.printf("Impossible de lire le contenu du fichier 'Salle" + numeroSalle + ".txt'");
	    }
	}
	
	public void remove(int x, int y) {
	    items[x][y] = null;
	}
	
	public boolean addAtEmptySpace(Item item, int x, int y){
		if (item == null)
			return true;
		
		List<Point> points = new ArrayList<Point>();
		List<Point> checked = new ArrayList<Point>();
		
		points.add(new Point(x, y));
		
		while (!points.isEmpty()){
			Point p = points.remove(0);
			checked.add(p);
			
			if (!tiles[p.x][p.y].isGround())
				continue;
				
			if (items[p.x][p.y] == null){
				items[p.x][p.y] = item;
				Creature c = this.creature(p.x, p.y);
				if (c != null)
					c.notify("%s est par terre.", item.name());
				return true;
			} else {
				List<Point> neighbors = p.neighbors8();
				neighbors.removeAll(checked);
				points.addAll(neighbors);
			}
		}
		return false;
	}

	public void removeRubis(int x, int y) {
		rubises[x][y] = null;
	}
}
