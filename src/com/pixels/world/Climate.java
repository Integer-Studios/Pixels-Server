package com.pixels.world;

import com.pixels.tile.Tile;

public class Climate {
	
	public Climate(int e, int eA, int h, int hA, int t, int tA, int[] noSpawn) {
		elevation = e;
		humidity = h;
		tempurature = t;
		eAdherence = eA;
		hAdherence = hA;
		tAdherence = tA;
		if (noSpawn == null) {
			noSpawnTiles = new int[]{1, 2};
		} else {
			noSpawnTiles = noSpawn;
		}
	}
	
	public Climate(int e, int h, int t, int[] noSpawn) {
		this(e, 55, h, 55, t, 55, noSpawn);
	}
	
	public Climate(Climate c) {
		this(c.elevation, c.eAdherence, c.humidity, c.hAdherence, c.tempurature, c.tAdherence, c.noSpawnTiles);
	}
	
	public int isInClimate(Tile t) {
		
		for (int i = 0; i < noSpawnTiles.length; i++) {
			if (t.id == noSpawnTiles[i])
				return 0;
		}
		
		int result = 10000;
		
		if (elevation != -1)
			result -= Math.abs(t.elevation - elevation)*eAdherence;
		
		if (humidity != -1)
			result -= Math.abs(t.humidity - humidity)*hAdherence;
		
		if (tempurature != -1)
			result -= Math.abs(t.tempurature - tempurature)*tAdherence;
		
		return result;
	}
	
	public Climate setAdherenceMultipliers(int e, int h, int t) {
		eAdherence = e;
		hAdherence = h;
		tAdherence = t;
		return this;
	}
	
	
	public static Climate everywhere = new Climate(0, 0, 0, 0, 0, 0, null);
	public static Climate nowhere = new Climate(-1000, -1000, -1000, null);
	public static Climate snow = new Climate(-1, 0, 40, 30, 15, 30, null);
	public static Climate tundra = new Climate(-1, 0, 100, 30, 15, 30, null);
	public static Climate coast = new Climate(-1, 0, 70, 40, 60, 50, null);
//	public static Climate jungle = new Climate(0, 1000, 0, 90, 80, 127, null);
//	public static Climate desert = new Climate(0, 1000, 90, 170, 80, 127, null);
//	
//	public static Climate lowlandSnow = new Climate(0, 20, 0, 90, 0, 32, null);
//	public static Climate lowlandTundra = new Climate(0, 20, 90, 170, 0, 32, null);
//	public static Climate lowlandJungle = new Climate(0, 20, 0, 90, 80, 127, null);
//	public static Climate lowlandDesert = new Climate(0, 20, 90, 170, 80, 127, null);
//	
//	public static Climate alpineSnow = new Climate(40, 60, 0, 90, 0, 32, null);
//	public static Climate alpineTundra = new Climate(40, 60, 90, 170, 0, 32, null);
//	public static Climate cloudJungle = new Climate(40, 60, 0, 90, 80, 127, null);
//	public static Climate highDesert = new Climate(40, 60, 90, 170, 80, 127, null);
//	
//	public static Climate deciduousForest = new Climate(20, 40, 40, 120, 32, 80, null);
//	public static Climate everywhere = new Climate(0, 1000, 0, 170, 0, 127, null);
//	public static Climate landAndSea = new Climate(0, 1000, 0, 170, 0, 127, new int[]{});
//	
	
	//essentially how much biome adherence matters
	public int eAdherence;
	public int hAdherence;
	public int tAdherence;
	public int elevation;
	public int humidity;
	public int tempurature;
	public int[] noSpawnTiles;

}
