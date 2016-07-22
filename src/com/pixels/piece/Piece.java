package com.pixels.piece;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;

import com.pixels.world.World;

public class Piece {
	
	public Piece (int x, int y, int id) {
		
		posX = x;
		posY = y;
		this.id = id;
		collisionBox = new Rectangle(x+((1-info.get(id).collisionWidth)/2), y+(1-info.get(id).collisionHeight), info.get(id).collisionWidth, info.get(id).collisionHeight);

	}
	
	public Piece (int x, int y, int id, int m) {
		
		this(x, y, id);
		metadata = m;
	}
	
	public void update(World w) {
		info.get(id).update(w, this);
	}
	
	public void setPieceID(int id) {
		this.id = id;
		this.metadata = 0;
	}
	
	public void setPieceIDAndMetadata(int id, int metadata) {
		this.id = id;
		this.metadata = metadata;
	}

	public int getPieceID() {
		return id;
	}
	
	public boolean doesCollide() {
		return info.get(id).doesCollide;
	}
	
	public float getCollisionWidth() {
		return info.get(id).collisionWidth;
	}
	
	public float getCollisionHeight() {
		return info.get(id).collisionHeight;
	}
	
	public int posX, posY, id, metadata;
	public static ArrayList<PieceInfo> info = new ArrayList<PieceInfo>();
	public Rectangle collisionBox;
	
	static {
		info.add(new PieceInfo());//0, blank
		info.add(new PieceInfo());//1, grass 1
		info.add(new PieceInfo());//2, grass 2
		info.add(new PieceInfo(0.2f, 0.1f));//3, rock 1
		info.add(new PieceInfo(0.4f, 0.2f));//4, rock 2
		info.add(new PieceInfo(0.1f, 0.2f));//5, pine
		info.add(new PieceInfo(0.1f, 0.2f));//6, apple
		info.add(new PieceInfo(0.1f, 0.2f));//7, abyssal fir
		info.add(new PieceInfo());//8, flower
		info.add(new PieceInfo());//9, cabin
		info.add(new PieceInfo());//10, cherry
		info.add(new PieceInfo());//11, cloudberry
		info.add(new PieceInfo());//12, crowberry
		info.add(new PieceInfo());//13, tulip
		info.add(new PieceInfo());//14, pine tall
		info.add(new PieceInfo());//15, rock 3

	}

}
