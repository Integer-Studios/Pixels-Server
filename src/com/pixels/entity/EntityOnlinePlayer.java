package com.pixels.entity;

import com.pixels.start.PixelsServer;

public class EntityOnlinePlayer extends Entity {
	
	public EntityOnlinePlayer() {
		this.id = 2;
	}
	
	public EntityOnlinePlayer(int x, int y, boolean prop, int uid) {
		this.id = 2;
		posX = x;
		posY = y;
		userID = uid;
		if (prop)
			PixelsServer.world.propogatePlayer(this);
	}
	
	public int userID;

}
