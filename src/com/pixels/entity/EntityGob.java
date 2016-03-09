package com.pixels.entity;

import java.util.Random;

import com.pixels.body.BodyBiped;
import com.pixels.world.World;

public class EntityGob extends EntityAlive {
	
	public EntityGob() {
		super();
		this.id = 3;
		body = new BodyBiped(this, 0.75f, 1.1875f);
	}
	
	public EntityGob(float x, float y, boolean prop) {
		super(x, y, prop);
		this.id = 3;
		body = new BodyBiped(this, 0.75f, 1.1875f);
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
	
	float speed = 0.2f;

}
