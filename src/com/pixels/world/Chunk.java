package com.pixels.world;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import com.pixels.piece.Piece;
import com.pixels.tile.Tile;

public class Chunk {
	
	public Chunk(int x, int y) {
		chunkX = x;
		chunkY = y;
		generateChunk();
	}
	
	public void generateChunk() {
		for (int y = 0; y < 16; y++) {
			for (int x = 0; x < 16; x++) {
				
				tiles.put(getLocalLocationIndex(x, y), new Tile((chunkX << 4) + x, (chunkY << 4) + y, 0));
				
				Random r = new Random();
				if (r.nextInt(30) == 0)
					pieces.put(getLocalLocationIndex(x, y), new Piece((chunkX << 4) + x, (chunkY << 4) + y, 2));
				else if (r.nextInt(10) == 0)
					pieces.put(getLocalLocationIndex(x, y), new Piece((chunkX << 4) + x, (chunkY << 4) + y, 1));
				
			}
		}
	}
	
	public void update(World w) {
		for (int i = 0; i < 256; i++) {
			tiles.get(i).update(w);
			
			Piece p = pieces.get(i);
			if (p != null)
				p.update(w);
		}
	}
	
	public void setPieceID(int x, int y, int id) {
		pieces.get(getGlobalLocationIndex(x, y)).setPieceID(id);
	}
	
	public int getPieceID(int x, int y) {
		return pieces.get(getGlobalLocationIndex(x, y)).getPieceID();
	}
	
	private int getGlobalLocationIndex(int x, int y) {
		int localX = x - (chunkX << 4);
		int localY = y - (chunkY << 4);
		return getLocalLocationIndex(localX, localY);
	}
	
	private int getLocalLocationIndex(int x, int y) {
		return y*16 + x;
	}
	
	public ConcurrentHashMap<Integer,Tile> tiles = new ConcurrentHashMap<Integer,Tile>();
	public ConcurrentHashMap<Integer,Piece> pieces = new ConcurrentHashMap<Integer,Piece>();
	
	public int chunkX, chunkY;
}
