package com.pixels.world;

import java.util.ArrayList;
import java.util.Random;

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
				
				tiles.add(new Tile((chunkX << 4) + x, (chunkY << 4) + y, 0));
				
				Random r = new Random();
				if (r.nextInt(30) == 0)
					pieces.add(new Piece((chunkX << 4) + x, (chunkY << 4) + y, 2));
				else if (r.nextInt(10) == 0)
					pieces.add(new Piece((chunkX << 4) + x, (chunkY << 4) + y, 1));
				else
					pieces.add(new Piece((chunkX << 4) + x, (chunkY << 4) + y, 0));
				
			}
		}
	}
	
	public void update(World w) {
		for (int i = 0; i < 256; i++) {
			tiles.get(i).update(w);
			pieces.get(i).update(w);
		}
	}
	
	public void setPiece(int x, int y, int id) {
		pieces.get(getPieceIndex(x, y)).setPieceID(id);
	}
	
	public int getPieceID(int x, int y) {
		return pieces.get(getPieceIndex(x, y)).getPieceID();
	}
	
	public int getPieceIndex(int x, int y) {
		int localX = x - (chunkX << 4);
		int localY = y - (chunkY << 4);
		return localY*16 + localX;
	}
	
	public ArrayList<Tile> tiles = new ArrayList<Tile>();
	public ArrayList<Piece> pieces = new ArrayList<Piece>();
	public ArrayList<Integer> entities = new ArrayList<Integer>();
	
	public int chunkX, chunkY;
}
