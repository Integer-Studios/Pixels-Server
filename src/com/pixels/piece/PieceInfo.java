package com.pixels.piece;

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
	
	public float collisionWidth, collisionHeight;
	public boolean doesCollide = false;

}
