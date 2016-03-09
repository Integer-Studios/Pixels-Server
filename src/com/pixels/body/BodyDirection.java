package com.pixels.body;

public class BodyDirection {
	
	// width and height as a factor of body width and height
	public BodyDirection(float widthFactor, float heightFactor) {
		collisionWidthFactor = widthFactor;
		collisionHeightFactor = heightFactor;
	}
	
	public float getCollisionWidth() {
		return collisionWidthFactor;
	}
	
	public float getCollisionHeight() {
		return collisionHeightFactor;
	}
	
	public float collisionWidthFactor, collisionHeightFactor;

}
