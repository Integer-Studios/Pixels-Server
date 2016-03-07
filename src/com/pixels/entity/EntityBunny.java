package com.pixels.entity;

import java.util.Random;

import com.pixels.world.World;

public class EntityBunny extends Entity {
	
	public EntityBunny() {
		this.id = 3;
	}
	
	public EntityBunny(float x, float y, boolean prop) {
		super(x, y, prop);
		this.id = 3;
	}
	
	public void update(World w) {
		Random r = new Random();
		if (r.nextInt(30) == 0) {
			velX = 0.05f;
		}
		if (r.nextInt(30) == 0) {
			velX = -0.05f;
		}
		if (r.nextInt(30) == 0) {
			velY = 0.05f;
		}
		if (r.nextInt(30) == 0) {
			velY = -0.05f;
		}
		if (r.nextInt(30) == 0) {
			velX = 0f;
		}
		if (r.nextInt(30) == 0) {
			velY = 0f;
		}
		if (r.nextInt(30) == 0) {
			velX = 0f;
			velY = 0f;
		}
		
		setPosition(posX+velX, posY+velY);
	}
	
	float velX = 0;
	float velY = 0;

}
