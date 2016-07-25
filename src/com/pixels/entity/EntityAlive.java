package com.pixels.entity;

import com.pixels.body.Body;
import com.pixels.world.World;

public class EntityAlive extends Entity {
	
	public EntityAlive() {
		super();
	}
	
	public EntityAlive(int i, float x, float y, boolean prop) {
		super(i, x, y, prop);
	}
	
	public void update(World w) {
		body.update();
		super.update(w);
	}

	public Body body;
}
