package com.pixels.entity;

import java.util.Random;

import com.pixels.packet.PacketMoveEntity;
import com.pixels.player.PlayerManager;
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
