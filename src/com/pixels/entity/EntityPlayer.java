package com.pixels.entity;

import com.pixels.start.PixelsServer;

public class EntityPlayer extends Entity {
	
	public EntityPlayer() {
		this.id = 1;
	}
	
	public EntityPlayer(int x, int y, boolean prop, int uid) {
		this.id = 1;
		posX = x;
		posY = y;
		userID = uid;
		if (prop)
			PixelsServer.world.propogatePlayer(this);
	}
	
	public int userID;

}
