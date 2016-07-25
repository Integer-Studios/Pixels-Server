package com.pixels.piece;

import com.pixels.item.Item;
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
	
	public float collisionWidth, collisionHeight;
	public boolean doesCollide = false;
	public int onHarvestID = 0;
	public Item[] harvestItems = new Item[0];

}
