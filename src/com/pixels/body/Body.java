package com.pixels.body;

import java.util.ArrayList;

import com.pixels.body.BodyDirection;
import com.pixels.entity.Entity;

public class Body {
	
	public Body(Entity e, float w, float h, ArrayList<BodyDirection> d) {
		//handles which direction to show, what actions are present
		entity = e;
		width = w;
		height = h;
		directions = d;

	}
	
	public void update() {
		updateDirection();
	}
	
	public void updateDirection() {
		
		if (entity.velocityX > 0) {
			if (entity.velocityY > 0) {
				// + x + y front right
				currentDirection = 3;
			} else if (entity.velocityY < 0) {
				// + x - y back right
				currentDirection = 4;
			} else {
				// + x 0 y right
				currentDirection = 2;
			}
		} else if (entity.velocityX < 0) {
			if (entity.velocityY > 0) {
				// - x + y front left
				currentDirection = 3;
			} else if (entity.velocityY < 0) {
				// - x - y back left
				currentDirection = 4;
			} else {
				// - x 0 y left
				currentDirection = 2;
			}
		} else if (entity.velocityY > 0) {
			// 0 x + y front
			currentDirection = 0;
		} else if (entity.velocityY < 0) {
			// 0 x - y back
			currentDirection = 1;
		} else {
			// 0 x 0 y no change (keep at whatever last movement was)
		}
		
		entity.setCollisionBoxSize(width * directions.get(currentDirection).getCollisionWidth(), height * directions.get(currentDirection).getCollisionHeight());
		
	}
	
	public Entity entity;
	public ArrayList<BodyDirection> directions;
	public int currentDirection = 0;
	public float width, height;


}
