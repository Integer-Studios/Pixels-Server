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
				//grass
				if (r.nextInt(50) == 0)
					pieces.put(getLocalLocationIndex(x, y), new Piece((chunkX << 4) + x, (chunkY << 4) + y, 1));
				else if (r.nextInt(50) == 0)
					pieces.put(getLocalLocationIndex(x, y), new Piece((chunkX << 4) + x, (chunkY << 4) + y, 2));
				
				//trees
				else if (r.nextInt(10) == 0)
					pieces.put(getLocalLocationIndex(x, y), new Piece((chunkX << 4) + x, (chunkY << 4) + y, 5));
				else if (r.nextInt(10) == 0)
					pieces.put(getLocalLocationIndex(x, y), new Piece((chunkX << 4) + x, (chunkY << 4) + y, 6));
				else if (r.nextInt(15) == 0)
					pieces.put(getLocalLocationIndex(x, y), new Piece((chunkX << 4) + x, (chunkY << 4) + y, 14));
				else if (r.nextInt(40) == 0)
					pieces.put(getLocalLocationIndex(x, y), new Piece((chunkX << 4) + x, (chunkY << 4) + y, 7));
				
				//rocks
				else if (r.nextInt(70) == 0)
					pieces.put(getLocalLocationIndex(x, y), new Piece((chunkX << 4) + x, (chunkY << 4) + y, 3));
				else if (r.nextInt(70) == 0)
					pieces.put(getLocalLocationIndex(x, y), new Piece((chunkX << 4) + x, (chunkY << 4) + y, 4));
				else if (r.nextInt(70) == 0)
					pieces.put(getLocalLocationIndex(x, y), new Piece((chunkX << 4) + x, (chunkY << 4) + y, 15));
				
				//flowers
				else if (r.nextInt(100) == 0)
					pieces.put(getLocalLocationIndex(x, y), new Piece((chunkX << 4) + x, (chunkY << 4) + y, 8));
				else if (r.nextInt(100) == 0)
					pieces.put(getLocalLocationIndex(x, y), new Piece((chunkX << 4) + x, (chunkY << 4) + y, 13));
				
				// bushes
				else if (r.nextInt(100) == 0)
					pieces.put(getLocalLocationIndex(x, y), new Piece((chunkX << 4) + x, (chunkY << 4) + y, 11));
				else if (r.nextInt(100) == 0)
					pieces.put(getLocalLocationIndex(x, y), new Piece((chunkX << 4) + x, (chunkY << 4) + y, 12));
				
				
			}
		}
	}
	
	public void update(World w) {
		
		for (int y = 0; y < 16; y++) {
			for (int x = 0; x < 16; x++) {
				
				int i = getLocalLocationIndex(x, y);
				
				tiles.get(i).update(w);
				Piece p = pieces.get(i);
				if (p != null)
					p.update(w);
				
//				entities will update after all chunk updates	
				
//				int globalX = (chunkX<<4)+x;
//				int globalY = (chunkY<<4)+y;
//				Entity e = w.getEntity(globalX, globalY);
//				if (e != null)
//				ww	e.update(w);
				
				
			}
		}

	}
	
	public void setPieceID(int x, int y, int id) {
		pieces.put(getGlobalLocationIndex(x, y), new Piece(x, y, id));
	}
	
	public void setPieceIDAndMetadata(int x, int y, int id, int metadata) {
		setPieceID(x, y, id);
		pieces.get(getGlobalLocationIndex(x, y)).metadata = metadata;
	}
	
	public int getPieceID(int x, int y) {
		if (pieces.get(getGlobalLocationIndex(x, y)) == null) {
			return 0;
		}  else {
			return pieces.get(getGlobalLocationIndex(x, y)).getPieceID();
		}
	}
	
	public Piece getPiece(int x, int y) {
		return pieces.get(getGlobalLocationIndex(x, y));
	}
	
	private int getGlobalLocationIndex(int x, int y) {
		int localX = x - (chunkX << 4);
		int localY = y - (chunkY << 4);
		return getLocalLocationIndex(localX, localY);
	}
	
	private int getLocalLocationIndex(int x, int y) {
		return y*16 + x;
	}
	
	private int getGlobalX(int x) {
		return x + (chunkX << 4);
	}
	
	private int getGlobalY(int y) {
		return y + (chunkY << 4);
	}
	
	public ConcurrentHashMap<Integer,Tile> tiles = new ConcurrentHashMap<Integer,Tile>();
	public ConcurrentHashMap<Integer,Piece> pieces = new ConcurrentHashMap<Integer,Piece>();
	
	public int chunkX, chunkY;
	
}
