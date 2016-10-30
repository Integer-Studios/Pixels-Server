package com.pixels.piece;

import java.util.Random;

import com.pixels.item.Item;
import com.pixels.tile.Tile;
import com.pixels.world.Climate;
import com.pixels.world.World;

public class PieceInfo {
	
	public PieceInfo() {
		collisionWidth = 0;
		collisionHeight = 0;
		doesCollide = false;
	}
	
	public PieceInfo(float width, float height) {
		collisionWidth = width;
		collisionHeight = height;
		doesCollide = true;
	}

	public void update(World w, Piece p) {

	}
	
	public PieceInfo setOnHarvestID(int i) {
		onHarvestID = i;
		return this;
	}
	
	public PieceInfo setHarvestItems(Item[] items) {
		harvestItems = items;
		return this;
	}
	
	public PieceInfo setRarity(int i) {
		rarity = i;
		return this;
	}
	
	public PieceInfo setNaturallyOccurring(boolean b) {
		if (!b)
			rarity = 0;
		return this;
	}
	
	public PieceInfo setClimate(Climate c) {
		climate = c;
		return this;
	}
	
	public boolean attemptToGenerate(World w, Tile t, int id) {
		
		// no spawning on cliffs ever
		int down = w.getElevation(t.posX, t.posY+1);
		if (down - t.elevation < 0)
			return false;
		
		Random r = new Random();
		//check climate
		if (r.nextInt(100) < climate.isInClimate(t)) {
			
			if (r.nextInt(10000) < rarity) {
				w.setPiece(t.posX, t.posY, new Piece(t.posX, t.posY, id));
				return true;
			}
		}
		
		return false;
	}
	
	public float collisionWidth, collisionHeight;
	public boolean doesCollide = false;
	public int onHarvestID = 0;
	public int rarity = 200;
	public boolean spwansInWater;
	public Item[] harvestItems = new Item[0];
	public Climate climate = Climate.everywhere;
	
	

}
