package com.pixels.entity;

import com.pixels.start.PixelsServer;
import com.pixels.world.World;

public class Entity {

	public Entity(int x, int y) {
		id = 0;
		posX = x;
		posY = y;
		serverID = PixelsServer.world.propogateEntity(this);
	}

	public void update(World w) {
		
	}

	public int getServerID() {
		return serverID;
	}
	
	public int id, posX, posY, serverID;

}
