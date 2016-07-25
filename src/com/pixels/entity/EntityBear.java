package com.pixels.entity;

import java.util.Random;

import com.pixels.body.BodyQuadruped;
import com.pixels.world.World;

public class EntityBear extends EntityAlive {
	
	public EntityBear() {
		this.id = 4;
		body = new BodyQuadruped(this, 1.5f, 1.448f);
	}
	
	public EntityBear(float x, float y, boolean prop) {
		super(4, x, y, prop);
		body = new BodyQuadruped(this, 1.5f, 1.448f);
	}
	
	public void update(World w) {

		Random r = new Random();
		if (r.nextInt(60) == 0) {
			velocityX = speed;
		}
		if (r.nextInt(60) == 0) {
			velocityX = -speed;
		}
		if (r.nextInt(60) == 0) {
			velocityY = speed;
		}
		if (r.nextInt(60) == 0) {
			velocityY = -speed;
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
		super.update(w);

	}
	
	float speed = 0.03f;

}
