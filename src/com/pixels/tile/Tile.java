package com.pixels.tile;

import java.util.ArrayList;

import com.pixels.tile.TileInfo;
import com.pixels.world.World;

public class Tile {

	public Tile(int x, int y, int id, int e) {
		
		posX = x;
		posY = y;
		elevation = e;
		this.id = id;
		
	}

	public void update(World w) {
		info.get(id).update(w, this);
	}
	
	public boolean isCliff(World w) {
		int e = elevation;
		int eDown = w.getElevation(posX, posY+1) - e;
		return (eDown < 0);
	}
	
	public int posX, posY, id, elevation;
	public static ArrayList<TileInfo> info = new ArrayList<TileInfo>();
	
	static {
		info.add(new TileInfo());//grass
	}

}
