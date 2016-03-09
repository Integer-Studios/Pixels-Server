package com.pixels.entity;

import org.newdawn.slick.geom.Rectangle;

import com.pixels.body.BodyBiped;
import com.pixels.start.PixelsServer;

public class EntityOnlinePlayer extends EntityAlive {
	
	public EntityOnlinePlayer() {
		super();
		this.id = 2;
		body = new BodyBiped(this, 0.875f, 1.3125f);
	}
	
	public EntityOnlinePlayer(float x, float y, boolean prop, int uid) {
		this.id = 2;
		posX = x;
		posY = y;
		userID = uid;
		collisionBox = new Rectangle(0, 0, 0, 0);
		body = new BodyBiped(this, 0.875f, 1.3125f);
		if (prop)
			PixelsServer.world.propogatePlayer(this);
	}

	public int userID;

}
