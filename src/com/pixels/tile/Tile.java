package com.pixels.tile;

import java.util.ArrayList;

import org.newdawn.slick.util.Log;

import com.pixels.tile.TileInfo;
import com.pixels.world.World;

public class Tile {

	public Tile(int x, int y, int id, int e, int h, int t) {
		
		posX = x;
		posY = y;
		elevation = e;
		humidity = h;
		tempurature = t;
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
	
	public static int getIDForTemplateColor(int color) {
		switch(color) {
		case -9332910:
			return 0;
		case -12741933:
			return 1;
		case -2703247:
			return 2;
		default:
			Log.debug("Unknown Template Tile Color: " + color);
			return 0;
		}
	}
	
	public int posX, posY, id, elevation, humidity, tempurature;
	public static ArrayList<TileInfo> info = new ArrayList<TileInfo>();
	
	static {
		info.add(new TileInfo());//grass
		info.add(new TileInfo());//water
		info.add(new TileInfo());//sand
	}

}
