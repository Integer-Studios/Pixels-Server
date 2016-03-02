package com.pixels.piece;

import java.util.ArrayList;

import com.pixels.world.World;

public class Piece {
	
	public Piece (int x, int y, int id) {
		
		posX = x;
		posY = y;
		this.id = id;
		
	}
	
	public void update(World w) {
		info.get(id).update(w, this);
	}
	
	public void setPieceID(int id) {
		this.id = id;
	}

	public int getPieceID() {
		return id;
	}
	
	public int posX, posY, id;
	public static ArrayList<PieceInfo> info = new ArrayList<PieceInfo>();
	
	static {
		info.add(new PieceInfo());
		info.add(new PieceInfo());//grass
		info.add(new PieceInfo());//rock
	}

}
