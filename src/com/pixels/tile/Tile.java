package com.pixels.tile;

import java.util.ArrayList;

import com.pixels.tile.TileInfo;
import com.pixels.world.World;

public class Tile {

	public Tile(int x, int y, int id) {
		
		posX = x;
		posY = y;
		this.id = id;
		
	}

	public void update(World w) {
		info.get(id).update(w, this);
	}
	
	public int posX, posY, id;
	public static ArrayList<TileInfo> info = new ArrayList<TileInfo>();
	
	static {
		info.add(new TileInfo());//grass
	}

}
