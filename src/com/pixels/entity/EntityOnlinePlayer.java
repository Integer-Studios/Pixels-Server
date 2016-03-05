package com.pixels.entity;

import com.pixels.start.PixelsServer;

public class EntityOnlinePlayer extends Entity {
	
	public EntityOnlinePlayer() {
		this.id = 2;
	}
	
	public EntityOnlinePlayer(float x, float y, boolean prop, int uid) {
		this.id = 2;
		posX = x;
		posY = y;
		userID = uid;
		if (prop)
			PixelsServer.world.propogatePlayer(this);
	}
	
	public int userID;

}
