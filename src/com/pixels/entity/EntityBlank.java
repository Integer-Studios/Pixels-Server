package com.pixels.entity;

import com.pixels.world.World;

public class EntityBlank extends Entity {
	
	public EntityBlank() {
		this.id = 0;
	}
	
	public EntityBlank(float x, float y, boolean prop) {
		super(x, y, prop);
		this.id = 0;
	}
	
	public void update(World w) {
		setPosition(posX+0.05f, posY+0.05f);
	}

}
