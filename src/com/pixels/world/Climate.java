package com.pixels.world;

import com.pixels.tile.Tile;

public class Climate {
	
	public Climate(int e, int e2, int h, int h2, int t, int t2, int[] noSpawn) {
		elevation1 = e;
		elevation2 = e2;
		humidity1 = h;
		humidity2 = h2;
		tempurature1 = t;
		tempurature2 = t2;
		if (noSpawn == null) {
			noSpawnTiles = new int[]{1, 2};
		} else {
			noSpawnTiles = noSpawn;
		}
	}
	
	public int isInClimate(Tile t) {
		
		for (int i = 0; i < noSpawnTiles.length; i++) {
			if (t.id == noSpawnTiles[i])
				return 0;
		}
		
		int result = 100;
		
		if (t.elevation < elevation1) {
			result -= (elevation1 - t.elevation)*5;
		} 
		if (t.elevation > elevation2) {
			result -= (t.elevation - elevation2)*5;
		}
		
		if (t.humidity < humidity1) {
			result -= (humidity1 - t.humidity)*2;
		}
		if (t.humidity > humidity2) {
			result -= (t.humidity - humidity2)*2;
		}
		
		if (t.tempurature < tempurature1) {
			result -= (tempurature1 - t.tempurature)*4;
		} 
		if (t.tempurature > tempurature2) {
			result -= (t.tempurature - tempurature2)*4;
		}
		
		return result;
	}
	
	public static Climate everywhere = new Climate(0, 1000, 0, 170, 0, 127, null);
	public static Climate landAndSea = new Climate(0, 1000, 0, 170, 0, 127, new int[]{});
	
	public static Climate snow = new Climate(0, 1000, 0, 90, 0, 32, null);
	public static Climate tundra = new Climate(0, 1000, 90, 170, 0, 32, null);
	public static Climate jungle = new Climate(0, 1000, 0, 90, 80, 127, null);
	public static Climate desert = new Climate(0, 1000, 90, 170, 80, 127, null);
	
	public static Climate lowlandSnow = new Climate(0, 20, 0, 90, 0, 32, null);
	public static Climate lowlandTundra = new Climate(0, 20, 90, 170, 0, 32, null);
	public static Climate lowlandJungle = new Climate(0, 20, 0, 90, 80, 127, null);
	public static Climate lowlandDesert = new Climate(0, 20, 90, 170, 80, 127, null);
	
	public static Climate alpineSnow = new Climate(40, 60, 0, 90, 0, 32, null);
	public static Climate alpineTundra = new Climate(40, 60, 90, 170, 0, 32, null);
	public static Climate cloudJungle = new Climate(40, 60, 0, 90, 80, 127, null);
	public static Climate highDesert = new Climate(40, 60, 90, 170, 80, 127, null);
	
	public static Climate deciduousForest = new Climate(20, 40, 40, 120, 32, 80, null);
	
	public int elevation1, elevation2;
	public int humidity1, humidity2;
	public int tempurature1, tempurature2;
	public int[] noSpawnTiles;

}
