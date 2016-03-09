package com.pixels.entity;

import java.util.Random;

import com.pixels.world.World;

public class EntityGob extends Entity {
	
	public EntityGob() {
		this.id = 4;
	}
	
	public EntityGob(float x, float y, boolean prop) {
		super(x, y, prop);
		this.id = 4;
	}
	
	public void update(World w) {
		super.update(w);
		Random r = new Random();
		if (r.nextInt(60) == 0) {
			velocityX = 0.05f;
		}
		if (r.nextInt(60) == 0) {
			velocityX = -0.05f;
		}
		if (r.nextInt(60) == 0) {
			velocityY = 0.05f;
		}
		if (r.nextInt(60) == 0) {
			velocityY = -0.05f;
		}
		if (r.nextInt(60) == 0) {
			velocityX = 0f;
		}
		if (r.nextInt(60) == 0) {
			velocityY = 0f;
		}
		if (r.nextInt(60) == 0) {
			velocityX = 0f;
			velocityY = 0f;
		}
	
		
	}

}
