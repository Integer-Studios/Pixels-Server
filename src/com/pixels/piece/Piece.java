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
		info.add(new PieceInfo());//0, blank
		info.add(new PieceInfo());//1, grass 1
		info.add(new PieceInfo());//2, grass 2
		info.add(new PieceInfo());//3, rock 1
		info.add(new PieceInfo());//4, rock 2
		info.add(new PieceInfo());//5, pine
		info.add(new PieceInfo());//6, apple
		info.add(new PieceInfo());//7, abyssal fir
		info.add(new PieceInfo());//8, flower

	}

}
