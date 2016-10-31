package com.pixels.piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.newdawn.slick.geom.Rectangle;

import com.pixels.entity.EntityItem;
import com.pixels.item.Item;
import com.pixels.tile.Tile;
import com.pixels.world.Climate;
import com.pixels.world.World;

public class Piece {
	
	//Piece generation variables
	
	//default value for piece rarity - effects density
	public static int pieceRarity = 3000;
	
	//climate adherence values also effect spawn rates, fades, and density
	
	//the importance of humidity in any piece's basic spawn chance (1-10)
	public static int humidityChanceMultiplier = 5;
	//the importance of humidity in decreasing piece density for dry areas (1-10)
	public static int humidityRetryMultiplier = 1;
	//the amount of fade between climates (1-10,000)
	public static int fadeRate = 600;
	
	public Piece (int x, int y, int id) {
		
		posX = x;
		posY = y;
		this.id = id;
		collisionBox = new Rectangle(x+((1-info.get(id).collisionWidth)/2), y+(1-info.get(id).collisionHeight), info.get(id).collisionWidth, info.get(id).collisionHeight);

	}
	
	public Piece (int x, int y, int id, int m) {
		
		this(x, y, id);
		metadata = m;
	}
	
	public void update(World w) {
		info.get(id).update(w, this);
	}
	
	public void setPieceID(int id) {
		this.id = id;
		this.metadata = 0;
	}
	
	public void setPieceIDAndMetadata(int id, int metadata) {
		this.id = id;
		this.metadata = metadata;
	}

	public int getPieceID() {
		return id;
	}
	
	public boolean doesCollide() {
		return info.get(id).doesCollide;
	}
	
	public float getCollisionWidth() {
		return info.get(id).collisionWidth;
	}
	
	public float getCollisionHeight() {
		return info.get(id).collisionHeight;
	}
	
	public void harvest(World w) {
		w.setPieceID(posX, posY, info.get(id).onHarvestID);
		Item[] items = info.get(id).harvestItems;
		Random r = new Random();
		for (int i = 0; i < items.length; i++) {
			new EntityItem(items[i], posX + ((float)r.nextInt(10)/(float)10), posY + 1+((float)r.nextInt(10)/(float)10), true);
		}
	}
	
	public static void generatePiece(World w, Tile t) {
		
		HashMap<Integer, Integer> possibilities = new HashMap<Integer, Integer>();
		for (int i = 1; i < info.size(); i++) {
			int chance = info.get(i).attemptToGenerate(w, t, i);
			chance = chance - (10000-info.get(i).rarity) - t.humidity*humidityChanceMultiplier;
			if (chance > 0) {				
				possibilities.put(i, chance);
			}
		}
		
		possibilities = (HashMap<Integer, Integer>) shuffle(possibilities);
		possibilities = (HashMap<Integer, Integer>) sortByValue(possibilities);
		
		for (Map.Entry<Integer, Integer> entry : possibilities.entrySet()) {
			int id = entry.getKey();
			int chance = entry.getValue();
			Random r = new Random();
			if (r.nextInt(10000) < chance) {
				w.setPiece(t.posX, t.posY, new Piece(t.posX, t.posY, id));
				break;
			} else {
				//retry based on humidity
				int h = t.humidity;
				h += (t.humidity - 85)*humidityRetryMultiplier;
				if (r.nextInt(170) < h)
					break;
			}
		}
	}
	
	public static void print(HashMap<Integer, Integer> map) {
		if (map.isEmpty())
			return;
		System.out.print("[");
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			int id = entry.getKey();
			System.out.print("(id:" + id);
			int chance = entry.getValue();
			System.out.print(" chance:" + chance + "),");
		}
		System.out.println("]");
	}
	
	public static HashMap<Integer, Integer> shuffle(HashMap<Integer, Integer> map) {
		LinkedHashMap<Integer, Integer> shuffled = new LinkedHashMap<Integer, Integer>();
		Set<Integer> keySet = map.keySet();
		Integer[] keys = keySet.toArray(new Integer[keySet.size()]);
		int i = 0;
		Random r = new Random();
		while (i < keys.length) {
			int key = keys[r.nextInt(keys.length)];
			while (shuffled.get(key) != null) {
				key = keys[r.nextInt(keys.length)];
			}
			shuffled.put(key, map.get(key));
			i++;
		}
		return shuffled;
	}
	
	public static <K, V extends Comparable<? super V>> Map<K, V> 
	    sortByValue( Map<K, V> map )
	{
	    List<Map.Entry<K, V>> list =
	        new LinkedList<Map.Entry<K, V>>( map.entrySet() );
	    Collections.sort( list, new Comparator<Map.Entry<K, V>>()
	    {
	        public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
	        {
	        	// small chance to fuck up order to allow fading on climate edges (imperfect natural selection)
	        	if (new Random().nextInt(10000) < fadeRate)
	        		return (o1.getValue()).compareTo( o2.getValue() );
	        	else
	        		return (o2.getValue()).compareTo( o1.getValue() );
	        }
	    } );
	
	    Map<K, V> result = new LinkedHashMap<K, V>();
	    for (Map.Entry<K, V> entry : list)
	    {
	        result.put( entry.getKey(), entry.getValue() );
	    }
	    return result;
	}
	
	public int posX, posY, id, metadata;
	public static ArrayList<PieceInfo> info = new ArrayList<PieceInfo>();
	public Rectangle collisionBox;
	
	static {
		info.add(new PieceInfo());//0, blank
		info.add(new PieceInfo().setClimate(Climate.everywhere).setRarity(1500));//1, grass 1
		info.add(new PieceInfo().setClimate(Climate.everywhere).setRarity(1500));//2, grass 2
		info.add(new PieceInfo(0.2f, 0.1f).setHarvestItems(new Item[]{Item.stone}).setClimate(Climate.everywhere).setRarity(1500));//3, rock 1
		info.add(new PieceInfo(0.4f, 0.2f).setOnHarvestID(3).setHarvestItems(new Item[]{Item.stone}).setClimate(Climate.tundra));//4, rock 2
		info.add(new PieceInfo(0.1f, 0.2f).setClimate(Climate.coast));//5, pine
		info.add(new PieceInfo(0.1f, 0.2f).setClimate(Climate.snow));//6, apple
		info.add(new PieceInfo(0.1f, 0.2f).setClimate(Climate.tundra));//7, abyssal fir
		info.add(new PieceInfo().setClimate(Climate.coast));//8, flower
		info.add(new PieceInfo().setNaturallyOccurring(false));//9, cabin
		info.add(new PieceInfo().setClimate(Climate.coast).setRarity(2000));//10, cherry
		info.add(new PieceInfo().setOnHarvestID(16).setHarvestItems(new Item[]{Item.cloudberries}).setClimate(Climate.coast).setRarity(2000));//11, cloudberry
		info.add(new PieceInfo().setOnHarvestID(17).setHarvestItems(new Item[]{Item.crowberries}).setClimate(Climate.coast).setRarity(2000));//12, crowberry
		info.add(new PieceInfo().setHarvestItems(new Item[]{Item.poppyHead}).setClimate(Climate.coast).setRarity(2000));//13, tulip
		info.add(new PieceInfo().setClimate(Climate.snow));//14, pine tall
		info.add(new PieceInfo().setOnHarvestID(4).setHarvestItems(new Item[]{Item.stone}).setClimate(Climate.snow));//15, rock 3
		info.add(new PieceInfo().setNaturallyOccurring(false));//16, cloudberry picked
		info.add(new PieceInfo().setNaturallyOccurring(false));//17, crowberry picked
		info.add(new PieceInfo());//18, shroom
		info.add(new PieceInfo().setNaturallyOccurring(false));//19, gaub building
		info.add(new PieceInfo().setClimate(Climate.tundra));//20, desert flower
		info.add(new PieceInfo());//21, stingjuice
		info.add(new PieceInfo());//22, desert lotus
		info.add(new PieceInfo());//23, agave
		info.add(new PieceInfo());//24, agave-bloomed


	}

}
